package com.puercha.algo.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.puercha.algo.user.service.LoginService;
import com.puercha.algo.user.vo.UserVO;

public class LoginInterceptor implements HandlerInterceptor {
	public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Inject
	LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(redirectWhenNull(request,response,session)) {			
			return false;
		}
		UserVO user = loginService.getLoggedInUser(session);
		logger.info(""+user);
		if(redirectWhenNull(request,response,user)) {		
			return false;			
		}		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	private boolean redirectWhenNull(HttpServletRequest request,HttpServletResponse response, Object target) throws Exception{
		// 로그아웃 직후
		logger.info("request.getParameterMap()"+request.getParameterMap().keySet());
		if(request.getParameterMap().get("afterlogout")!=null) {// 로그아웃 직후
			response.sendRedirect("/");
			return true;
		}
		if(target==null) {	
			String uriStr = request.getRequestURI();
			String contextPath = request.getContextPath();
			String reqResource = uriStr.substring(contextPath.length());
			
			response.sendRedirect(contextPath+"/login/signing-in?next="+reqResource);
			return true;			
		}
		return false;
	}
	
}
