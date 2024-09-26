package com._520it.crm.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
public class UserContext {
    /**
     * 用户登录session
     */
    public static final String USER_IN_SESSION = "USER_IN_SESSION";
    /**
     * 用户功能权限
     */
    public static final String PERMISSION_IN_SESSION = "PERMISSION_IN_SESSION";
    /**
     * 用户权限菜单
     */
    public static final String MENU_IN_SESSION = "MENU_IN_SESSION";
    /**
     * 为每个用户的线程保存自己的request，用来获取日志需要的信息
     */
    private static final ThreadLocal<HttpServletRequest> THREAD_LOCAL = new ThreadLocal<>();

    public static HttpServletRequest get() {
        return THREAD_LOCAL.get();
    }

    public static void set(HttpServletRequest request) {
        THREAD_LOCAL.set(request);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    public static <T>  T getCurrentLoginEmployee(String sessionName, Class<T> clazz) {
        Object attribute = UserContext.get().getSession().getAttribute(sessionName);
        if (Objects.isNull(attribute) || !clazz.isInstance(attribute)) {
            return null;
        }
        return clazz.cast(attribute);
    }
}
