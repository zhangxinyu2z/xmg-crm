package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.SystemDictionary;
import com._520it.crm.mapper.SystemDictionaryMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.SystemDictionaryService;

@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {
	@Autowired
	private SystemDictionaryMapper systemDictionaryDao;
	
	@Override
	public int insert(SystemDictionary record) {
		return 0;
	}

	@Override
	public SystemDictionary selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public List<SystemDictionary> selectAll() {
		return systemDictionaryDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(SystemDictionary record) {
		return 0;
	}

	@Override
	public PageResult queryForPage(QueryObject qo) {
		Long count = systemDictionaryDao.queryCount();
		if(count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		} 
		List<SystemDictionary> records = systemDictionaryDao.queryForPage(qo);
		return new PageResult(count.intValue(), records);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		 return 0;
	}

}
