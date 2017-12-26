package com.lyae.service;

import static org.hamcrest.CoreMatchers.instanceOf;

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
//				map.put("리그테이블", new Picker(map).get("_links").get("leagueTable").get("href").toString() );
//				map.put("경기기록", new Picker(map).get("_links").get("fixtures").get("href").toString() );
//				map.put("팀정보", new Picker(map).get("_links").get("teams").get("href").toString() );
				get_links(map);
				map.remove("_links");
			});
			
			model.addAttribute("seasonsList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void get_links(Map<String,Object> param) {
		for (Map.Entry<String, Object> fnode : ((Map<String, Object>) param.get("_links")).entrySet()) {
			for (Map.Entry<String, Object> snode : ((Map<String, Object>) fnode.getValue()).entrySet()) {
				param.put(fnode.getKey(), snode.getValue());
			};
		};
	}
	
	public void test_seasonsList() {
		
		WebResult<String> result = null;
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).get(); 
			List<Map<String, Object>> list =ConvUtil.toListByJsonObject(result.getData()); 
//			System.out.println("시즌리스트 1: " + list.toString());
				
			list.stream().forEach(map -> {
//				map.put("리그테이블", new Picker(map).get("_links").get("leagueTable").get("href").toString() );
//				map.put("경기기록", new Picker(map).get("_links").get("fixtures").get("href").toString() );
//				map.put("팀정보", new Picker(map).get("_links").get("teams").get("href").toString() );
				
				for (Map.Entry<String, Object> fnode : ((Map<String, Object>) map.get("_links")).entrySet()) {
					for (Map.Entry<String, Object> snode : ((Map<String, Object>) fnode.getValue()).entrySet()) {
						map.put(fnode.getKey(), snode.getValue());
					};
				};
				
				map.remove("_links");
			});
			
			System.out.println("시즌리스트 2: " + list.toString());
			
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
	
	
/**
 * API 
 * 
 */
	
	public String api_seasonsList(HttpServletRequest req, Model model) {
		
		WebResult<String> result = null;
		String jsonResult = "";
		
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).get();
			List<Map<String, Object>> list =ConvUtil.toListByJsonObject(result.getData()); 
			list.stream().forEach(map -> {
				get_links(map);
				map.remove("_links");
			});
			jsonResult = ConvUtil.toJsonObjectByClass(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonResult;
	}

}
