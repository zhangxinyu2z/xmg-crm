package com._520it.crm.mapper;

import com._520it.crm.domain.Role;
import com._520it.crm.query.QueryObject;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface RoleMapper {
	/**
	 * @throws DataAccessException
	 *             外键关联员工，无法删除，但是异常无法捕获，使用该异常处理
	 */
	int deleteByPrimaryKey(Long id) throws DataAccessException;

	/**
	 * 通过角色id删除关联的权限
	 */
	void deleteRelation(Long id);

	int insert(Role record);

	Role selectByPrimaryKey(Long id);

	List<Role> selectAll();

	int updateByPrimaryKey(Role record);

	/**
	 * 分配角色的权限
	 */
	void insertRelation(@Param("rid") Long rid, @Param("pid") Long pid);

	/**
	 * @param queryObject
	 */
	Long queryForPageCount(QueryObject queryObject);

	List<Role> queryForPage(QueryObject queryObject);

	List<Role> queryForEmp();

}