package com.lyae.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuController {

	@RequestMapping("/board/list")
	String boardList(){
		return "";
	}
}
