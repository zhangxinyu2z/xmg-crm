package com._520it.crm.mapper;

import com._520it.crm.domain.CustomerDevPlan;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface CustomerDevPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerDevPlan record);

    CustomerDevPlan selectByPrimaryKey(Long id);

    List<CustomerDevPlan> selectAll();

    int updateByPrimaryKey(CustomerDevPlan record);

    Long queryDevPlanByConditionCount(QueryObject qo);

    List<CustomerDevPlan> queryDevPlanByCondition(QueryObject qo);

    Long queryPotentialDevPlanByConditionCount(QueryObject qo);

    List<CustomerDevPlan> queryPotentialDevPlanByCondition(QueryObject qo);

    CustomerDevPlan selectByCustomerId(Long id);
}