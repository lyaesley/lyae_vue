package com.lyae.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lyae.service.ImageBoardService;
import com.lyae.util.ConvUtil;
import com.lyae.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(path="/file")
@RestController @Slf4j
public class FileController {
	@Value("${path.upload.img}") String pathUploadImg;
	@Autowired ImageBoardService imageBoardService;
	
//	@RequestMapping(path="/img/upload")
	public String Upload(@RequestParam("files") List<MultipartFile> mtFiles, MultipartHttpServletRequest multiRes) throws Exception {
		log.info("####### File Upload Start #######");
		List<MultipartFile> nodes = multiRes.getFiles("files");
		List<UploadResponse> resList = new ArrayList<>();
		if (mtFiles.size() > 0) {
			for(MultipartFile mtFile : mtFiles) {
				try{
					UploadResponse res = new UploadResponse();
					//중복파일 패스
					if(Files.exists(Paths.get(pathUploadImg, mtFile.getOriginalFilename()))) {
						continue;
					};
					Files.copy(mtFile.getInputStream(), Paths.get(pathUploadImg, mtFile.getOriginalFilename()));
					File file = new File(Paths.get(pathUploadImg, mtFile.getOriginalFilename()).toString());
					imageBoardService.makeThumbnail(file);
					res.setFileName(mtFile.getOriginalFilename());
					res.setFileSize(mtFile.getSize());
					res.setFileContentType(mtFile.getContentType());
//					res.setAttachmentUrl("http://localhost:8080/"+file.getOriginalFilename() );
					res.setOrignPath("/pic/"+res.getFileName());
					res.setThumPath("/pic/thumb/"+res.getFileName());
					resList.add(res);
					log.info("--- " + res.getFileName() + " success ---");
				}catch (IOException | RuntimeException e) {
					log.error("file upload fail", e);
					return "파일업로드 실패 로그 확인요망";
				}
			}
		}else {
			return "파일이 없음";
		}
		log.info("####### File Upload End #######");
		return  ConvUtil.toJsonObjectByClass(resList);
	}
	
	@RequestMapping(path="/img/upload")
	public String UploadImg(@RequestParam("files") List<MultipartFile> mtFiles, MultipartHttpServletRequest multiRes) throws Exception {
		log.info("####### File Upload Start #######");
		List<MultipartFile> nodes = multiRes.getFiles("files");
		List<UploadResponse> resList = new ArrayList<>();
		
		mtFiles.stream().filter( e -> !Files.exists(Paths.get(pathUploadImg, e.getOriginalFilename()))).forEach( mtFile -> {
			try {
//				//해당 경로에 파일 생성
//				Files.copy(mtFile.getInputStream(), Paths.get(pathUploadImg, mtFile.getOriginalFilename()));
				
				String fullName = mtFile.getOriginalFilename();
				File saveFile = new File(pathUploadImg + fullName);
				
				//파일명과 확장자 분리
				int index = fullName.lastIndexOf("."); 
				String fileName = fullName.substring(0, index); 
				String fileExt = fullName.substring(index + 1);
				
				//파일 rotate 작업.
				BufferedImage bufferedImage = ImageIO.read(mtFile.getInputStream());
				Rotation rotate = Util.getRotate(mtFile.getInputStream());
				if(rotate != null) {
					bufferedImage = Scalr.rotate(bufferedImage, rotate, Scalr.OP_ANTIALIAS);
				}
				
				//파일 저장
				ImageIO.write(bufferedImage, fileExt.toUpperCase(), saveFile);
				//썸네일 생성
				imageBoardService.makeThumbnail(saveFile);
				//업로드결과
				UploadResponse res = new UploadResponse();
				res.setFileName(mtFile.getOriginalFilename());
				res.setFileSize(mtFile.getSize());
				res.setFileContentType(mtFile.getContentType());
//				res.setAttachmentUrl("http://localhost:8080/"+file.getOriginalFilename() );
				res.setOrignPath("/pic/"+res.getFileName());
				res.setThumPath("/pic/thumb/"+res.getFileName());
				resList.add(res);
				log.info("--- " + res.getFileName() + " success ---");        
				
			} catch (IOException e) {
				log.error("file upload fail", mtFile.getOriginalFilename());
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		log.info("####### File Upload End #######");
		return  ConvUtil.toJsonObjectByClass(resList);
	}
	
	public void rotate(File sourceFile, File resultFile, Rotation rotation) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(sourceFile);
		BufferedImage doneImage = Scalr.rotate(bufferedImage, rotation, Scalr.OP_ANTIALIAS);
//		writeJpegImage(doneImage, resultFile, 0.75f);
	}
	
	@RequestMapping(path="/uploadOne")
	public String UploadOne(@RequestParam("file") MultipartFile file) {
		
		List<UploadResponse> resList = new ArrayList<>();
		UploadResponse res = new UploadResponse();
		if (!file.isEmpty()) {
				try{
					Files.copy(file.getInputStream(), Paths.get(pathUploadImg, file.getOriginalFilename()));
					res.setFileName(file.getOriginalFilename());
					res.setFileSize(file.getSize());
					res.setFileContentType(file.getContentType());
					res.setAttachmentUrl("http://localhost:8080/"+file.getOriginalFilename() );
					resList.add(res);
				}catch (IOException | RuntimeException e) {
					log.error("file upload fail", e);
					return "파일업로드 실패 로그 확인요망";
				}
		}else {
			return "파일이 없음";
		}
		return  ConvUtil.toJsonObjectByClass(resList);
	}
	/*	BufferedImage 를 이용한 사진 회전. 사용해보진 않았음. 
    public BufferedImage rotateImageForMobile(InputStream is,int orientation) throws IOException {
        BufferedImage bi = ImageIO. read(is);
         if(orientation == 6){ //정위치
                return rotateImage(bi, 90);
        } else if (orientation == 1){ //왼쪽으로 눞였을때
                return bi;
        } else if (orientation == 3){//오른쪽으로 눞였을때
                return rotateImage(bi, 180);
        } else if (orientation == 8){//180도
                return rotateImage(bi, 270);      
        } else{
                return bi;
        }       
	 }

	  public BufferedImage rotateImage(BufferedImage orgImage,int radians) {
	        BufferedImage newImage;
	         if(radians==90 || radians==270){
	               newImage = new BufferedImage(orgImage.getHeight(),orgImage.getWidth(),orgImage.getType());
	        } else if (radians==180){
	               newImage = new BufferedImage(orgImage.getWidth(),orgImage.getHeight(),orgImage.getType());
	        } else{
	                return orgImage;
	        }
	        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
	        graphics.rotate(Math. toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
	        graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2, (newImage.getHeight() - orgImage.getHeight()) / 2);
	        graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null );
	        
	         return newImage;
	 }
	*/	
	@AllArgsConstructor // 모든 필드를 파라마미터로 가진 생성자
	@NoArgsConstructor //파라미터가 없는 생성자
    @Data
    private static class UploadResponse {
				
        private String fileName;

        private long fileSize;

        private String fileContentType;

        private String attachmentUrl;
        
        private int fix = 0;
        
    	private String orignPath;
    	private String thumPath;
    	
    }
}
