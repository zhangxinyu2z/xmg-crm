package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Salary;
import com._520it.crm.req.SalaryQueryObejct;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.ISalaryService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 员工工资
 * <p>
 * 普通员工查看自己的工资，人事可以导入工资到系统中
 * @author
 */
@Controller
public class SalaryController extends BaseController {

    @Autowired
    private ISalaryService salaryService;

    @RequestMapping("/salary")
    public String index() {
        return "salary";
    }

    /**
     * @param qo
     * @param request
     * @return
     */
    @RequestMapping("/salary_list")
    @ResponseBody
    public PageResult list(SalaryQueryObejct qo, HttpServletRequest request) {
        PageResult result = null;
        String function = "com._520it.crm.web.controller.CheckInController:AddCheckIn";
        boolean checkPermission = PermissionUtils.checkPermission(function);
        List<Salary> listAll = salaryService.listAll();
        if (checkPermission) {
            result = salaryService.queryByCondition(qo);
            return result;
        }
        Employee current = (Employee) request.getSession().getAttribute(
                UserContext.USER_IN_SESSION);
        Long id = current.getId();
        result = salaryService.querySalaryByEid(id);
        return result;
    }

    // 上传Excel
    @RequestMapping("/salary_upload")
    @ResponseBody
    public AjaxResult upload(MultipartFile file) {
        AjaxResult result = null;
        if (file == null || file.getSize() <= 0) {
            result = new AjaxResult(false, "请选择要上传的文件");
            return result;
        }
        try {
            salaryService.uploadSalary(file);
            result = new AjaxResult(true, "导入成功");

        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "导入异常");
        }
        return result;
    }

}
