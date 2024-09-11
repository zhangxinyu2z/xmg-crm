package com._520it.crm.service.impl;

import com._520it.crm.domain.CustomerDevPlan;
import com._520it.crm.mapper.CustomerDevPlanMapper;
import com._520it.crm.req.CustomerDevPlanQueryObject;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.ICustomerDevPlanService;
import com._520it.crm.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class CustomerDevPlanServiceImpl implements ICustomerDevPlanService {

    @Autowired
    private CustomerDevPlanMapper dao;

    @Override
    public int save(CustomerDevPlan plan) {
        CustomerDevPlan devPlan = dao.selectByCustomerId(plan.getCustomer().getId());
        if (devPlan != null) {
            return -1;
        }
        return dao.insert(plan);
    }

    @Override
    public int delete(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public CustomerDevPlan get(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int update(CustomerDevPlan plan) {
        return dao.updateByPrimaryKey(plan);
    }

    @Override
    public PageResult listAll() {
        return new PageResult(Long.parseLong(dao.selectAll().size() + ""), dao.selectAll());
    }

    @Override
    public PageResult queryDevPlanByCondition(PageReq qo) {
        // 市场主管可以查看所有正式客户开发计划
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerDevPlanController:ALL")) {
            CustomerDevPlanQueryObject qo1 = (CustomerDevPlanQueryObject) qo;
            qo1.setUserId(null);
        }
        // 根据查询条件查询出总条数
        Long count = dao.queryDevPlanByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CustomerDevPlan> listData = dao.queryDevPlanByCondition(qo);
            return new PageResult(count, listData);
        }
    }

    /**
     * 市场专员只允许查看自己指定的潜在客户开发计划
     *
     * @param qo
     * @return
     */
    @Override
    public PageResult queryPontentialDevPlanByCondition(PageReq qo) {
        // 市场主管可以查看所有潜在列表计划
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerDevPlanController:ALL")) {
            CustomerDevPlanQueryObject customerQo = (CustomerDevPlanQueryObject) qo;
            customerQo.setUserId(null);
        }
        // 根据查询条件查询出总条数
        Long count = dao.queryPotentialDevPlanByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CustomerDevPlan> listData = dao.queryPotentialDevPlanByCondition(qo);
            return new PageResult(count, listData);
        }
    }
}
