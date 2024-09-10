package com._520it.crm.service;

import java.util.List;

import com._520it.crm.domain.Permission;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

public interface PermissionService {
	int deleteByPrimaryKey(Long id);

	int insert(Permission record);

	Permission selectByPrimaryKey(Long id);

	List<Permission> selectAll();

	int updateByPrimaryKey(Permission record);

	/**
	 * 返回分页的所有权限记录
	 * 
	 * @param queryObject
	 *            分页请求的参数
	 * @return
	 */
	PageResult queryForPage(QueryObject queryObject);

	/**
	 * 根据用户ID查询出当前用户的权限路径
	 * @param id
	 * @return
	 */
	List<String> queryPermissionByEmpId(Long id);
}