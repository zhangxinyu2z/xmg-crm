package com._520it.crm.mapper;

import com._520it.crm.domain.Clew;

import java.util.List;

public interface ClewMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Clew record);

    Clew selectByPrimaryKey(Long id);

    List<Clew> selectAll();

    int updateByPrimaryKey(Clew record);

    String getContentById(Long id);
}