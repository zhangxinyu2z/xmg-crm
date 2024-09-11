package com._520it.crm.service.impl;

import com._520it.crm.domain.SystemDictionary;
import com._520it.crm.mapper.SystemDictionaryMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public PageResult queryForPage(PageReq qo) {
		Long count = systemDictionaryDao.queryCount();
		if(count == 0) {
			return PageResult.EMPTY;
		} 
		List<SystemDictionary> records = systemDictionaryDao.queryForPage(qo);
		return new PageResult(count, records);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		 return 0;
	}

}
