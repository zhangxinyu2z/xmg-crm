package com._520it.crm.service;

import com._520it.crm.domain.CustomerVO;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import java.util.List;

public interface ICustomerChartService {


    PageResult selectByCondition(PageReq qo);

    List<String> selectTimeYear(PageReq qo);


    List<Long> selectAmountYear(PageReq qo);

    List<CustomerVO> selectPie(PageReq qo);

}
