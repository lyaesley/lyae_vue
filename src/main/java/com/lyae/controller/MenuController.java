package com.lyae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MenuController {

	@RequestMapping("/")
	String boardList(){
		return "index";
	}
}
