package com._520it.crm.mapper;

import com._520it.crm.domain.Employee;
import com._520it.crm.req.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Employee record);

	Employee selectByPrimaryKey(Long id);

	List<Employee> selectAll();

	int updateByPrimaryKey(Employee record);

	/**
	 * 根据表单信息查询用户信息
	 * 
	 * @param username
	 *            用户传入的登录名
	 * @param password
	 *            用户传入的登录密码
	 */
	Employee getEmployeeForLogin(@Param("username") String username, @Param("password") String password);

	/**
	 * 查询分页记录数
	 */
	Long queryForPageCount(EmployeeQueryObject queryObject);

	/**
	 * 查询分页数据
	 */
	List<Employee> queryForPage(EmployeeQueryObject queryObject);

	void updateState(long id);

	/** 插入员工和角色关联信息 */
	void insertRelation(@Param("eid") Long eid, @Param("rid") Long rid);

	/** 查询用户角色 */
	List<Long> queryRoleById(Long eid);

	/**
	 * 删除员工和角色的关联信息
	 */
	void deleteRelation(Long id);

	/**
	 * 查询人事专员角色关联的所有用户
	 */
	List<Employee> queryEmployeeByRoleId(Long id);
}