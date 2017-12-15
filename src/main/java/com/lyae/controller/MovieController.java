package com.lyae.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyae.common.Menu;
import com.lyae.service.MovieService;
import com.lyae.util.ConvUtil;

@Controller
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	MovieService movieService;
		
	@Menu(name="영화검색", desc="영화정보 검색", faicon="fa-video-camera", order=10)
	@RequestMapping("/list")
	public String movieSearch(HttpServletRequest req, Model model){
//		movieService.search(req, model);
		movieService.apiMovieSearch(req, model);
		return "movie/list";
	}
	
	
	// ================================
	// - 아래는 API 입니다.
	// ================================
	// 때문에 @DevoMenu를 선언하지 마시오.
	// ================================
	
	@ResponseBody @RequestMapping("/search")
	public String apiMovieSearch(HttpServletRequest req, Model model) throws IOException{
		movieService.apiMovieSearch(req, model);
		return "업데이트중";
	}
	
}
