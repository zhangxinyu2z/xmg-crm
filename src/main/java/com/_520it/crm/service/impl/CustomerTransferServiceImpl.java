package com._520it.crm.service.impl;

import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTransferServiceImpl implements CustomerTransferService {
    @Autowired
    private CustomerTransferMapper customerTransferDao;

    @Override
    public PageResult list(PageReq qo) {
        Long count = customerTransferDao.queryCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<CustomerTransfer> transferList = customerTransferDao.queryTransferList(qo);

        return new PageResult(count, transferList);
    }

    @Override
    public int save(CustomerTransfer transfer) {
        return customerTransferDao.insert(transfer);
    }

    @Override
    public int delete(Long id) {
        return customerTransferDao.deleteByPrimaryKey(id);
    }

    @Override
    public CustomerTransfer get(Long id) {
        return customerTransferDao.selectByPrimaryKey(id);
    }

    @Override
    public int update(CustomerTransfer transfer) {
        return customerTransferDao.updateByPrimaryKey(transfer);
    }

    @Override
    public PageResult listAll() {
        return new PageResult(Long.parseLong(customerTransferDao.selectAll().size() + ""),
            customerTransferDao.selectAll());
    }

    @Override
    public PageResult queryByCondition(PageReq qo) {
        // 根据查询条件查询出总条数
        Long count = customerTransferDao.queryByConditonCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CustomerTransfer> listData = customerTransferDao.queryByCondition(qo);
            return new PageResult(count, listData);
        }
    }

}
