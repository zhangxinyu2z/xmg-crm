package com._520it.crm.service;


import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface IContractChartService {




    PageResult selectByCondition(QueryObject qo);

    List<String> selectTimeYear(QueryObject qo);


    List<Long> selectAmountYear(QueryObject qo);

    List<ContractChartVO> selectPie(QueryObject qo);





}
