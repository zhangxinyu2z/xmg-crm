package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Permission;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.RoleMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int deleteByPrimaryKey(Long id) { // DataAccessException
		// 先把当前角色对应的权限关联信息删掉
		roleMapper.deleteRelation(id);
		// 删除角色，如果角色被分配给员工，抛出异常->回滚删除的信息->controller进行捕获
		int effectCount = roleMapper.deleteByPrimaryKey(id);
		return effectCount;
	}

	@Override
	public int insert(Role record) {
		// 插入角色信息
		int effectCount = roleMapper.insert(record);
		// 插入角色分配的权限
		for (Permission p : record.getPermissions()) {
			roleMapper.insertRelation(record.getId(), p.getId());
		}
		return effectCount;
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		// 更新角色信息
		int effectCount = roleMapper.updateByPrimaryKey(record);
		// 删除旧的权限关联信息
		roleMapper.deleteRelation(record.getId());
		// 插入新的权限关联信息
		for (Permission p : record.getPermissions()) {
			roleMapper.insertRelation(record.getId(), p.getId());
		}
		return effectCount;
	}

	@Override
	public PageResult queryForPage(QueryObject queryObject) {
		Long count = roleMapper.queryForPageCount(queryObject);
		if (count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<Role> roleList = roleMapper.queryForPage(queryObject);
		return new PageResult(count.intValue(), roleList);
	}

	@Override
	public List<Role> queryForEmp() {
		return roleMapper.queryForEmp();
	}

}
