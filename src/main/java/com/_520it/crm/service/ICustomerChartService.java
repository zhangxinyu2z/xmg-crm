package com._520it.crm.service;


import com._520it.crm.domain.CustomerVO;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface ICustomerChartService {


    PageResult selectByCondition(QueryObject qo);

    List<String> selectTimeYear(QueryObject qo);


    List<Long> selectAmountYear(QueryObject qo);

    List<CustomerVO> selectPie(QueryObject qo);

}
