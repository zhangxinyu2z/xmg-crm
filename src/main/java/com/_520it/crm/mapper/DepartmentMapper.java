package com._520it.crm.mapper;

import com._520it.crm.domain.Department;
import com._520it.crm.query.DepartmentQueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);
 
    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

	List<Department> queryForEmp();

	/**
	 * @param qo
	 * @return
	 */
	Long queryForPageCount(DepartmentQueryObject qo);

	/**
	 * @param qo
	 * @return
	 */
	List<Department> queryForPage(DepartmentQueryObject qo);
}