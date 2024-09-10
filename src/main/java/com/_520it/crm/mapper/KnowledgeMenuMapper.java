package com._520it.crm.mapper;

import com._520it.crm.domain.KnowledgeMenu;

import java.util.List;

public interface KnowledgeMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KnowledgeMenu record);

    KnowledgeMenu selectByPrimaryKey(Long id);

    List<KnowledgeMenu> selectAll();

    int updateByPrimaryKey(KnowledgeMenu record);
    //查询父菜单
    List<KnowledgeMenu> selectRoot();
    //查询子菜单
    List<KnowledgeMenu> selectByPid();
}