package com.lyae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.common.Menu;
import com.lyae.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	
	@Menu(name="게시판", desc="게시판 입니다", order = 1)
	@RequestMapping("/list")
	public String list(Model model){
		model.addAttribute("name", "우와");
		return "board/list";
	}
	
	@Menu(name="테스트", desc="테스트 입니다", order = 2)
	@RequestMapping("/test")
	public String test(Model model){
		return "board/test";
	}
	
	@RequestMapping("/hello")
	public void hello() {
		System.out.println("board.hello");
	}
}
