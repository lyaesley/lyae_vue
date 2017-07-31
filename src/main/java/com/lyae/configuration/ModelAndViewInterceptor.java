package com.lyae.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class ModelAndViewInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("======= preHandle 시작 =======");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		log.info("======= postHandle 시작 =======");
		String path = request.getServletPath();
		
		log.info(" path : " + path);
		modelAndView.addObject("_path", path);
		blogHead(modelAndView);
		log.info("======= postHandle 종료 =======");
	}	

	void blogHead(ModelAndView mav){
		mav.addObject("_test", "인터셉터 테스트");
	}
	
}
