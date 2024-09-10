package com._520it.crm.mapper;

import com._520it.crm.domain.SystemDictionary;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface SystemDictionaryMapper {
	int deleteByPrimaryKey(Long id);

	int insert(SystemDictionary record);

	SystemDictionary selectByPrimaryKey(Long id);

	List<SystemDictionary> selectAll();

	int updateByPrimaryKey(SystemDictionary record);

	/**
	 * query the count of the table's record
	 * 
	 * @return
	 */
	Long queryCount();

	/**
	 * @param qo
	 * @return
	 */
	List<SystemDictionary> queryForPage(PageReq qo);
}