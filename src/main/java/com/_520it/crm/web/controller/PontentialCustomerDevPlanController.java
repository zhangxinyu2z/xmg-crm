package com._520it.crm.web.controller;

import com._520it.crm.annotation.RequiredPermission;
import com._520it.crm.domain.CustomerDevPlan;
import com._520it.crm.domain.Employee;
import com._520it.crm.req.CustomerDevPlanQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.ICustomerDevPlanService;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 潜在客户开发计划
 *
 * @author zhangxinyu
 */
@Controller
public class PontentialCustomerDevPlanController extends BaseController {


    @Autowired
    private ICustomerDevPlanService customerDevPlanService;

    @RequiredPermission("查看潜在客户开发计划")
    @RequestMapping("/potentialCustomerDevPlan")
    public String index() {
        return "potentialCustomerDevPlan";
    }

    /**
     * 潜在客户开发计划列表
     *
     * @param qo
     * @return
     */
    @RequestMapping("/potentialCustomerDevPlan_list")
    @ResponseBody
    public PageResult list(CustomerDevPlanQueryObject qo) {
        PageResult result = null;
        try {
            Employee emp = getCurrentLoginUser();
            qo.setUserId(emp.getId());
            result = customerDevPlanService.queryPontentialDevPlanByCondition(qo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增潜在客户开发计划
     *
     * TODO：新增开发计划中应排除正在开发的客户
     * @param plan
     * @return
     */
    @RequestMapping("/potentialCustomerDevPlan_save")
    @ResponseBody
    public AjaxResult save(CustomerDevPlan plan) {
        Employee employee = getCurrentLoginUser();
        // 如果该客户已经在开发中
        plan.setInputuser(employee);
        plan.setInputtime(new Date());
        plan.setType(0);
        AjaxResult result = null;
        try {
            int effectCount = customerDevPlanService.save(plan);
            if (effectCount > 0) {
                result = new AjaxResult(true, "保存成功");
            } else if (effectCount == -1) {
                result = new AjaxResult(false,"该客户正在开发中");
            } else {
                result = new AjaxResult(false, "保存失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "保存异常");
        }
        return result;
    }

    private Employee getCurrentLoginUser() {
        return (Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
    }

    /**
     * 编辑计划
     *
     * @param plan
     * @return
     */
    @RequestMapping("/potentialCustomerDevPlan_update")
    @ResponseBody
    public AjaxResult update(CustomerDevPlan plan) {

        AjaxResult result = null;
        try {
            int effectCount = customerDevPlanService.update(plan);
            if (effectCount > 0) {
                result = new AjaxResult(true, "更新成功");
            } else {
                result = new AjaxResult(false, "更新失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "更新异常");
        }
        return result;
    }

    /**
     * 计划删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/potentialCustomerDevPlan_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int effectCount = customerDevPlanService.delete(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult(false, "删除失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "删除异常");
        }
        return result;
    }

}
