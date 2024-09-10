package com._520it.crm.web.controller;

import com._520it.crm.domain.CheckIn;
import com._520it.crm.domain.Employee;
import com._520it.crm.page.AjaxResult;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.CheckInQueryObject;
import com._520it.crm.service.ICheckInService;
import com._520it.crm.util.PermissionUtils;
import com._520it.crm.util.RequiredPermission;
import com._520it.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 员工签到管理
 *
 * @author zhangxinyu
 */
@Controller
public class CheckInController extends BaseController {

    @Autowired
    private ICheckInService checkInService;

    @RequestMapping("/checkIn")
    public String index() {
        return "checkIn";
    }

    private Employee getCurrentLoginUser() {
        return (Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
    }

    /**
     * 人事可以查询所有的考勤记录，普通员工只能查看自己的考勤
     *
     * @param qo
     * @param request
     * @return
     */
    @RequestMapping("/checkIn_list")
    @ResponseBody
    public PageResult list(CheckInQueryObject qo, HttpServletRequest request) {
        PageResult result = null;
        String function = "com._520it.crm.web.controller.CheckInController:addCheckIn";
        boolean checkPermission = PermissionUtils.checkPermission(function);
        if (checkPermission) {
            result = checkInService.queryByCondition(qo);
            return result;
        }
        Employee current = getCurrentLoginUser();
        Long id = current.getId();
        result = checkInService.queryCheckInByEid(id);
        return result;
    }

    /**
     * 签到
     *
     * @param checkIn
     * @param request
     * @return
     */
    @RequestMapping("/checkIn_signIn")
    @ResponseBody
    public AjaxResult signIn(CheckIn checkIn, HttpServletRequest request) {
        Employee current = this.getCurrentLoginUser();
        Long id = current.getId();
        HttpServletRequest localRequest = UserContext.get();
        AjaxResult result = null;
        try {
            checkIn.setEmployee(current);
            checkIn.setUserip(localRequest.getRemoteAddr());
            checkIn.setState(CheckIn.SIGNSTATE_NORMAL);
            int effectRows = checkInService.signIn(checkIn, id);
            if (effectRows > 0) {
                result = new AjaxResult(true, "签到成功");
            } else {
                result = new AjaxResult(false, "已签到或者已经签退!已签退请联系管理员补签!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "签到异常，请联系管理员!");
        }
        return result;
    }

    /**
     * 签退
     *
     * @param checkIn
     * @param request
     * @return
     */
    @RequestMapping("/checkIn_signOut")
    @ResponseBody
    public AjaxResult signOut(CheckIn checkIn, HttpServletRequest request) {
        Employee current = this.getCurrentLoginUser();
        Long id = current.getId();
        HttpServletRequest localRequest = UserContext.get();
        AjaxResult result = null;
        try {
            checkIn.setEmployee(current);
            checkIn.setUserip(localRequest.getRemoteAddr());
            checkIn.setState(CheckIn.SIGNSTATE_NORMAL);
            int effectRows = checkInService.signOut(checkIn, id);
            if (effectRows > 0) {
                result = new AjaxResult(true, "签退成功");
            } else {
                result = new AjaxResult(false, "签退失败!!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "签退异常,联系管理员!");
        }
        return result;
    }


    /**
     * 补签,只有人事有权限操作
     *
     * @param checkIn
     * @param request
     * @return
     */
    @RequiredPermission("考勤补签")
    @RequestMapping("/checkIn_checkIn")
    @ResponseBody
    public AjaxResult addCheckIn(CheckIn checkIn, HttpServletRequest request) {
        //设置补签时间
        Date date = new Date();
        AjaxResult result = null;
        Employee current = this.getCurrentLoginUser();
        try {
            int effectRows = -1;
            // 获取当前ip地址
            checkIn.setUserip(UserContext.get().getRemoteAddr());
            // 设置补签人
            checkIn.setCheckman(current);
            // 设置补签时间
            checkIn.setChecktime(date);
            // 获取当前的记录的ID
            Long id = checkIn.getId();
            // 修改补签
            if (id != null) {
                effectRows = checkInService.update(checkIn);
            } else {
                // 新增
                effectRows = checkInService.save(checkIn);
            }
            if (effectRows > 0) {
                result = new AjaxResult(true, "补签成功");
            } else {
                result = new AjaxResult(false, "补签失败!!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "补签异常,请联系管理员");
        }
        return result;
    }


    /**
     * 新增补签
     *
     * @param checkIn
     * @param request
     * @return
     */
    /*
    @RequestMapping("/checkIn_save")
    @ResponseBody
    public AjaxResult save(CheckIn checkIn, HttpServletRequest request) {
        AjaxResult result = null;
        try {
            int effectRows = checkInService.save(checkIn);
            if (effectRows > 0) {
                result = new AjaxResult(true, "新增补签成功");
            } else {
                result = new AjaxResult(false, "新增补签失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "新增补签失败,请重新补签");
        }
        return result;
    }
     */


    /**
     * 补签修改
     *
     * @param checkIn
     * @param request
     * @return
     */
    /*
    @RequestMapping("/checkIn_update")
    @ResponseBody
    public AjaxResult update(CheckIn checkIn, HttpServletRequest request) {
        AjaxResult result = null;
        try {
            Employee current = (Employee) request.getSession().getAttribute(
                    UserContext.USER_IN_SESSION);
            Long id = current.getId();
            checkIn.setCheckman(current);
            checkIn.setChecktime(new Date());
            int effectRows = checkInService.update(checkIn);
            if (effectRows > 0) {
                result = new AjaxResult(true, "补签修改成功");
            } else {
                result = new AjaxResult(false, "补签修改失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "补签修改失败,请重新补签");
        }
        return result;
    }

     */

}