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
import org.springframework.ui.Model;

import com.lyae.dao.BoardDao;
import com.lyae.model.ImageModel;
import com.lyae.util.ConvUtil;
import com.lyae.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class ImageBoardService {
	
	@Autowired BoardDao boardDao;
//	@Value("${path.root.imagepath}") String ROOTPATH;
	@Value("${path.root.images}") String PICTUREPATH;
	
	//빠른 저장을 위한 변수.
	List<String> subMenu = new ArrayList<String>();
	
	boolean isLoad = false;
	
	public void getImgList(HttpServletRequest req) throws Exception{
		String subPath = req.getParameter("sub") != null ? req.getParameter("sub") : "";
		
		List<ImageModel> imgList = new ArrayList<>();
		System.out.println("subPath : " + subPath);
		
		for (File file : new File(PICTUREPATH+subPath).listFiles()){
			if(!file.isHidden() && file.isFile()){
				/*
				 * 어떻게 구현할지?
				 * 1. 해당경로 이미지 파일 불러오기.
				 * 2. 하위폴더도 카테고리로 불러오기.
				 * 3. 섬네일 생성에서 미리보기 보여주기
				 * */
				String fileName = file.getName();
				if(fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
					//썸네일 생성
//					썸네일이 없을경우 한번만 실행되게 해야함;;;;;;
//					if(!isLoad){
//						log.info("썸네일 생성!!");
//						makeThumbnail(file);
//						isLoad=true;
//					}
					makeThumbnail(file);

					ImageModel img = new ImageModel();
					//파일이름
					img.setFileName(fileName);
					img.setOrignPath("/pic/"+ subPath + fileName);
					img.setThumPath("/pic/"+ subPath +"/thumb/" +fileName);
					img.setFileSize(file.length() / 1024);
					//파일회전
					img.setFix(Util.getDegreeForOrientation(Util.getOrientation(file)));
					imgList.add(img);
				}
				
			}else if (!file.isHidden() && file.isDirectory() && subPath.equals("")){
				
				if(file.getName().equals("thumb")) {
					continue;
				}
				
				//하위 디렉토리 서브메뉴에 추가
				if(!subMenu.contains(file.getName())){
					subMenu.add(file.getName());
				}
			}
			
			
		}
		
		req.setAttribute("imgList", ConvUtil.toJsonObjectByClass(imgList));
		req.setAttribute("subMenu", subMenu);
		
	}
	
	public void getImgList_back(HttpServletRequest req) throws Exception{
		//subDirectiory 체크
		String subPath = req.getParameter("sub") != null ? req.getParameter("sub") : "";
		
		List<Map<String,Object>> listImg = new ArrayList<Map<String,Object>>();
		System.out.println("subPath : " + subPath);
		
		for (File file : new File(PICTUREPATH+subPath).listFiles()){
			if(!file.isHidden() && file.isFile()){
				/*
				 * 어떻게 구현할지?
				 * 1. 해당경로 이미지 파일 불러오기.
				 * 2. 하위폴더도 카테고리로 불러오기.
				 * 3. 섬네일 생성해서 미리보기 보여주기
				 * */
				String fileName = file.getName();
				if(fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
					//썸네일 생성
//					썸네일이 없을경우 한번만 실행되게 해야함;;;;;;
//					if(!isLoad){
//						log.info("썸네일 생성!!");
//						makeThumbnail(file);
//						isLoad=true;
//					}
					makeThumbnail(file);
					
					Map<String,Object> imgParam = new HashMap<String,Object>();
					//파일이름
					imgParam.put("name", "/pic/"+ subPath + fileName);
					imgParam.put("thumName", "/pic/"+ subPath +"/thumb/" +fileName);
					//파일회전
					imgParam.put("fix",Util.getDegreeForOrientation(Util.getOrientation(file)));
					
					listImg.add(imgParam);
				}
				
			}else if (!file.isHidden() && file.isDirectory() && subPath.equals("")){
				
				if(file.getName().equals("thumb")) {
					continue;
				}
				
				//하위 디렉토리 서브메뉴에 추가
				if(!subMenu.contains(file.getName())){
					subMenu.add(file.getName());
				}
			}
				
			
		}
	
		req.setAttribute("listImg", listImg);
		req.setAttribute("subMenu", subMenu);

	}
	
	//썸네일 이미지 생성
	public void makeThumbnail( File file) throws Exception {
		
		String fullName = file.getName();
		
		// 썸네일을 저장합니다. 해당경로에 thumb 폴더 생성
		String thumbDir = file.getParent() + "/thumb/";
		
		//THUMB 폴더가 존재하지 않으면 폴더 생성
		File thumbDirFile = new File(thumbDir);
		if (!thumbDirFile.exists()){
			FileUtils.forceMkdir(thumbDirFile);
		} 
		File thumbFile = new File( thumbDir + fullName);
		
		//썸네일이 존재할경우 패스
		if (thumbFile.exists()){
			System.out.println("이미있음 : " + fullName);
			return;
		}
		System.out.println("생성 : " + fullName);
		int index = fullName.lastIndexOf("."); 
		String fileName = fullName.substring(0, index); 
		String fileExt = fullName.substring(index + 1);
//		System.out.println("fileExt : " + fileExt);

		try {
			// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다. 
			BufferedImage srcImg = ImageIO.read(file); 
			
			//리사이즈 작업 시작
		    // 썸네일의 너비와 높이 입니다.
		    int dw = 250, dh = 250;
		    
		    // 원본 이미지의 너비와 높이 입니다.
		    int ow = srcImg.getWidth();
		    int oh = srcImg.getHeight();
		    
		    // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
		    int nw = ow;
		    int nh = (ow * dh) / dw;
		    
		    // 계산된 높이가 원본보다 높다면 crop이 안되므로
		    // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
		    if(nh > oh) {
		        nw = (oh * dw) / dh;
		        nh = oh;
		    }
		    // 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
//		    BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);

		    // crop된 이미지로 썸네일을 생성합니다.
//		    BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
		    
		    //리사이즈 작업 종료
			
		    BufferedImage destImg = Scalr.resize(srcImg, 300);
		    
			// 높이 비율에 맞춰서 썸네일 생성
//			BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 250);
			
			// 넓이 비율에 맞춰서 썸네일 생성
//			BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 250);
			
			
			ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
		} catch(Exception e) {
			System.out.println("오류 : " + fullName);
			e.printStackTrace();
		}
	}
}
