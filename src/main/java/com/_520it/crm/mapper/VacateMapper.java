package com._520it.crm.mapper;

import com._520it.crm.domain.Vacate;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface VacateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Vacate record);

    Vacate selectByPrimaryKey(Long id);

    List<Vacate> selectAll();

    int updateByPrimaryKey(Vacate record);

    Long queryByConditionCount(PageReq qo);

    List<Vacate> queryByCondition(PageReq qo);

    Long queryVacateByEidCount(PageReq qo);

    List<Vacate> queryVacateByEid(PageReq qo);


}