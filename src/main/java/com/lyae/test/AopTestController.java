package com.lyae.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyae.menu.Menu;

public class AopTestController {

	@Menu(name = "aop", desc = "aop", order = 1)
	@RequestMapping("/aop")
	public String aopTest() {
		
		return "test/aop.jsp";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
