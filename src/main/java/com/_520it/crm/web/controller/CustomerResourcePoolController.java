package com._520it.crm.web.controller;

import com._520it.crm.annotation.RequiredPermission;
import com._520it.crm.domain.*;
import com._520it.crm.req.CustomerResourcePoolQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.*;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 客户资源池管理
 *
 * @author zhangxinyu
 */
@Controller
public class CustomerResourcePoolController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SystemDictionaryItemService systemDictionaryItemService;

    @RequiredPermission("查看客户资源池")
    @RequestMapping("/customerResourcePool")
    public String index() {
        return "customerResourcePool";
    }

    /**
     * 资源池客户列表
     *
     * @param qo
     * @return
     */
    @RequestMapping("/customerResourcePool_list")
    @ResponseBody
    public PageResult list(CustomerResourcePoolQueryObject qo) {
        PageResult result = null;
        Employee e = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
        try {
            qo.setUserId(e.getId());
            result = customerService.queryResourcePoolByCondition(qo);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/customerResourcePool_query")
    @ResponseBody
    public List<SystemDictionaryItem> queryBySn(String sn) {
        return systemDictionaryItemService.queryBySn(sn);

    }

    @RequestMapping("/customerResourcePool_save")
    @ResponseBody
    public AjaxResult save(Customer c) {
        Employee employee = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);

        AjaxResult result = null;
        try {
            c.setStatus(0);
            c.setInputtime(new Date());
            c.setInputuser(employee);
            c.setInchargeuser(employee);
            customerService.save(c);
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "保存异常");
        }

        return result;
    }

    @RequestMapping("/customerResourcePool_update")
    @ResponseBody
    public AjaxResult update(Customer c) {
        //        Employee employee = (Employee) UserContext.getLocalRequest().getSession().getAttribute(UserContext
        //        .USER_IN_SESSION);

        AjaxResult result = null;
        try {
            c.setStatus(0);
            int effectCount = customerService.updateById(c);

            if (effectCount > 0) {
                result = new AjaxResult(true, "更新成功");
            } else {
                result = new AjaxResult(true, "更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "更新异常");
        }

        return result;
    }

    @RequestMapping("/customer_admit")
    @ResponseBody
    public AjaxResult admit(Long id) {
        Employee employee = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);

        AjaxResult result = null;
        try {
            int effectCount = customerService.customerAdmit(employee.getId(), id);

            if (effectCount > 0) {
                result = new AjaxResult(true, "吸纳成功");
            } else {
                result = new AjaxResult(true, "吸纳失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "吸纳异常");
        }

        return result;
    }

}
