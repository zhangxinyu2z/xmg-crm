package com._520it.crm.mapper;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerVO;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface CustomerChartMapper {

    List<Customer> queryByCondition(QueryObject qo);

    Long queryByConditionCount(QueryObject qo);


    List<String> selectTimeYear(QueryObject qo);

    List<Long> selectAmountYear(QueryObject qo);

    List<CustomerVO> selectPie(QueryObject qo);
}