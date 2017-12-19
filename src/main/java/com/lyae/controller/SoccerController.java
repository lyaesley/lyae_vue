package com.lyae.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.service.SoccerService;

import lombok.extern.slf4j.Slf4j;

@Controller @Slf4j 
@RequestMapping("/soccer")
public class SoccerController {
	
	@Autowired SoccerService soccerService;
	
	@RequestMapping("/list")
	public String seasonsList(HttpServletRequest req, Model model) {
		soccerService.seasonsList(req, model);
		return "soccer/seasonsList";
	}
	
}
