package com.lyae.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyae.menu.Menu;

//@RestController
@RequestMapping("/test")
@Controller
@Menu(name="Test", desc="Test")
public class TestController {
	
	@Value("${path.controller}") String path;
	
	@Menu(name="인덱스", desc="인덱스", order=1)
	@GetMapping("")
	public String index(){
		return "index";
	}
	
	@Menu(name="셀렉트 박스", desc="셀렉트박스 테스트", order=2)
	@GetMapping("/selectBox")
	public String selectBox(){
		return "test/selectBox";
	}
			
	@GetMapping("/hello")
	public String hello(){
		return "hello world"+path;
	}
	
	@GetMapping("/demo/index")
	public String bootstrapDemo(){
		return "demo/index";
	}
}
