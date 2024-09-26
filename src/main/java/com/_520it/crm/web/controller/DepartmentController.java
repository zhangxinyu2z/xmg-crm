package com._520it.crm.web.controller;

import com._520it.crm.domain.Department;
import com._520it.crm.req.DepartmentQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
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
        AjaxResult result = AjaxResult.createResponse();
        try {
            department.setState(true);
            departmentService.save(department);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    @RequestMapping("/department_update")
    @ResponseBody
    public AjaxResult update(Department department) {
        AjaxResult result = AjaxResult.createResponse();
        try {
            departmentService.update(department);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    @RequestMapping("/department_del")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = AjaxResult.createResponse();
        try {
            departmentService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

}
