package com.lyae.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lyae.util.ConvUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(path="/file")
@RestController @Slf4j
public class FileController {
	@Value("${file.upload-dir}") String uploadDir;
	
	@RequestMapping(path="/upload")
	public String Upload(@RequestParam("files") List<MultipartFile> files, MultipartHttpServletRequest multiRes) {
		log.info("####### File Upload Start #######");
		List<MultipartFile> nodes = multiRes.getFiles("files");
		List<UploadResponse> resList = new ArrayList<>();
		if (files.size() > 0) {
			for(MultipartFile file : files) {
				try{
					UploadResponse res = new UploadResponse();
					//중복파일 패스
					if(Files.exists(Paths.get(uploadDir, file.getOriginalFilename()))) {
						continue;
					};
					Files.copy(file.getInputStream(), Paths.get(uploadDir, file.getOriginalFilename()));
					res.setFileName(file.getOriginalFilename());
					res.setFileSize(file.getSize());
					res.setFileContentType(file.getContentType());
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
	
	@RequestMapping(path="/uploadOne")
	public String UploadOne(@RequestParam("file") MultipartFile file) {
		
		List<UploadResponse> resList = new ArrayList<>();
		UploadResponse res = new UploadResponse();
		if (!file.isEmpty()) {
				try{
					Files.copy(file.getInputStream(), Paths.get(uploadDir, file.getOriginalFilename()));
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
