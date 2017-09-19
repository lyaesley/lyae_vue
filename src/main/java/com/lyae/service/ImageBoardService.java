package com.lyae.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lyae.dao.BoardDao;
import com.lyae.util.Util;

@Service
public class ImageBoardService {
	
	@Autowired BoardDao boardDao;
//	@Value("${path.root.imagepath}") String ROOTPATH;
	@Value("${path.root.imagepath}") String PICTUREPATH;
	
	public void getImgList(HttpServletRequest req) throws Exception{
		List<Map<String,Object>> listImg = new ArrayList<Map<String,Object>>();
		for (File file : new File(PICTUREPATH).listFiles()){
			if(!file.isHidden() && file.isFile()){
				/*
				 * 어떻게 구현할지?
				 * 1. 해당경로 이미지 파일 불러오기.
				 * 2. 하위폴더도 카테고리로 불러오기.
				 * 3. 섬네일 생성에서 미리보기 보여주기
				 * */
				String fileName = file.getName();
				if(fileName.endsWith(".jpg")) {
					//썸네일 생성
//					썸네일이 없을경우 한번만 실행되게 해야함;;;;;;
//					makeThumbnail(file);
					
					Map<String,Object> imgParam = new HashMap<String,Object>();
					//파일이름
					imgParam.put("name", "/pic/" + fileName);
					imgParam.put("thumName", "/pic/THUMB_"+fileName);
					//파일회전
					imgParam.put("fix",Util.getDegreeForOrientation(Util.getOrientation(file)));
					listImg.add(imgParam);
				}
				
			}else if (!file.isHidden() && file.isDirectory()){
				System.out.println("isDir");
			}
				
			
		}
		req.setAttribute("listImg", listImg);
	}
	
	private void makeThumbnail( File file) throws Exception {
		
		String fullName = file.getName();
		int index = fullName.lastIndexOf("."); 
		String fileName = fullName.substring(0, index); 
		String fileExt = fullName.substring(index + 1);
		System.out.println("fileExt : " + fileExt);

			
		// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다. 
		BufferedImage srcImg = ImageIO.read(file); 
		
		// 높이 비율에 맞춰서 썸네일 생성
		BufferedImage destImg = Scalr.resize(srcImg, 100, 100);
		
		// 넓이 비율에 맞춰서 썸네일 생성
		//BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 150);
		
		// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
		String thumbName = file.getParent() + "/THUMB_" + fullName;
		File thumbFile = new File(thumbName);
		
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
	}
}
