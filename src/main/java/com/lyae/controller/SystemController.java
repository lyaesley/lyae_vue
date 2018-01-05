package com.lyae.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 시스템 컨트롤러
 * - 이 컨트롤러는 예외 컨트롤러임으로 다른 컨트롤러와 달리
 * - 시스템에 필요한 것들만 넣어주시기 바랍니다.
 * @author		박용서
 * @since		2018. 1. 2.
 */
@Controller @RequestMapping("/error")
public class SystemController {
	
//	@Autowired UserLoginService userLoginService;
//	@Autowired UserService userService;
	
	@GetMapping("/404")
	String error404(Model model) {
		model.addAttribute("icon", "fa fa-ban");
		model.addAttribute("code", "404");
		model.addAttribute("name", "존재하지 않는 페이지");
		model.addAttribute("desc", "페이지를 찾을 수 없습니다.");
		return "error";
	}
	
	@GetMapping("/500")
	String error500(Model model) {
		model.addAttribute("icon", "fa fa-exclamation-triangle");
		model.addAttribute("code", "500");
		model.addAttribute("name", "시스템오류");
		model.addAttribute("desc", "일시적인 시스템 오류입니다.<br/>문제가 지속되면 개발팀에 문의 해주세요.");
		return "error";
	}
	
//	// 로그인 페이지
//	@GetMapping("login")
//	String login() {
//		return "login";
//	}
//	
//	// ajax 로그인 / 암호변경 [npw 가 들어가는경우 암호가 변경되며 로그인된다.]
//	@PostMapping(value="login", produces="text/plain;charset=UTF-8") @ResponseBody
//	String ajaxLogin(HttpServletRequest req) {
//		return (String) Util.runtime(() -> userLoginService.doLogin(req.getParameter("ac"), req.getParameter("pw"), req.getParameter("npw"), req.getRemoteAddr(), userService));
//	}
//	
//	// 로그인 페이지
//	@PostMapping(value="logout", produces="text/plain;charset=UTF-8") @ResponseBody
//	String ajaxLogout(HttpServletResponse res) {
//		userService.setUser(null);
//		return "";
//	}
}
