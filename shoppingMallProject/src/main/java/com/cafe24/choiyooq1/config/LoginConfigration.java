package com.cafe24.choiyooq1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cafe24.choiyooq1.interceptor.LoginInterceptor;

@Configuration
public class LoginConfigration implements WebMvcConfigurer {
	
	@Autowired
	private LoginInterceptor loginInterceptor;
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/*")
				.excludePathPatterns("/")
				.excludePathPatterns("/login")
				.excludePathPatterns("/logout");
	}
}
