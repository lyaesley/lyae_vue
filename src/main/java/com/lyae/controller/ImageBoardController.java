package com.lyae.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.menu.Menu;
import com.lyae.service.ImageBoardService;

@Controller
@RequestMapping("/board/image")
@Menu(name="이지미 게시판", desc="이미지 게시판 리스트", icon="far fa-images")
public class ImageBoardController {
	@Autowired ImageBoardService imageBoardService;
	
	@Menu(name="이미지게시판", desc="사진첩", order=1)
	@GetMapping("/list")
	public String list(HttpServletRequest req, Model model) throws Exception{
		System.out.println("이미지게시판 콜");
		imageBoardService.getImgList(req);
		return "imageBoard/list";
	}
	
	@GetMapping("/list_back")
	@Menu(name="이미지게시판", desc="사진첩", order=2)
	public String list_back(HttpServletRequest req, Model model) throws Exception{
		System.out.println("이미지게시판 aop 테스트테스트");
		imageBoardService.getImgList_back(req);
		return "board/test";
	}
	
	@GetMapping("/{filename}")
	public String view(@PathVariable String filename,  Model model){
		return "imageBoard/view";
	}
	
	@AfterReturning(pointcut = "imageBoard()") //적용 안됨. advice 는 @Aspect 클래스 내에 선언되어야 작동? (추후확인필요)
	public void test() {
		System.out.println("aop 테스트");
	}
}
