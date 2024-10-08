package com._520it.crm.service;

import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import java.util.List;

/**
 * @author xinyu
 * @date 2021/06/26
 */
public interface SystemDictionaryItemService {
	
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

	/**
	 * @param qo
	 * @return
	 */
	PageResult queryItemById(PageReq qo);

	/**
	 * @param id
	 */
	void forbiddenDictionaryItem(Long id);

	/**
	 * 查询字典对应的字典明细
	 */
	List<SystemDictionaryItem> queryDicItem(Integer id);

    List<SystemDictionaryItem> queryBySn(String sn);
}
