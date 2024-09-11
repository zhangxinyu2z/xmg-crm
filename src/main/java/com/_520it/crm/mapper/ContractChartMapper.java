package com._520it.crm.mapper;

import com._520it.crm.domain.Contract;
import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface ContractChartMapper {

    List<Contract> selectByCondition(PageReq qo);

    Long selectByConditionCount(PageReq qo);

    List<String> selectTimeYear(PageReq qo);

    List<Long> selectAmountYear(PageReq qo);

    List<ContractChartVO> selectPie(PageReq qo);


}