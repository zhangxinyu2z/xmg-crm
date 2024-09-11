package com._520it.crm.utils;

import com._520it.crm.domain.*;
import com._520it.crm.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author xinyu
 * @date 2021/06/20
 */
@Component
@Slf4j
public class PermissionUtils {

    private static PermissionService permissionService;

    /**
     * spring无法为static变量注入数据
     * <p>
     * 使用autowired作用在方法上，会调用一次该方法，如果方法中有参数，会对方法中的参数进行装配
     */
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        PermissionUtils.permissionService = permissionService;
    }

    /**
     * 检查当前用户是否拥有该请求的权限
     * <p>
     * 1.对超级管理员直接放行
     * <p>
     * 2.获取当前系统中的所有权限资源（即需要验证的url），如果请求不在权限资源中，直接放行；如果请求在权限资源中，获取当前用户的所有权限，判断是否拥有该权限，先进行完全匹配，再判断是否拥有all权限，没有则拦截
     *
     */
    public static boolean checkPermission(String function) {
        log.info("checkPermission function:{}", function);
        // 1.如果是超级管理员，直接放行
        Employee currentUser = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
        if (currentUser.getAdmin()) {
            return true;
        }

        // 2.先得到系统所有的权限
        if (CommonUtils.ALL_PERMISSIONS_RESOURCES.size() == 0) {
            // 从数据库中查询到所有的权限信息
            List<Permission> allPermissions = permissionService.selectAll();
            for (Permission p : allPermissions) {
                CommonUtils.ALL_PERMISSIONS_RESOURCES.add(p.getResource());
            }
        }
        // 判断当前访问表达式是否需要进行权限判断
        if (CommonUtils.ALL_PERMISSIONS_RESOURCES.contains(function)) {
            // 如果系统权限列表中存在，判断当前用户是否有该权限
            // 获取到用户的权限集合，从用户登录保存的session中获取当前用户的所有权限集合
            Object permissions = UserContext.get().getSession().getAttribute(UserContext.PERMISSION_IN_SESSION);
            if(Objects.isNull(permissions)) {
                return false;
            }
            List<String> userPermissions = ConvertUtils.castList(permissions, String.class);
            // 进行权限完全匹配
            if (userPermissions.contains(function)) {
                return true;
            } else {
                // 进行ALL匹配（拥有当前controller的所有权限）
                String allPermission = function.split(":")[0] + "ALL";
                return userPermissions.contains(allPermission);
            }
        } else {
            // 如果当前路径不是权限路径，放行
            return true;
        }
    }

    /**
     * 该方法将传入的所有系统菜单进行处理：排除用户无权访问的菜单
     *
     * @param menuList 系统菜单
     */
    @SuppressWarnings("unchecked")
    public static void checkMenus(List<Menu> menuList) {
        // 1.如果是超级管理员，直接放行
        Employee currentUser = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
        if (currentUser.getAdmin()) {
            return;
        }

        // 获取用户拥有的权限
        List<String> userPermissions =
            (List<String>)UserContext.get().getSession().getAttribute(UserContext.PERMISSION_IN_SESSION);
        Menu menu = null;
        // 遍历系统菜单，查询用户是否有匹配的权限
        for (int i = menuList.size() - 1; i >= 0; i--) {
            menu = menuList.get(i);
            // 如果菜单的权限表达式不为空
            if (StringUtils.isNotBlank(menu.getFunction())) {
                // 如果用户没有该权限，从菜单列表中移除该菜单
                if (!userPermissions.contains(menu.getFunction())) {
                    menuList.remove(i);
                    // list集合删除索引会更新，处理NullPointerException，这里回顾一下为什么不可以用for
                    // i--;
                }
            }
            // else 菜单权限表单式为空，说明该菜单不需要校验，任何用户都能访问
            // 递归处理菜单的子菜单
            if (menu.getChildren() != null && menu.getChildren().size() > 0) {
                checkMenus(menu.getChildren());
            }
        }
    }

}
