package com.lyae.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ModelAndViewInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		log.info("======= preHandle 시작 =======");
		boolean result = true;
		
//		log.info("======= preHandle 종료 =======");
		
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("======= postHandle 시작 =======");
		log.info("mav 객체 전 : " +modelAndView);
		if (modelAndView != null){
			Head(modelAndView);
			Vars(modelAndView, request.getServletPath());
		}
		log.info("mav 객체 후 : " +modelAndView);
		log.info("======= postHandle 종료 =======");
	}	

	void Head(ModelAndView mav){
		mav.addObject("_test", "인터셉터 테스트");
	}
	
	void Vars(ModelAndView mav, String path){
		mav.addObject("_title", "타이틀");
		mav.addObject("_menu", "메뉴");
		mav.addObject("_path", path);
	}
	
}
