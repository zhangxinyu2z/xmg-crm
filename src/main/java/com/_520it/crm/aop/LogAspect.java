package com._520it.crm.aop;

import cn.hutool.core.util.ReflectUtil;
import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Log;
import com._520it.crm.service.LogService;
import com._520it.crm.utils.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import sun.misc.ProxyGenerator;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 日志信息的增强类
 * @author daniel
 */
public class LogAspect {

	@Autowired
	private LogService logService;

	/**
	 * AOP日志功能，因为LogUtil交给了Spring管理，只要方法申明joinPoint，spring会自动完成装配
	 * 
	 * @param joinPoint
	 */
	public void writeLog(JoinPoint joinPoint) {
		// logService不需要日志拦截，否则导致死循环
		if (joinPoint.getTarget() instanceof LogService) {
			return;
		}
		Log log = new Log();
		// 拦截器不拦截登录请求，在登录接口中保存请求线程的request
		HttpServletRequest request = UserContext.get();

		log.setOpip(request.getRemoteAddr());
		log.setOptime(new Date());
		Employee currentUser = (Employee)request.getSession().getAttribute(UserContext.USER_IN_SESSION);
		log.setOpuser(currentUser);

		// ------------获取方法相关信息---------------
		// 被代理类的Class的全类名
		String className = joinPoint.getTarget().getClass().getName();

		// 获取被代理类的class对象
		//Class class1 = joinPoint.getTarget().getClass();
		// 获取的是代理类的class对象
		//Class class2 = joinPoint.getThis().getClass(); // com.sun.proxy.$Proxy
		// 用来把生成的代理类保存到磁盘上
		// outputProsyClass(class1.getInterfaces()[0]);

		// 获取的是接口中的方法对象
		Method m1 = ((MethodSignature)joinPoint.getSignature()).getMethod();
		// 获取接口中方法的参数名字
		String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();

		// 获取的的接口实现类的方法对象
		Method m2 = ReflectUtil.getMethod(joinPoint.getTarget().getClass(), m1.getName(), m1.getParameterTypes());
		LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
		// 获取到接口实现类的方法的参数
		String[] parameterNames = paramNames.getParameterNames(m2);

		// 获取方法的名字
		String methodName = joinPoint.getSignature().getName();

		log.setFunction(className + ":" + methodName);
		ObjectMapper objectMapper = new ObjectMapper();
		String params;
		try {
			// 获取传入方法参数的值
			Object[] args = joinPoint.getArgs();
			/**
			 * TODO:日志的处理 如果方法没有参数产生的空指针异常
			 */
			Map<String, Object> map = new HashMap<>();
			if (parameterNames != null && args != null) {
				for (int i = 0; i < parameterNames.length; i++) {
					if (args[i] instanceof OutputStream) {
						map.put(parameterNames[i], args[i].toString());
					} else {
						map.put(parameterNames[i], args[i]);
					}
				}
			}

			//JSONObject jsonObject = new JSONObject();
			//jsonObject.accumulate("params",map);
			//String s=  jsonObject.toString();

			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			params = objectMapper.writeValueAsString(map);
			log.setParams(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logService.insert(log);
	}

	/**
	 * 用来生成service的代理类
	 * 
	 * @param clazz
	 */
	public void outputProsyClass(Class clazz) {
		byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy", new Class[] {clazz});
		try (FileOutputStream fos = new FileOutputStream(new File("D:/$Proxy.class"))) {
			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
