package com.lyae.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyae.common.Menu;
import com.lyae.service.SoccerService;

import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j 
@RequestMapping("/soccer")
public class SoccerController {
	
	@Autowired SoccerService soccerService;
	
	@Menu(name="해외축구 정보", desc="해외축구 정보", order=20)
	@RequestMapping("/list")
	public String soccer(HttpServletRequest req, Model model) {
		soccerService.api_soccer(req, model);
		return "soccer/index";
	}
	
	// ================================
	// - 아래는 API 입니다.
	// ================================
	// 때문에 @DevoMenu를 선언하지 마시오.
	// ================================
	
	@ResponseBody
	@RequestMapping("/api/seasonsList" )
	String api_seasonsList(HttpServletRequest req, Model model) {
		
		return soccerService.api_seasonsList(req, model);
	}
	
}
