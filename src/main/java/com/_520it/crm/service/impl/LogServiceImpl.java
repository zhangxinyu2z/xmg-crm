package com._520it.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Log;
import com._520it.crm.mapper.LogMapper;
import com._520it.crm.service.LogService;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private LogMapper logDao;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return logDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Log record) {
		return logDao.insert(record);
	}

	@Override
	public Log selectByPrimaryKey(Long id) {
		return logDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Log> selectAll() {
		return logDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		return 0;
	}

}
