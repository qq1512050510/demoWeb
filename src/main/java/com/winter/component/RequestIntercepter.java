package com.winter.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestIntercepter implements HandlerInterceptor{
	private final static Logger logger = LoggerFactory.getLogger(RequestIntercepter.class); 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		logger.info("RequestIntercepter preHandle");
		return true;
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		logger.info("RequestIntercepter afterCompletion");
	}
}
