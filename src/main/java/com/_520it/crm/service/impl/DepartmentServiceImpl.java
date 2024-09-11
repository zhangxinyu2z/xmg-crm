package com._520it.crm.service.impl;

import com._520it.crm.domain.Department;
import com._520it.crm.mapper.DepartmentMapper;
import com._520it.crm.req.DepartmentQueryObject;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang xinyu
 * @date 2021/06/17
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentMapper departmentDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return departmentDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Department record) {
		return 0;
	}

	@Override
	public Department selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public List<Department> selectAll() {
		return null;
	}

	@Override
	public int updateByPrimaryKey(Department record) {
		return 0;
	}

	/**
	 * 查询所有的部门名称和id
	 */
	@Override
	public List<Department> queryForEmp() {
		return departmentDao.queryForEmp();
	}

	@Override
	public PageResult queryForPage(DepartmentQueryObject qo) {
		Long count = departmentDao.queryForPageCount(qo);
		if (count == 0) {
			return PageResult.EMPTY;
		}
		List<Department> departmentList = departmentDao.queryForPage(qo);
		return new PageResult(count, departmentList);
	}

	/* (non-Javadoc)
	 * @see com._520it.crm.service.DepartmentService#update(com._520it.crm.domain.Department)
	 */
	@Override
	public void update(Department department) {
		// 修改部门信息
		departmentDao.updateByPrimaryKey(department);
	}

	/* (non-Javadoc)
	 * @see com._520it.crm.service.DepartmentService#save(com._520it.crm.domain.Department)
	 */
	@Override
	public void save(Department department) {
		departmentDao.insert(department);
	}

	@Override
	public List<Department> listAll() {
		return departmentDao.selectAll();
	}

	@Override
	public List<Department> getByMid(Long id) {
		return departmentDao.getByMid(id);
	}

}
