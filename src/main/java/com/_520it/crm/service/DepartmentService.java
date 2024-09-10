package com._520it.crm.service;

import java.util.List;

import com._520it.crm.domain.Department;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.DepartmentQueryObject;

public interface DepartmentService {
	int deleteByPrimaryKey(Long id);

	int insert(Department record);

	Department selectByPrimaryKey(Long id);

	List<Department> selectAll();

	int updateByPrimaryKey(Department record);

	/**
	 * 获取所有的部门信息
	 * @return
	 */
	List<Department> queryForEmp();

	/**
	 * @param qo
	 * @return
	 */
	PageResult queryForPage(DepartmentQueryObject qo);

	/**
	 * @param department
	 */
	void update(Department department);

	/**
	 * @param department
	 */
	void save(Department department);

}
