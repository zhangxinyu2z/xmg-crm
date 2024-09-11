package com._520it.crm.service;

import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import java.util.List;

public interface IContractChartService {




    PageResult selectByCondition(PageReq qo);

    List<String> selectTimeYear(PageReq qo);


    List<Long> selectAmountYear(PageReq qo);

    List<ContractChartVO> selectPie(PageReq qo);





}
