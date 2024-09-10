package com._520it.crm.web.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author dhjy
 * @version v1.0
 * @date created in 2021-05-27 12:41
 */
public class CustomerHandlerExceptionResolver implements HandlerExceptionResolver {
	/**
	 * @param o
	 *            发生异常的信息位置 包名+类名+方法名（形参） 字符串
	 * @param e
	 *            发生的具体异常 程序发布后不可能再用开发工具，只能通过日志，程序打了war包，发布到linux服务器上
	 * @return
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		Object o, Exception e) {
		String message = null;
		// 预期异常
		if (e instanceof MessageException) {
			message = e.getMessage();
		} else {
			// 未知异常,把堆栈信息保存到打印流中
			Writer out = new StringWriter();
			PrintWriter s = new PrintWriter(out);
			e.printStackTrace(s);
			message = out.toString();
		}
		ModelAndView modelAndView = new ModelAndView();;
		modelAndView.addObject("msg", message);
//		modelAndView.setViewName("error");
//		modelAndView.setViewName("forward:/cs.jsp");
		modelAndView.setView(new RedirectView(httpServletRequest.getContextPath() + "/cs.jsp"));
		return modelAndView;

	}
}
