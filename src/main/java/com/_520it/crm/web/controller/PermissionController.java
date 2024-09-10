package com._520it.crm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.resp.PageResult;
import com._520it.crm.req.PermissionPageReq;
import com._520it.crm.service.PermissionService;

/**
 * @author xinyu
 * @date 2021/06/19
 */
@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/permission_list")
    @ResponseBody
    public PageResult permissionList(PermissionPageReq qo) {
        return permissionService.queryForPage(qo);
    }

    @RequestMapping("/permission_queryById")
    @ResponseBody
    public PageResult queryPermissionsById(PermissionPageReq qo) {
        return permissionService.queryForPage(qo);
    }
}
