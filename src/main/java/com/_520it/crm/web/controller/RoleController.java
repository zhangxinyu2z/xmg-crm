package com._520it.crm.web.controller;

import com._520it.crm.domain.Role;
import com._520it.crm.req.RoleQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public PageResult list(RoleQueryObject qo) {
        return roleService.queryForPage(qo);
    }

    @RequestMapping("/role_save")
    @ResponseBody
    public AjaxResult save(Role role) {
        AjaxResult ajaxResult = null;
        try {
            roleService.insert(role);
            ajaxResult = new AjaxResult(true, "新增角色成功");
        } catch (Exception e) {
            ajaxResult = new AjaxResult("新增角色失败，请联系管理员");
        }
        return ajaxResult;
    }

    @RequestMapping("/role_update")
    @ResponseBody
    public AjaxResult update(Role role) {
        AjaxResult ajaxResult = null;
        try {
            roleService.updateByPrimaryKey(role);
            ajaxResult = new AjaxResult(true, "修改角色成功");
        } catch (Exception e) {
            ajaxResult = new AjaxResult("修改角色失败，请联系管理员");
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
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int effectCount = roleService.deleteByPrimaryKey(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("删除异常，请联系管理员");
        }
        return result;
    }

}
