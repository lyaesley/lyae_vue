package com.lyae.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyae.common.Menu;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Value("${path.controller}") String path;
	
	@Menu(name="테스트안녕", desc="테스트안녕 입니다", order = 4)
	@RequestMapping("/hello")
	public String hello(){
		return "hello world"+path;
	}
}
