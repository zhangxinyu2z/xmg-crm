package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Menu;
import com._520it.crm.req.EmployeePageReq;
import com._520it.crm.resp.AJAXResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.*;
import com._520it.crm.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
@Controller
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/employee")
    public String index() {
        return "employee";
    }

    @RequestMapping("/employee_queryRoleById")
    @ResponseBody
    public List<Long> queryByEmployeeId(Long eid) {
        List<Long> roleIds = employeeService.queryRoleById(eid);
        return roleIds;
    }

    @RequestMapping("/employee_save")
    @ResponseBody
    public AJAXResult save(Employee employee) {
        AJAXResult result = AJAXResult.createResponse();
        try {
            employee.setPassword(EncryptHelper.md5("123456"));
            employee.setState(true);
            employee.setAdmin(false);
            employeeService.insert(employee);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setMsg("系统异常");
        }
        return result;
    }

    @RequestMapping("/employee_update")
    @ResponseBody
    public AJAXResult update(Employee employee) {
        AJAXResult result = AJAXResult.createResponse();
        try {
            employeeService.updateByPrimaryKey(employee);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("系统异常");
        }
        return result;
    }

    @RequestMapping("/employee_delete")
    @ResponseBody
    public AJAXResult delete(long id) {
        AJAXResult result = AJAXResult.createResponse();
        try {
            employeeService.updateState(id);
            result = new AJAXResult(true, "离职成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("系统异常");
        }
        return result;
    }

    @RequestMapping("/employee_list")
    @ResponseBody
    public PageResult list(EmployeePageReq queryObject) {
        return employeeService.queryForPage(queryObject);
    }

    @RequestMapping("/employee_export")
    @ResponseBody
    public AJAXResult export(HttpServletResponse response) {
        AJAXResult result;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=SignInTable.xls");
            // response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            employeeService.export(response.getOutputStream());
            result = new AJAXResult(true, "导出成功");
        } catch (Exception e) {
            result = new AJAXResult("导出异常");
        }
        return result;
    }

    @RequestMapping("/login")
    @ResponseBody
    public AJAXResult login(String username, String password, HttpServletRequest request) {
        //拦截器对登录接口放行，由于aop日志需要从request获取信息，需要将当前线程请求的request存储
        UserContext.set(request);
        AJAXResult result = AJAXResult.createResponse();
        String token = EncryptHelper.md5(password);
        Employee currentUser = employeeService.getEmployeeForLogin(username, token);
        if (Objects.isNull(currentUser)) {
            result.setSuccess(false);
            result.setMsg("账号或密码错误");
            return result;
        }

        //1.存储用户信息到session中
        request.getSession().setAttribute(UserContext.USER_IN_SESSION, currentUser);

        //2.存储用户的权限信息
        List<String> userPermissions = permissionService.queryPermissionByEmpId(currentUser.getId());
        request.getSession().setAttribute(UserContext.PERMISSION_IN_SESSION, userPermissions);

        // 3.保存用户菜单权限到session中
        List<Menu> menus = menuService.queryForMenu();
        // 先查询出所有的系统菜单，在根据用户的权限去除用户无权访问的菜单
        PermissionUtil.checkMenus(menus);
        request.getSession().setAttribute(UserContext.MENU_IN_SESSION, menus);

        return result;
    }

    /**
     * 查询拥有人事专员角色的所有用户
     *
     * @return
     */
    @RequestMapping("/queryEmployeeByRole")
    @ResponseBody
    public List<Employee> queryEmployeeByRole(String id) {
        return employeeService.queryEmployeeByRole();
    }

}
