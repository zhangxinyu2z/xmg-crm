package com._520it.crm.service.impl;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.mapper.CustomerMapper;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.req.*;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerService;
import com._520it.crm.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerDao;
    @Autowired
    private CustomerTransferMapper customerTransferDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Customer record) {
        return customerDao.insert(record);
    }

    @Override
    public Customer selectByPrimaryKey(Long id) {
        return customerDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> selectAll() {
        return customerDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Customer record) {
        return customerDao.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryForPage(PageReq qo) {
        Long count = customerDao.queryCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<Customer> customerList = customerDao.queryForPage(qo);
        return new PageResult(count, customerList);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        // 开发失败，修改客户状态
        customerDao.updateStatusById(id, status);
    }

    @Override
    public void transfer(CustomerTransfer ct) {
        // 1、修改客户的负责人
        customerDao.updateInChargerUser(ct);
        // 2、插入一条修改记录
        customerTransferDao.insert(ct);
    }

    @Override
    public PageResult formalList(CustomerQueryObject qo) {
        Long count = customerDao.queryFormalCustomerCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<Customer> customerList = customerDao.queryFormalCustomer(qo);
        return new PageResult(count, customerList);
    }

    @Override
    public int updateByChargeId(Long id, Long inchargeuserId) {
        return customerDao.updateByChargeId(id, inchargeuserId);
    }

    @Override
    public int customerAdmit(Long id, Long id1) {
        return customerDao.customerAdmit(id, id1);
    }

    @Override
    public PageResult queryResourcePoolByCondition(CustomerResourcePoolQueryObject qo) {
        // 所有用户都可以查询到资源池的内容
        CustomerResourcePoolQueryObject customerQo = (CustomerResourcePoolQueryObject)qo;
        customerQo.setUserId(null);
        // 根据查询条件查询出总条数
        Long count = customerDao.queryResourcePoolByConditionCount(qo);
        if (count == 0) {

            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Customer> listData = customerDao.queryResourcePoolByCondition(qo);
            return new PageResult(count, listData);
        }
    }

    @Override
    public boolean save(Customer c) {
        int insert = customerDao.insert(c);
        return insert > 0;
    }

    @Override
    public int updateById(Customer c) {
        return customerDao.updateByPrimaryKey(c);
    }

    @Override
    public int updateStatusFalseById(Long id) {
        return customerDao.updateStatusFalseById(id);
    }

    @Override
    public int updateStatusSuccessById(Long id) {
        return customerDao.updateStatusSuccessById(id);
    }

    @Override
    public PageResult queryByCondition(PotentialCustomerQueryObject qo) {
        String permission = "cn.xy.crm.web.controller.PotentialCustomerController:ALL";
        // 如果是营销主管的话可以查询所有
        if (PermissionUtils.checkPermission(permission)) {
            PotentialCustomerQueryObject customerQo = (PotentialCustomerQueryObject)qo;
            customerQo.setUserId(null);
        }
        // 根据查询条件查询出总条数
        Long count = customerDao.queryByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Customer> listData = customerDao.queryByCondition(qo);
            return new PageResult(count, listData);
        }
    }

}
