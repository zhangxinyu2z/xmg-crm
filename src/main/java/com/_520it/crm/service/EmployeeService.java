package com._520it.crm.service;

import com._520it.crm.domain.Employee;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.EmployeeQueryObject;

import javax.servlet.ServletOutputStream;
import java.util.List;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
public interface EmployeeService {
	int deleteByPrimaryKey(Long id);

	int insert(Employee record);

	Employee selectByPrimaryKey(Long id);

	List<Employee> selectAll();

	int updateByPrimaryKey(Employee record);

	/**
	 * 根据用户名查询用户信息
	 */
	Employee getEmployeeForLogin(String username, String password);

	/**
	 * @param queryObject
	 * @return
	 */
	PageResult queryForPage(EmployeeQueryObject queryObject);

	/**
	 * 修改员工状态
	 *
	 * @param id
	 *            0 离职 1 在编
	 */
	void updateState(long id);

	/**
	 * 根据用户id查询所有的角色id
	 */
	List<Long> queryRoleById(Long eid);

	/**
	 * @param outputStream
	 */
	void export(ServletOutputStream outputStream);

	/**
	 * 
	 */
	List<Employee> queryEmployeeByRoleId(Long id);

}
