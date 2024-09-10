package com._520it.crm.mapper;

import com._520it.crm.domain.Contract;
import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface ContractChartMapper {

    List<Contract> selectByCondition(QueryObject qo);

    Long selectByConditionCount(QueryObject qo);

    List<String> selectTimeYear(QueryObject qo);

    List<Long> selectAmountYear(QueryObject qo);

    List<ContractChartVO> selectPie(QueryObject qo);


}