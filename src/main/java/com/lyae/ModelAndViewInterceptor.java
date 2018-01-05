package com.lyae;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lyae.menu.MenuService;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ModelAndViewInterceptor extends HandlerInterceptorAdapter{

	@Autowired  MenuService menuService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/* false 를 return 할 경우 다음 내용은 실행하지 않는다.*/
		boolean result = true;
		
		log.info("======= preHandle 시작 =======");
		String path = request.getServletPath();
		log.info("호출 주소 : " + path);
		
//		log.info("======= preHandle 종료 =======");
		
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		log.info("======= postHandle 시작 =======");
		String path = request.getServletPath();
//		log.info("매핑 주소 : " + path);
		log.info("mav 객체 전 : " +modelAndView);
		if (modelAndView != null){
			Head(modelAndView);
			Vars(modelAndView, path);
		}
		
		//하위메뉴 검증;;
//		if ("".equals(request.getAttribute("subDir")) || null == request.getAttribute("subDir")){
//			request.setAttribute("subDir", "[]");
//		};
		
		log.info("mav 객체 후 : " +modelAndView);
		log.info("======= postHandle 종료 =======");
	}	

	void Head(ModelAndView mav){
		mav.addObject("_test", "인터셉터 테스트");
	}
	
	void Vars(ModelAndView mav, String path){
		mav.addObject("_menu", menuService.getMenuGroups());
//		mav.addObject("_title", menuService.getMenuTitle(path));
		mav.addObject("_path", path);
	}
}
