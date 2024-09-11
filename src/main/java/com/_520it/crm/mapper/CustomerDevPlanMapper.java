package com._520it.crm.mapper;

import com._520it.crm.domain.CustomerDevPlan;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface CustomerDevPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerDevPlan record);

    CustomerDevPlan selectByPrimaryKey(Long id);

    List<CustomerDevPlan> selectAll();

    int updateByPrimaryKey(CustomerDevPlan record);

    Long queryDevPlanByConditionCount(PageReq qo);

    List<CustomerDevPlan> queryDevPlanByCondition(PageReq qo);

    Long queryPotentialDevPlanByConditionCount(PageReq qo);

    List<CustomerDevPlan> queryPotentialDevPlanByCondition(PageReq qo);

    CustomerDevPlan selectByCustomerId(Long id);
}