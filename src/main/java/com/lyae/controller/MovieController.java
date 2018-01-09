package com.lyae.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyae.menu.Menu;
import com.lyae.service.MovieService;
import com.lyae.util.ConvUtil;

@Controller
@RequestMapping("/movie")
@Menu(name="영화", desc="영화정보 검색", icon="fa fa-video-camera")
public class MovieController {
	
	@Autowired
	MovieService movieService;
		
	@Menu(name="영화검색", desc="영화정보 검색", order=1)
	@GetMapping("/list")
	public String movieSearch(HttpServletRequest req, Model model) {
//		movieService.search(req, model);
		movieService.apiSearch(req, model);
		return "movie/list";
	}
	
	@Menu(name="박스오피스", desc="일별 박스오피스", order=2 )
	@GetMapping("/boxOffice")
	public String movieBoxOffice(HttpServletRequest req, Model model) {
		
		movieService.boxOffice(req, model);
		return "movie/boxOffice";
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
