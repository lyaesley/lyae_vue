package com.lyae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	
	@RequestMapping("/list")
	public String list(Model model){
		model.addAttribute("name", "우와");
		return "board/list";
	}
	
	@RequestMapping("/test")
	public String test(Model model){
		System.out.println("test");	
		return "board/test";
	}
}
