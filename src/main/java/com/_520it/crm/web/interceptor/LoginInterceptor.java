package com._520it.crm.web.interceptor;

import com._520it.crm.domain.Employee;
import com._520it.crm.util.PermissionUtil;
import com._520it.crm.util.UserContext;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户是否登录拦截器
 * 
 * @author xinyu
 * @date 2021/06/21
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		// 为当前用户线程保存request对象，日志从中获取信息
		UserContext.set(request);

		// ------ 用户登录拦截功能相关 ------
		// 从session中获取当前用户信息
		Employee currentUser = (Employee)request.getSession().getAttribute(UserContext.USER_IN_SESSION);
		// 如果session中没有用户信息，或者存在用户信息但是用户的状态为离职
		if (currentUser == null || !currentUser.getState()) {
			// 登录去
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return false;
		}

		// ------ 用户权限访问功能相关 ------
		// 如果不是class org.springframework.web.method.HandlerMethod，说明没有走controller方法，不需要校验（处理响应的json）
		if (handler instanceof HandlerMethod) {
			// 拼接权限表达式(controller全类名+方法名)
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			// com._520it.crm.web.controller.EmployeeController:employee_list
			String function = handlerMethod.getBean().getClass().getName() + ":" + handlerMethod.getMethod().getName();
			/* 2、根据权限表达式进行权限处理
			 * 如果不是权限路径，不需要拦截,返回true
			 * 如果是权限路径，判断当前用户是否拥有该访问权限
			 */
			boolean result = PermissionUtil.checkPermission(function);
			if (result) {
				return true;
			} else {
				// 如果方法上有@ResponseBody注解，返回的json数据，说明是ajax请求
				if (handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class)) {
					// 如果是ajax
					response.sendRedirect("/noPermission.json");
				} else {
					// 如果是返回的是页面路径
					response.sendRedirect("/noPermission.jsp");
				}
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {

	}

}
