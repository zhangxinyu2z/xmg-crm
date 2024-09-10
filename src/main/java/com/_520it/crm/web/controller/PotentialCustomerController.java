package com._520it.crm.web.controller;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.domain.Employee;
import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.page.AjaxResult;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.PotentialCustomerQueryObject;
import com._520it.crm.service.*;
import com._520it.crm.util.RequiredPermission;
import com._520it.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 潜在客户管理
 *
 * @author zhangxinyu
 * @date 2016/9/19
 */
@Controller
public class PotentialCustomerController extends BaseController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICustomerTransferService transferService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @RequiredPermission("查看潜在客户")
    @RequestMapping("/potentialCustomer")
    public String index() {
        return "potentialCustomer";
    }


    /**
     * 查询所有潜在客户
     * 默认全部显示所有的潜在客户
     *
     * @param qo
     * @return
     */
    @RequestMapping("/potentialCustomer_list")
    @ResponseBody
    public PageResult list(PotentialCustomerQueryObject qo) {
        PageResult result = null;
        Employee e = getCurrentLoginEmployee();
        try {
            qo.setUserId(e.getId());
            result = customerService.queryByCondition(qo);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }

    private Employee getCurrentLoginEmployee() {
        return (Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
    }

    /**
     * 查询字典项数据
     *
     * @param sn
     * @return
     */
    @RequestMapping("/potentialCustomer_query")
    @ResponseBody
    public List<SystemDictionaryItem> queryBySn(String sn) {
        return systemDictionaryItemService.queryBySn(sn);

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
        AjaxResult result = null;
        Employee employee = getCurrentLoginEmployee();
        try {
            c.setStatus(0);
            c.setInputtime(new Date());
            c.setInputuser(employee);
            c.setInchargeuser(employee);
            int effectCount = customerService.save(c);
            if (effectCount > 0) {
                result = new AjaxResult(true, "保存成功");
            } else {
                result = new AjaxResult(true, "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(true, "保存异常");
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
        AjaxResult result = null;
        try {
            c.setStatus(0);
            int effectCount = customerService.update(c);

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
     * @param c          潜在客户信息
     * @param inchargeId 其他专员
     * @param reason
     * @return
     */
    @RequestMapping("/potential_updateInCharge")
    @ResponseBody
    public AjaxResult updateInCharge(Customer c, Long inchargeId, String reason) {
        // 获取当前登录对象
        AjaxResult result = null;
        if (c.getInchargeuser().getId().equals(inchargeId)) {
            result = new AjaxResult(false, "您不能自己共享或移交给自己");
            return result;
        }
        try {
            Employee employee = this.getCurrentLoginEmployee();
            // 创建移交记录对象
            CustomerTransfer transfer = new CustomerTransfer();
            transfer.setCustomer(c);
            transfer.setOldseller(c.getInchargeuser());
            transfer.setNewseller(employeeService.get(inchargeId));
            transfer.setTranstime(new Date());
            transfer.setTransuser(employee);
            transfer.setTransreason(reason);
            // 创建移交记录
            transferService.save(transfer);
            int effectCount = customerService.updateByChargeId(c.getId(), inchargeId);
            if (effectCount > 0) {
                result = new AjaxResult(true, "操作成功");
            } else {
                result = new AjaxResult(false, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "操作异常");
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
        AjaxResult result = null;
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
     * 潜在客户开发成功为正式客户
     *
     * @param id
     * @return
     */
    @RequestMapping("/potentialCustomer_become")
    @ResponseBody
    public AjaxResult become(Long id) {
        AjaxResult result = null;
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
