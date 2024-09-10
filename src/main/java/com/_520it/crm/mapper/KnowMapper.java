package com._520it.crm.mapper;


import com._520it.crm.domain.Know;
import com._520it.crm.query.KnowQueryObject;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface KnowMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Know record);
    Know selectByPrimaryKey(Long id);
    List<Know> selectAll();
    int updateByPrimaryKey(Know record);
	Long queryByConditionCount(KnowQueryObject qo);
	
	List<Know> queryByCondition(QueryObject qo);
	//根据id查询内容
	Know queryContextById(Long id);
}