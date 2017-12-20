package com.lyae.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.common.Menu;
import com.lyae.service.SoccerService;

import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j 
@RequestMapping("/soccer")
public class SoccerController {
	
	@Autowired SoccerService soccerService;
	
	@Menu(name="해외축구 정보", desc="해외축구 정보", order=20)
	@RequestMapping("/list")
	public String seasonsList(HttpServletRequest req, Model model) {
		soccerService.seasonsList(req, model);
		return "soccer/seasonsList";
	}
	
}
