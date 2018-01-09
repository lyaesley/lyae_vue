package com.lyae.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lyae.util.ConvUtil;
import com.lyae.util.UrlParameterStringBuilder;
import com.lyae.util.WebUtil;
import com.lyae.util.WebUtil2;
import com.lyae.util.WebUtil.WebResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieService {

	@Value("${naver.movie.clientId}")	private String CLIENT_ID;
	@Value("${naver.movie.clientSecret}") private String CLIENT_SECRET;
	@Value("${naver.movie.sendUrl}") private String SEND_URL; 
	@Value("${movie.kobis.key}") private String KOBIS_KEY;
	@Value("${movie.kobis.boxOffice.daily}") private String BOXOFFICE_DAILY;
	public final static String CHARSET = "UTF-8";
	
	public void search(HttpServletRequest req, Model model) {
		log.debug("검색어 : " + req.getParameter("query"));
		String query = req.getParameter("query");
		model.addAttribute("query", query);
	}
	
	public void apiSearch(HttpServletRequest req, Model model) {
		String query = req.getParameter("query");
		
		if (query == null || "".equals(query)){
			return;
		}
		
		model.addAttribute("query", query);
		
		WebResult<Map<String, Object>> result;
			try {
				result = new WebUtil(new URL(SEND_URL), CHARSET).setHeader("X-Naver-Client-Id",CLIENT_ID).setHeader("X-Naver-Client-Secret", CLIENT_SECRET).addParam("query", query).getJsonMap();
				model.addAttribute("result", result.getData());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void boxOffice(HttpServletRequest req, Model model) {
		String date = req.getParameter("date");
		if (date ==null || "".equals(date)) {
			date = ConvUtil.toYMD(new Date());
		}
		
		WebResult<Map<String, Object>> result;
		
		try {
			result = new WebUtil(new URL(BOXOFFICE_DAILY), CHARSET).addParam("key", KOBIS_KEY).addParam("targetDt", "20180108").getJsonMap();
			log.info("boxOffice : " + result.getData());
			model.addAttribute("result", result.getData());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void apiMovieSearch(HttpServletRequest req, Model model) {

		String query = req.getParameter("query");
		
		if (query == null || "".equals(query)){
			return;
		}
		
		model.addAttribute("query", query);
		
		String reqData = new UrlParameterStringBuilder(1024,CHARSET)
				.addGetParam("query", query).build();
		
		String resData ="";
		try {
			resData = WebUtil2.naverGet(new URL(SEND_URL+reqData), getClientInfo(), CHARSET);
			model.addAttribute("result", ConvUtil.toMapByJsonObject(resData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Map<String,String> getClientInfo(){
		
		Map<String,String> client = new HashMap<String,String>();
		client.put("X-Naver-Client-Id", CLIENT_ID);
		client.put("X-Naver-Client-Secret", CLIENT_SECRET);
		
		return client;
	}

	
	public void abc() throws Exception {
		
		
		System.out.println(URLEncoder.encode("범죄도시",CHARSET ));
		URL url = new URL("https://openapi.naver.com/v1/search/movie.json?query=%EB%B2%94%EC%A3%84%EB%8F%84%EC%8B%9C");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("X-Naver-Client-Id", "CLIENT_ID");
		conn.setRequestProperty("X-Naver-Client-Secret", "CLIENT_SECRET");
		
		StringBuilder sb = new StringBuilder();
		
		try (InputStream is = conn.getInputStream() ; InputStreamReader isr = new InputStreamReader(is, CHARSET) ; BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		}
		
		System.out.println(sb);
	}
	
	public void testapiMovieSearch() {

		
		String query="범죄도시";
		
		String reqData = new UrlParameterStringBuilder(1024,CHARSET)
				.addGetParam("query", query).build();
		String resData = "";
		try {
			resData = WebUtil2.naverGet(new URL(SEND_URL+reqData), getClientInfo(), CHARSET);
			System.out.println(resData);
			System.out.println(ConvUtil.toMapByJsonObject(resData));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
