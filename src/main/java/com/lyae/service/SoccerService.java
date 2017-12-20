package com.lyae.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lyae.util.ConvUtil;
import com.lyae.util.Picker;
import com.lyae.util.Util;
import com.lyae.util.WebUtil;
import com.lyae.util.WebUtil.WebResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class SoccerService {
	
	@Value("${api.soccer}") private String SEASONS;
	public final static String UTF8 = "UTF-8";
	
	public String seasonsList(HttpServletRequest req, Model model) {
		
		WebResult<String> result = null;
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).get();
			List<Map<String, Object>> list =ConvUtil.toListByJsonObject(result.getData()); 
			list.stream().forEach(map -> {
				map.put("리그테이블", new Picker(map).get("_links").get("leagueTable").get("href").toString() );
				map.put("경기기록", new Picker(map).get("_links").get("fixtures").get("href").toString() );
				map.put("팀정보", new Picker(map).get("_links").get("teams").get("href").toString() );
				
				map.remove("_links");
			});
			
			model.addAttribute("seasonsList", list);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
public void test_seasonsList() {
		
		WebResult<String> result = null;
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).get(); 
			List<Map<String, Object>> list =ConvUtil.toListByJsonObject(result.getData()); 
			list.stream().forEach(map -> {
				
				map.put("안녕", "하세요");
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
