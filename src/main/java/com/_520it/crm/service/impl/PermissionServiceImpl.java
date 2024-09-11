package com._520it.crm.service.impl;

import com._520it.crm.domain.Permission;
import com._520it.crm.mapper.PermissionMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public PageResult queryForPage(PageReq pageReq) {
		Long count = permissionMapper.queryForPageCount(pageReq);
		if(count == 0) {
			return PageResult.EMPTY;
		}
		List<Permission> resultList = permissionMapper.queryForPage(pageReq);
		return new PageResult(count, resultList);
	}

	@Override
	public List<String> queryPermissionByEmpId(Long id) {
		return permissionMapper.queryPermissionByEmpId(id);
	}

}
