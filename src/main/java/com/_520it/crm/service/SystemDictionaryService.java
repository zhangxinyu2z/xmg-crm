package com._520it.crm.service;

import java.util.List;

import com._520it.crm.domain.SystemDictionary;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

/**
 * @author xinyu
 * @date 2021/06/26
 */
public interface SystemDictionaryService {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

	/**
	 * @return
	 */
	PageResult queryForPage(QueryObject qo);
}
