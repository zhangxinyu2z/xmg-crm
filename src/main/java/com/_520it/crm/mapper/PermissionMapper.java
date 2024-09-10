package com._520it.crm.mapper;

import java.util.List;

import com._520it.crm.domain.Permission;
import com._520it.crm.req.PageReq;

public interface PermissionMapper {
	int deleteByPrimaryKey(Long id);
	
	int deleteRelation(Long id);

	int insert(Permission record);

	Permission selectByPrimaryKey(Long id);

	List<Permission> selectAll();

	int updateByPrimaryKey(Permission record);

	/**
	 * 查询权限的总计数，如果有高级查询条件
	 * @param pageReq
	 *            请求分页信息
	 */
	Long queryForPageCount(PageReq pageReq);

	/**
	 * 返回分页的所有权限记录
	 * 
	 * @param pageReq
	 *            分页请求的参数
	 */
	List<Permission> queryForPage(PageReq pageReq);
	
	/**
	 * 根据employee id 查询权限路径
	 */
	List<String> queryPermissionByEmpId(Long id);
}