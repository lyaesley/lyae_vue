package com.lyae.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyae.menu.Menu;

//@RestController
@RequestMapping("/")
@Controller
@Menu(name="Test", desc="Test")
public class TestController {
	
	@Value("${path.controller}") String path;
	
	@GetMapping("")
	public String index(){
		return "index";
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
