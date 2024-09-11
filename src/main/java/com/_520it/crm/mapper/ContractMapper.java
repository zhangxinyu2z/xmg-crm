package com._520it.crm.mapper;

import com._520it.crm.domain.Contract;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    Contract selectByPrimaryKey(Long id);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    List<Contract> queryByCondition(PageReq qo);

    Long queryByConditionCount(PageReq qo);

    int checked(Long id);

    int noChecked(Long id);
}