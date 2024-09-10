package com._520it.crm.web.interceptor;

import com._520it.crm.domain.Employee;
import com._520it.crm.utils.PermissionUtil;
import com._520it.crm.utils.UserContext;
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

		// ------ 用户是否登录 ------
		// 从session中获取当前用户信息
		Employee currentUser = (Employee)request.getSession().getAttribute(UserContext.USER_IN_SESSION);
		// 如果session中没有用户信息，或者存在用户信息但是用户的状态为离职
		if (currentUser == null || !currentUser.getState()) {
			// 登录去
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return false;
		}

		// ------ 用户权限 ------
		// 只处理controller的请求： org.springframework.web.method.HandlerMethod
		if (handler instanceof HandlerMethod) {
			// 拼接权限表达式（包名+类名:接口路径名）：com._520it.crm.web.controller.EmployeeController:employee_list
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			String function = handlerMethod.getBean().getClass().getName() + ":" + handlerMethod.getMethod().getName();
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
