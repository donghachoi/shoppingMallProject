package com.cafe24.choiyooq1.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle interceptor =====================================");
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("LoginId");
		if(loginId==null) {
			System.out.println("loginId == null");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인이 필요합니다.') ;self.location = '/logout';</script>");
			out.flush();
		}
		return super.preHandle(request, response, handler);
	}

}
