package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Menu;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.EmployeeQueryObject;
import com._520it.crm.service.EmployeeService;
import com._520it.crm.service.MenuService;
import com._520it.crm.service.PermissionService;
import com._520it.crm.util.AJAXResult;
import com._520it.crm.util.PermissionUtil;
import com._520it.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
@Controller
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
        List<Long> roleIds = null;
        roleIds = employeeService.queryRoleById(eid);
        return roleIds;
    }

    @RequestMapping("/employee_save")
    @ResponseBody
    public AJAXResult save(Employee employee) {
        AJAXResult result;
        try {
            employee.setPassword("666666");
            employee.setState(true);
            employee.setAdmin(false);
            employeeService.insert(employee);
            result = new AJAXResult(true, "保存成功");
        } catch (Exception e) {
            result = new AJAXResult("保存异常，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/employee_update")
    @ResponseBody
    public AJAXResult update(Employee employee) {
        AJAXResult result;
        try {
            employeeService.updateByPrimaryKey(employee);
            result = new AJAXResult(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            // 日志信息
            result = new AJAXResult("修改异常，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/employee_delete")
    @ResponseBody
    public AJAXResult delete(long id) {
        AJAXResult result;
        try {
            employeeService.updateState(id);
            result = new AJAXResult(true, "离职成功");
        } catch (Exception e) {
            e.printStackTrace();
            // 日志信息
            result = new AJAXResult("操作异常，请联系管理员");
        }
        return result;
    }

    @RequestMapping("/employee_list")
    @ResponseBody
    public PageResult list(EmployeeQueryObject queryObject) {
        return employeeService.queryForPage(queryObject);
    }

    @RequestMapping("/employee_export")
    @ResponseBody
    public AJAXResult export(HttpServletResponse response) throws IOException {
        AJAXResult result = null;
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
        AJAXResult result;
        Employee currentUser = employeeService.getEmployeeForLogin(username, password);
        if (currentUser != null) {
            result = new AJAXResult(true, "登录成功");
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

        } else {
            result = new AJAXResult("账号或密码错误");
        }
        return result;
    }

    /**
     * 查询拥有人事专员角色的所有用户
     *
     * @return
     */
    @RequestMapping("/queryEmployeeByRole")
    @ResponseBody
    public List<Employee> queryEmployeeByRole() {
        return employeeService.queryEmployeeByRole();
    }

}
