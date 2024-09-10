package com._520it.crm.service;

import com._520it.crm.domain.Role;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.PageReq;

import java.util.List;

/**
 * 
 * @author xinyu
 * @date 2021/06/22
 */
public interface RoleService {
	int deleteByPrimaryKey(Long id);

	/**
	 * 添加角色信息，同时建立角色和权限的关联
	 * 
	 * @param record
	 * @return
	 */
	int insert(Role record);

	Role selectByPrimaryKey(Long id);

	List<Role> selectAll();

	/**
	 * 修改角色信息
	 * <p>
	 * 1.更新角色信息2.删除旧的权限关联3.添加新的权限关联
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Role record);

	PageResult queryForPage(PageReq pageReq);

	/**
	 * @return
	 */
	List<Role> queryForEmp();

}