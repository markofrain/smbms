package pers.cgq.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.tools.Constants;

/**
 * 自定义拦截器 对访问该系统的所有请求进行登陆验证(登陆除外) 若当前session存储用户信息则返回true，放过请求
 * 
 * @author 光奇
 *
 */
public class SysInterceptor implements HandlerInterceptor {

	/**
	 * 请求到大handler之前执行
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		//获得登陆信息
		User user=(User) session.getAttribute(Constants.USER_SESSION);
		if(null==user){//是否为null，是否登陆
			response.sendRedirect(request.getContextPath()+"/error.jsp");
			return false;
		}
		return true;
	}

	/**
	 * 请求被handlerAdapter执行后，渲染视图之前执行
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 响应已经被渲染之后
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
