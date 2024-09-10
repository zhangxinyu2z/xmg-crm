package com._520it.crm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.domain.Role;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.RolePageReq;
import com._520it.crm.service.RoleService;
import com._520it.crm.resp.AJAXResult;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;

	@RequestMapping("/role")
	public String index() {
		return "role";
	}

	@RequestMapping("role_list")
	@ResponseBody
	public PageResult list(RolePageReq qo) {
		return roleService.queryForPage(qo);
	}

	@RequestMapping("/role_save")
	@ResponseBody
	public AJAXResult save(Role role) {
		AJAXResult ajaxResult = null;
		try {
			roleService.insert(role);
			ajaxResult = new AJAXResult(true, "新增角色成功");
		} catch (Exception e) {
			ajaxResult = new AJAXResult("新增角色失败，请联系管理员");
		}
		return ajaxResult;
	}

	@RequestMapping("/role_update")
	@ResponseBody
	public AJAXResult update(Role role) {
		AJAXResult ajaxResult = null;
		try {
			roleService.updateByPrimaryKey(role);
			ajaxResult = new AJAXResult(true, "修改角色成功");
		} catch (Exception e) {
			ajaxResult = new AJAXResult("修改角色失败，请联系管理员");
		}
		return ajaxResult;
	}

	@RequestMapping("/role_queryForEmp")
	@ResponseBody
	public List<Role> queryForEmp() {
		return roleService.queryForEmp();
	}

	@RequestMapping("/role_del")
	@ResponseBody
	public AJAXResult delete(Long id) {
		AJAXResult result = null;
		try {
			int effectCount = roleService.deleteByPrimaryKey(id);
			if (effectCount > 0) {
				result = new AJAXResult(true, "删除成功");
			} else {
				result = new AJAXResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 角色表关联权限表和用户表，因此已经分配角色是不能删除的
			result = new AJAXResult("删除异常，请联系管理员");
		}
		return result;
	}

}
