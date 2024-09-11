package com._520it.crm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.domain.Department;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.DepartmentQueryObject;
import com._520it.crm.service.DepartmentService;
import com._520it.crm.resp.AjaxResult;

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
	public AjaxResult save(Department department) {
		AjaxResult result = null;
		try {
			department.setState(true);
			departmentService.save(department);
			result = new AjaxResult(true, "部门信息添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("添加失败，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/department_update")
	@ResponseBody
	public AjaxResult update(Department department) {
		AjaxResult result = null;
		try {
			departmentService.update(department);
			result = new AjaxResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("操作失败，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/department_del")
	@ResponseBody
	public AjaxResult delete(Long id) {
		AjaxResult result = null;
		try {
			departmentService.deleteByPrimaryKey(id);
			result = new AjaxResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("操作失败，请联系管理员");
		}
		return result;
	}

}
