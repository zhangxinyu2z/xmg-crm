package com._520it.crm.web.controller;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.Employee;
import com._520it.crm.req.CustomerQueryObject;
import com._520it.crm.req.TransferCustomerReq;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * @author xinyu
 * @date 2021/06/22
 */
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/customer")
    public String index() {
        return "customer";
    }

    /**
     * 客户列表
     */
    @RequestMapping("/customer_list")
    @ResponseBody
    public PageResult list(CustomerQueryObject qo) {
        // 专员只能查看自己负责的潜在客户，主管可以查看所有：这里是认为拥有转交权限的就用有查看所有潜在客户的数据权限
        boolean result = PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:transfer");
        if (!result) {
            //根据id查询
            Employee currentUser = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
            qo.setUserId(currentUser.getId());
        }
        if(qo.getStatus() == null) {
            qo.setStatus(4);
        }
        PageResult pageResult = customerService.queryForPage(qo);
        return pageResult;
    }

    /**
     * 正式客户查询，只有市场经理有权限
     *
     * @param qo
     * @return
     */
    @RequestMapping("/official_customer_list")
    @ResponseBody
    public PageResult formalList(CustomerQueryObject qo) {
        qo.setStatus(1);
        return customerService.queryForPage(qo);
    }

    @RequestMapping("/customer_save")
    @ResponseBody
    public AjaxResult save(Customer customer) {
        AjaxResult result = null;
        try {
            Employee currentUser = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);

            customer.setInputuser(currentUser);
            customer.setInchargeuser(currentUser);
            customer.setInputtime(new Date());
            // 新增用户状态为潜在客户 sql插入为0
            customerService.insert(customer);
            result = new AjaxResult(true, "录入成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("警告，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/customer_update")
    @ResponseBody
    public AjaxResult update(Customer customer) {
        AjaxResult result = null;
        try {
            // 修改sql,只修改操作的信息，其它的status、user之类的不要修改
            customerService.updateByPrimaryKey(customer);
            result = new AjaxResult(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("警告，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/customer_fail")
    @ResponseBody
    public AjaxResult fail(Long id) {
        AjaxResult result = null;
        try {
            customerService.updateStatusById(id, -1);
            result = new AjaxResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("警告，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/customer_become")
    @ResponseBody
    public AjaxResult become(Long id) {
        AjaxResult result = null;
        try {
            customerService.updateStatusById(id, 1);
            result = new AjaxResult(true, "转正成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("警告，请联系管理员");
        }
        return result;
    }

    /**
     * 移交&共享
     */
    @RequestMapping("/shareOrTransferCustomer")
    @ResponseBody
    public AjaxResult transfer(TransferCustomerReq req) {
        AjaxResult result = AjaxResult.createResponse();
        Employee user = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
        if (Objects.equals(req.getNewSellerId(), req.getOldSellerId())) {
            AjaxResult.setFailResponse(result, "不能移交给原负责人");
            return result;
        }
        try {
            // 移交的操作员
            req.setTransUserId(user.getId());
            customerService.transfer(req);
        } catch (Exception e) {
            e.printStackTrace();
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    /**
     * 移入资源池
     */
    @RequestMapping("/customer_moveResourcePool")
    @ResponseBody
    public AjaxResult moveToResourcePool(Long id) {
        AjaxResult result = AjaxResult.createResponse();
        Employee employee = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
        if (Objects.isNull(employee)) {
            AjaxResult.setFailResponse(result, "请重新登录");
            return result;
        }
        try {
            customerService.moveToResourcePool(employee.getId(), id);
        } catch (Exception e) {
            e.printStackTrace();
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    /**
     * 流失客户
     *
     * @param id
     * @return
     */
    @RequestMapping("/customer_lost")
    @ResponseBody
    public AjaxResult customerLost(Long id) {
        AjaxResult result = AjaxResult.createResponse();
        Employee employee = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
        if (Objects.isNull(employee)) {
            AjaxResult.setFailResponse(result, "请重新登录");
            return result;
        }
        try {
            customerService.lostCustomer(employee.getId(), id);
        } catch (Exception e) {
            e.printStackTrace();
            AjaxResult.setFailResponse(result);
        }
        return result;
    }

    /**
     * 导出客户信息
     *
     * @param response
     * @return
     */
    @RequestMapping("/customer_export")
    @ResponseBody
    public AjaxResult export(HttpServletResponse response) {
        AjaxResult result = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=SignInTable.xls");
            ServletOutputStream outputStream = response.getOutputStream();
            int effectRows = customerService.exportCustomer(outputStream);
            if (effectRows > 0) {
                result = new AjaxResult(true, "导出成功");
            } else {
                result = new AjaxResult(false, "导出失败 ! 请关闭该文件后重新导出 !");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "导出异常");
        }
        return result;
    }
}
