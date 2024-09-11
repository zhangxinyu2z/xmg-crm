package com._520it.crm.web.controller;

import com._520it.crm.domain.Attendance;
import com._520it.crm.domain.Employee;
import com._520it.crm.req.CheckInQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IAttendanceService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 考勤记录数据
 * <p>
 * todo:每个月的考勤统计应该根据签到记录表自动统计，应该判断当天用户是否有签到，如果签到了，在考勤表中的数据更新
 *
 * @author zhangxinyu
 */
@Controller
public class AttendanceController extends BaseController {

    @Autowired
    private IAttendanceService attendanceService;

    @RequestMapping("/attendance")
    public String index() {
        return "attendance";
    }

    private Employee getCurrentLoginUser() {
        return (Employee) UserContext.get().getSession().getAttribute(
                UserContext.USER_IN_SESSION);
    }

    /**
     * 当月的出勤记录统计
     * <p>
     *
     * @param qo
     * @return
     */
    @RequestMapping("/attendance_list")
    @ResponseBody
    public PageResult list(CheckInQueryObject qo) {
        PageResult result = null;
        String function = "com._520it.crm.web.controller.CheckInController:addCheckIn";
        boolean checkPermission = PermissionUtils.checkPermission(function);
        if (checkPermission) {
            result = attendanceService.queryByCondition(qo);
            return result;
        }
        Employee current = this.getCurrentLoginUser();
        Long id = current.getId();
        result = attendanceService.queryAttendanceByEid(id);
        return result;
    }


    /**
     * 生成所有员工的考勤统计
     *
     * @return
     */
    @RequestMapping("/attendance_updateAttendance")
    @ResponseBody
    public AjaxResult updateAttendance() {
        AjaxResult result = null;
        int row = attendanceService.updateAttendance();
        try {
            if (row > 0) {
                result = new AjaxResult(true, "导入考勤表成功");

            } else {
                result = new AjaxResult(false, "导入考勤表失败");

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "导入考勤表异常");
        }
        return result;

    }


    @RequestMapping("/attendance_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int row = attendanceService.delete(id);
            if (row > 0) {
                result = new AjaxResult(true, "删除成功");

            } else {
                result = new AjaxResult(false, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "删除异常");
        }
        return result;

    }

    /**
     * 新增考勤记录统计
     *
     * @param attendance
     * @return
     */
    @RequestMapping("/attendance_save")
    @ResponseBody
    public AjaxResult save(Attendance attendance) {
        AjaxResult result = null;
        try {
            int effectRows = attendanceService.save(attendance);
            if (effectRows > 0) {
                result = new AjaxResult(true, "保存成功");
            } else {
                result = new AjaxResult(false, "保存失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "保存异常");
        }
        return result;
    }

    /**
     * 修改员工当月考勤统计
     *
     * @param attendance
     * @return
     */
    @RequestMapping("/attendance_update")
    @ResponseBody
    public AjaxResult update(Attendance attendance) {
        AjaxResult result = null;
        try {
            int effectRows = attendanceService.update(attendance);
            if (effectRows > 0) {
                result = new AjaxResult(true, "更改成功");
            } else {
                result = new AjaxResult(false, "更改失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "更改异常");
        }
        return result;
    }

    /**
     * 导出考勤记录
     * <p>
     * @param response
     * @return
     */
    @RequestMapping("/attendance_export")
    @ResponseBody
    public AjaxResult exportAttendance(HttpServletResponse response) {
        AjaxResult result = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=SignInTable.xls");
            ServletOutputStream outputStream = response.getOutputStream();
            int effectRows = attendanceService.exportAttendance(outputStream);
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
