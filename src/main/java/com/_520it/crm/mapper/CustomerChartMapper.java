package com._520it.crm.mapper;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerVO;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface CustomerChartMapper {

    List<Customer> queryByCondition(PageReq qo);

    Long queryByConditionCount(PageReq qo);


    List<String> selectTimeYear(PageReq qo);

    List<Long> selectAmountYear(PageReq qo);

    List<CustomerVO> selectPie(PageReq qo);
}