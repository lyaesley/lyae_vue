package com.lyae.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lyae.dao.BoardDao;

@Service
public class ImageBoardService {
	
	@Autowired BoardDao boardDao;
//	@Value("${path.root.imagepath}") String ROOTPATH;
	@Value("${path.root.imagepath}") String PICTUREPATH;
	
	public void getImgList(HttpServletRequest req){
		List<String> listImg = new ArrayList<String>(); 
		for (File file : new File(PICTUREPATH).listFiles()){
			if(!file.isHidden() && file.isFile()){
				/*
				 * 어떻게 구현할지?
				 * 1. 해당경로 이미지 파일 불러오기.
				 * 2. 하위폴더도 카테고리로 불러오기.
				 * 3. 섬네일 생성에서 미리보기 보여주기
				 * */
				listImg.add("/pic/"+file.getName());
			}else if (!file.isHidden() && file.isDirectory()){
				listImg.add("/pic/"+file.getName());
			}
				
			
		}
		req.setAttribute("listImg", listImg);
	}
	
}
