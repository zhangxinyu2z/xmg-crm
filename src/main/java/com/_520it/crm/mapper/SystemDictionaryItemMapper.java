package com._520it.crm.mapper;

import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface SystemDictionaryItemMapper {
	int deleteByPrimaryKey(Long id);

	int insert(SystemDictionaryItem record);

	SystemDictionaryItem selectByPrimaryKey(Long id);

	List<SystemDictionaryItem> selectAll();

	int updateByPrimaryKey(SystemDictionaryItem record);

	/**
	 * @param qo
	 * @return
	 */
	Long queryCount(PageReq qo);

	/**
	 * @param qo
	 * @return
	 */
	List<SystemDictionaryItem> queryForPageById(PageReq qo);

	/**
	 * 查询字典对应的字典明细
	 * 
	 * @param id
	 *            字典ID
	 */
	List<SystemDictionaryItem> queryDicItem(Integer id);

	List<SystemDictionaryItem> listBySn(String sn);
}