package com._520it.crm.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
public class UserContext {
	/** 用户登录session */
	public static final String USER_IN_SESSION = "USER_IN_SESSION";
	/** 用户功能权限 */
	public static final String PERMISSION_IN_SESSION = "PERMISSION_IN_SESSION";
	/** 用户权限菜单 */
	public static final String MENU_IN_SESSION = "MENU_IN_SESSION";
	/**
	 * 为每个用户的线程保存自己的request，用来获取日志需要的信息
	 */
	private static final ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();

	public static HttpServletRequest get() {
		return local.get();
	}

	public static void set(HttpServletRequest request) {
		local.set(request);
	}
}
