package com.lyae.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.lyae.util.UrlParameterStringBuilder;
import com.lyae.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieService {

	@Value("${naver.movie.clientId}")
	private String CLIENT_ID;
	
	@Value("${naver.movie.clientSecret}")
	private String CLIENT_SECRET;
	
	@Value("${naver.movie.sendUrl}")
	private String SEND_URL; 
	
	public final static String CHARSET = "UTF-8";
	
	public void search(HttpServletRequest req, Model model) {
		log.debug("검색어 : " + req.getParameter("query"));
		String query = req.getParameter("query");
		model.addAttribute("query", query);
	}

	public String apiMovieSearch(HttpServletRequest req, Model model) {

		String query = req.getParameter("query");
		if (query == null || "".equals(query)){
			return "/movie/list";
		}
		model.addAttribute("query", query);
		
		try {
			final String reqData = new UrlParameterStringBuilder(1024, CHARSET)
					.addParam("query", query 	).build();
			
			String resData = WebUtil.naverPost(new URL(SEND_URL), getClientInfo(), (e, f) -> {
				// 토큰
				f.write(reqData);
			}, CHARSET);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Map<String,String> getClientInfo(){
		
		Map<String,String> client = new HashMap<String,String>();
		client.put("X-Naver-Client-Id", CLIENT_ID);
		client.put("X-Naver-Client-Secret", CLIENT_SECRET);
		
		return client;
	}

	@Test
	public void abc() throws Exception {
		
		
		System.out.println(URLEncoder.encode("범죄도시",CHARSET ));
		URL url = new URL("https://openapi.naver.com/v1/search/movie.json?query=%EB%B2%94%EC%A3%84%EB%8F%84%EC%8B%9C");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("X-Naver-Client-Id", "K7zmP3v4JgM19FZIalYy");
		conn.setRequestProperty("X-Naver-Client-Secret", "Op7rz5s3F1");
		
		StringBuilder sb = new StringBuilder();
		
		try (InputStream is = conn.getInputStream() ; InputStreamReader isr = new InputStreamReader(is, CHARSET) ; BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		}
		
		System.out.println(sb);
	}
	

}
