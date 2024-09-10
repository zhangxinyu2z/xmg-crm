package com._520it.crm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.domain.Department;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.DepartmentQueryObject;
import com._520it.crm.service.DepartmentService;
import com._520it.crm.util.AJAXResult;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/department")
	public String index() {
		return "department";
	}

	@RequestMapping("/dep_queryDept")
	@ResponseBody
	public List<Department> queryForEmp(Long id) {
		return departmentService.queryForEmp();
	}

	@RequestMapping("/department_list")
	@ResponseBody
	public PageResult list(DepartmentQueryObject qo) {
		return departmentService.queryForPage(qo);
	}

	@RequestMapping("/department_save")
	@ResponseBody
	public AJAXResult save(Department department) {
		AJAXResult result = null;
		try {
			department.setState(true);
			departmentService.save(department);
			result = new AJAXResult(true, "部门信息添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("添加失败，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/department_update")
	@ResponseBody
	public AJAXResult update(Department department) {
		AJAXResult result = null;
		try {
			departmentService.update(department);
			result = new AJAXResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("操作失败，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/department_del")
	@ResponseBody
	public AJAXResult delete(Long id) {
		AJAXResult result = null;
		try {
			departmentService.deleteByPrimaryKey(id);
			result = new AJAXResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("操作失败，请联系管理员");
		}
		return result;
	}

}
