package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Permission;
import com._520it.crm.mapper.PermissionMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.PermissionService;

/**
 * @author xinyu
 * @date 2021/06/19
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	/** 注入数据库操作mapper */
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}

	@Override
	public Permission selectByPrimaryKey(Long id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Permission> selectAll() {
		return permissionMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		return permissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryForPage(QueryObject queryObject) {
		Long count = permissionMapper.queryForPageCount(queryObject);
		if(count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<Permission> resultList = permissionMapper.queryForPage(queryObject);
		return new PageResult(count.intValue(), resultList);
	}

	@Override
	public List<String> queryPermissionByEmpId(Long id) {
		return permissionMapper.queryPermissionByEmpId(id);
	}

}
