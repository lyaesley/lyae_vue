package com.ljy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ljy.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	
	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("게시판리스트");	
		model.addAttribute("name", "우와");
		return "board/list";
	}
}
