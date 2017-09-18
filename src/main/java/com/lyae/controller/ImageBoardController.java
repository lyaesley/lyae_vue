package com.lyae.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.service.ImageBoardService;

@Controller
@RequestMapping("/board/Image")
public class ImageBoardController {
	@Autowired ImageBoardService imageBoardService;
	@Value("{path.root.imagepath}") String ROOTPATH;
	
	@RequestMapping("list")
	public String list(Model model){
			for (File file : new File(ROOTPATH).listFiles()){
				if(!file.isHidden() && file.isFile()){
					/*
					 * 어떻게 구현할지?
					 * 1. 해당경로 이미지 파일 불러오기.
					 * 2. 하위폴더도 카테고리로 불러오기.
					 * 3. 섬네일 생성에서 미리보기 보여주기
					 * */
					file.getName();
				}
			};
			
		return "imageBoard/list";
	}
	
	@RequestMapping("/{filename}")
	public String view(@PathVariable String filename,  Model model){
		return "imageBoard/view";
	}
}
