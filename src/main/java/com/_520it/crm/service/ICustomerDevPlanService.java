package com._520it.crm.service;

import com._520it.crm.domain.CustomerDevPlan;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface ICustomerDevPlanService {

    int save(CustomerDevPlan plan);

    int delete(Long id);

    CustomerDevPlan get(Long id);

    int update(CustomerDevPlan plan);

    PageResult listAll();

    /**
     * 正式客户开发计划列表查询
     * @param qo
     * @return
     */
    PageResult queryDevPlanByCondition(QueryObject qo);

    /**
     * 潜在客户计划列表
     * @param qo
     * @return
     */
    PageResult queryPontentialDevPlanByCondition(QueryObject qo);

}
