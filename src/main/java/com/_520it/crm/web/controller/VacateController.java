package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Vacate;
import com._520it.crm.req.VacateQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IVacateService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 请假管理
 *
 * @author
 */
@Controller
public class VacateController {

    @Autowired
    private IVacateService vacateService;


    @RequestMapping("/vacate")
    public String index() {
        return "vacate";
    }

    private Employee getCurrentLoginUser() {
        return (Employee) UserContext.get().getSession().getAttribute(
                UserContext.USER_IN_SESSION);
    }

    @RequestMapping("/vacate_list")
    @ResponseBody
    public PageResult list(VacateQueryObject qo) {
        PageResult result = null;
        String function = "com._520it.crm.web.controller.CheckInController:AddCheckIn";
        boolean checkPermission = PermissionUtils.checkPermission(function);
        if (checkPermission) {
            result = vacateService.queryByCondition(qo);
            return result;
        }
        Employee current = this.getCurrentLoginUser();
        Long id = current.getId();
        qo.setId(id);
        return vacateService.queryVacateByEid(qo);
    }

    @RequestMapping("/vacate_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {

            int row = vacateService.delete(id);
            if (row > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult(false, "请假单已审核,不能删除");

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "删除异常");
        }
        return result;
    }

    @RequestMapping("/vacate_save")
    @ResponseBody
    public AjaxResult save(Vacate vacate, HttpServletRequest request) {
        AjaxResult result = null;
        int effectRows = 0;
        try {
            String function = "com._520it.crm.web.controller.CheckInController:AddCheckIn";
            boolean checkPermission = PermissionUtils.checkPermission(function);
            if (checkPermission) {
                vacate.setState(0);
                effectRows = vacateService.save(vacate);
            } else {
                Employee current = (Employee) request.getSession()
                        .getAttribute(UserContext.USER_IN_SESSION);
                Long id = current.getId();
                if (Objects.equals(vacate.getEmployee().getId(), id)) {
                    vacate.setState(0);
                    effectRows = vacateService.save(vacate);
                } else {
                    return new AjaxResult(false, "只能给自己增加请假单");
                }
            }
            if (effectRows > 0) {
                result = new AjaxResult(true, "保存成功");
            } else {
                result = new AjaxResult(false, "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "保存异常");
        }
        return result;
    }

    @RequestMapping("/vacate_update")
    @ResponseBody
    public AjaxResult update(Vacate vacate) {
        AjaxResult result = null;
        try {
            int effectRows = vacateService.update(vacate);
            if (effectRows > 0) {
                result = new AjaxResult(true, "更新成功");
            } else {
                result = new AjaxResult(false, "已审核,不能更改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "更改异常");
        }
        return result;
    }

    /**
     * 请假审核
     *
     * @param vacate
     * @param request
     * @return
     */
    @RequestMapping("/vacate_audit")
    @ResponseBody
    public AjaxResult audit(Vacate vacate, HttpServletRequest request) {
        AjaxResult result = null;
        try {
            Employee current = (Employee) request.getSession().getAttribute(
                    UserContext.USER_IN_SESSION);
            Long id = current.getId();
            vacate.setAudit(current);
            vacate.setAudittime(new Date());
            int effectRows = vacateService.audit(vacate);
            if (effectRows > 0) {
                result = new AjaxResult(true, "审核成功");
            } else {
                result = new AjaxResult(false, "请假单已审核!  不能重复审核");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "审核异常，请联系管理员");
        }
        return result;
    }
}
