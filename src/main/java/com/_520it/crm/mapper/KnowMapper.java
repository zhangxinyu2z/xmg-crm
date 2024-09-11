package com._520it.crm.mapper;

import com._520it.crm.domain.Know;
import com._520it.crm.req.KnowQueryObject;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface KnowMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Know record);
    Know selectByPrimaryKey(Long id);
    List<Know> selectAll();
    int updateByPrimaryKey(Know record);
	Long queryByConditionCount(KnowQueryObject qo);
	
	List<Know> queryByCondition(PageReq qo);
	//根据id查询内容
	Know queryContextById(Long id);
}