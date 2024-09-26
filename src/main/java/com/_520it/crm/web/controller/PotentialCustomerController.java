package com._520it.crm.web.controller;

import com._520it.crm.annotation.RequiredPermission;
import com._520it.crm.domain.Customer;
import com._520it.crm.domain.Employee;
import com._520it.crm.req.CustomerQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerService;
import com._520it.crm.service.CustomerTransferService;
import com._520it.crm.service.EmployeeService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 潜在客户管理
 *
 * @author zhangxinyu
 * @date 2016/9/19
 */
@Controller
public class PotentialCustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerTransferService transferService;
    @Autowired
    private EmployeeService employeeService;

    @RequiredPermission("查看潜在客户")
    @RequestMapping("/potentialCustomer")
    public String index() {
        return "customerPotential";
    }

    /**
     * 潜在客户管理列表
     */
    @RequestMapping("/potentialCustomer_list")
    @ResponseBody
    public PageResult list(CustomerQueryObject qo) {
        PageResult result = null;
        try {
            if (!PermissionUtils.checkPermission(
                "com._520it.crm.web.controller.PotentialCustomerController:transfer")) {
                //根据id查询
                Employee currentUser = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
                qo.setUserId(currentUser.getId());
            }
            if (qo.getStatus() == null) {
                qo.setStatus(3);
            }
            result = customerService.queryForPage(qo);
        } catch (Exception e1) {
            e1.printStackTrace();
            result = PageResult.EMPTY;
        }
        return result;
    }


    @RequestMapping("/official_potentialCustomer_list")
    @ResponseBody
    public PageResult formalList(CustomerQueryObject qo) {
        if (!PermissionUtils.checkPermission(
            "com._520it.crm.web.controller.PotentialCustomerController:transfer")) {
            //根据id查询
            Employee currentUser = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
            qo.setUserId(currentUser.getId());
        }
        qo.setStatus(0);
        return customerService.queryForPage(qo);
    }


    /**
     * 添加潜在客户
     *
     * @param c
     * @return
     */
    @RequestMapping("/potentialCustomer_save")
    @ResponseBody
    public AjaxResult save(Customer c) {
        AjaxResult result = AjaxResult.createResponse();
        Employee employee = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
        try {
            c.setStatus(0);
            c.setInputtime(new Date());
            c.setInputuser(employee);
            c.setInchargeuser(employee);
            customerService.save(c);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("保存失败");
        }
        return result;
    }

    /**
     * 修改潜在客户信息
     *
     * @param c
     * @return
     */
    @RequestMapping("/potentialCustomer_update")
    @ResponseBody
    public AjaxResult update(Customer c) {
        AjaxResult result = AjaxResult.createResponse();
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

    /**
     * 共享功能和移交功能等同，不同的是市场专员只能共享自己的潜在客户给其他专员，而销售主管可分配所有专员的潜在客户
     *
     * @param c          当前客户的负责人和客户相关信息
     * @param inchargeId 被移交人id
     */
    @RequestMapping("/potential_updateInCharge")
    @ResponseBody
    public AjaxResult updateInCharge(Customer c, Long inchargeId, String reason) {
        // 获取当前登录对象
        AjaxResult result = AjaxResult.createResponse();
        if (c.getInchargeuser().getId().equals(inchargeId)) {
            result = new AjaxResult(false, "不能自己共享或移交给自己");
            return result;
        }
        boolean b = customerService.shareOrTransfer(c, inchargeId, reason);
        if (!b) {
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    /**
     * 潜在客户开发失败
     *
     * @param id
     * @returnk
     */
    @RequestMapping("/potentialCustomer_developFalse")
    @ResponseBody
    public AjaxResult developFailed(Long id) {
        AjaxResult result = AjaxResult.createResponse();
        try {
            int effectCount = customerService.updateStatusFalseById(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "操作成功");
            } else {
                result = new AjaxResult(true, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "操作异常");
        }
        return result;
    }

    /**
     * 潜在客户开发成功 => 正式客户
     */
    @RequestMapping("/potentialCustomer_become")
    @ResponseBody
    public AjaxResult become(Long id) {
        AjaxResult result = AjaxResult.createResponse();
        try {
            int effectCount = customerService.updateStatusSuccessById(id);

            if (effectCount > 0) {
                result = new AjaxResult(true, "操作成功");
            } else {
                result = new AjaxResult(true, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "操作异常");
        }
        return result;
    }

}
