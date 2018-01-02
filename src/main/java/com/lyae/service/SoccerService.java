package com.lyae.service;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyae.model.LeagueTable;
import com.lyae.model.TeamFixtures;
import com.lyae.model.TeamPlayers;
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
	@Value("${api.soccer.key}") private String SOCCER_KEY;
	public final static String UTF8 = "UTF-8";
	
	public String seasonsList(HttpServletRequest req, Model model) {
		
		WebResult<String> result = null;
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).setHeader("X-Auth-Token", SOCCER_KEY).get();
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
		param.remove("_links");
	}
	
/**
 * API 
 * 
 */
	
	public void mappingUrlFun(String url) {
		if (url.endsWith("leagueTable")) {
			
		}
		
	}
	
	public void leagueTable() {
		
	}

	public String api_soccer(HttpServletRequest req, Model model) {
		
		final String REGEX_TEAMS = "^(?i)(http|https)\\S+.(?i)(teams/)\\d{1,}$";
		final String REGEX_TEAMSPLAYERS = "^(?i)(http|https)\\S+.(?i)(teams/)\\d{1,}(?i)(/players)$";
		final String REGEX_TEAMSFIXTURES = "^(?i)(http|https)\\S+.(?i)(teams/)\\d{1,}(?i)(/fixtures)$";
		
		String url = req.getParameter("url");
		if (url == null || "".equals(url)){
			url=SEASONS;
		}
		
		log.info("url : " + url);
		
		WebResult<String> webResult = null;
		
		try {
			webResult = new WebUtil(new URL(url), UTF8 ).setHeader("X-Auth-Token", SOCCER_KEY).get();

//			작업시작 API URL 확인하고 해당 URL에 맞는 분기 처리
			ObjectMapper objectMapper = new ObjectMapper();
			if (url.endsWith("leagueTable")) { //	http://api.football-data.org/v1/soccerseasons/444/leagueTable
				//LeagueTable
				log.info("API CHECK URL : leagueTable");
				String node =objectMapper.readTree(webResult.getData()).get("standing").toString();
				List<LeagueTable> result = ConvUtil.toListClassByJsonObject(node,LeagueTable.class);
				model.addAttribute("mapping", "leagueTable");
				model.addAttribute("result", result);
				log.info("leagueTable : " + result);
			} else if (url.equals(SEASONS)) { //	http://api.football-data.org/v1/soccerseasons
				//SeasonsList
				log.info("API CHECK URL : seasonsList");
				List<Map<String, Object>> result =ConvUtil.toListByJsonObject(webResult.getData()); 
				result.stream().forEach(map -> {
					get_links(map);
					map.remove("id");
					map.remove("currentMatchday");
					map.remove("numberOfMatchdays");
					map.remove("numberOfTeams");
					map.remove("numberOfGames");
					map.remove("lastUpdated");
//					map.remove("_links");
				});
				model.addAttribute("mapping", "seasonsList");
				model.addAttribute("result", result);
			} else if (url.matches(REGEX_TEAMS)) { //	http://api.football-data.org/v1/teams/81
				// Teams
				log.info("API CHECK URL : teams");
				Map<String,Object> result = ConvUtil.toMapByJsonObject(webResult.getData());
				get_links(result);
				log.info(result.toString());
				model.addAttribute("mapping", "teamsInfo");
				model.addAttribute("result", result);
			} else if (url.matches(REGEX_TEAMSPLAYERS)) { //		http://api.football-data.org/v1/teams/81/players
				// TeamPlayers
				log.info("API CHECK URL : teamsPlayers");
				String node =objectMapper.readTree(webResult.getData()).get("players").toString();
				List<TeamPlayers> result = ConvUtil.toListClassByJsonObject(node,TeamPlayers.class);
				result = result.parallelStream().sorted((a,b) -> a.getNumber()-b.getNumber()).collect(Collectors.toList());
//				result = result.parallelStream().sorted(Comparator.comparing(TeamPlayers::getNumber)).collect(Collectors.toList());
				model.addAttribute("mapping", "teamsPlayers");
				model.addAttribute("result", result);
				log.info("teamsPlayers : " + result);
			} else if (url.matches(REGEX_TEAMSFIXTURES)) { //		http://api.football-data.org/v1/teams/65/fixtures
				// TeamFixtures
				log.info("API CHECK URL : teamsFixtures");
				String node =objectMapper.readTree(webResult.getData()).get("fixtures").toString();
				List<TeamFixtures> result = ConvUtil.toListClassByJsonObject(node,TeamFixtures.class);
				model.addAttribute("mapping", "teamsFixtures");
				model.addAttribute("result", result);
				log.info("teamsFixtures : " + result);
				
			} else {
				log.info("default");
				url=SEASONS;
				webResult = new WebUtil(new URL(url), UTF8 ).setHeader("X-Auth-Token", SOCCER_KEY).get();
				List<Map<String, Object>> result =ConvUtil.toListByJsonObject(webResult.getData()); 
				result.stream().forEach(map -> {
					get_links(map);
					map.remove("id");
					map.remove("currentMatchday");
					map.remove("numberOfMatchdays");
					map.remove("numberOfTeams");
					map.remove("numberOfGames");
					map.remove("lastUpdated");
//					map.remove("_links");
				});
				model.addAttribute("mapping", "seasonsList");
				model.addAttribute("result", result);
			}
			
//			작업종료
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String api_seasonsList(HttpServletRequest req, Model model) {
		
		String url = req.getParameter("url");
		if (url == null || "".equals(url)){
			url=SEASONS;
		}
		
		log.info("url : " + url);
		
		WebResult<String> result = null;
		String jsonResult = "";
		
		try {
			result = new WebUtil(new URL(url), UTF8 ).setHeader("X-Auth-Token", SOCCER_KEY).get();
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

	
	public void test_seasonsList() {
		
		WebResult<String> webResult = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			webResult = new WebUtil(new URL("http://api.football-data.org/v1/soccerseasons/445/leagueTable"), UTF8 ).setHeader("X-Auth-Token", SOCCER_KEY).get();
			String node = objectMapper.readTree(webResult.getData()).get("standing").toString();
			log.info("result : " + webResult.getData());
			log.info("node : " + node);
			log.info("이거 : "+ ConvUtil.toListClassByJsonObject(node,LeagueTable.class));
//			log.info("저거 : "+ new ObjectMapper().readValue(node, new TypeReference<List<LeagueTable>>(){}));
			
			
//			list.stream().forEach(map -> {
////				map.put("리그테이블", new Picker(map).get("_links").get("leagueTable").get("href").toString() );
////				map.put("경기기록", new Picker(map).get("_links").get("fixtures").get("href").toString() );
////				map.put("팀정보", new Picker(map).get("_links").get("teams").get("href").toString() );
//				
//				for (Map.Entry<String, Object> fnode : ((Map<String, Object>) map.get("_links")).entrySet()) {
//					for (Map.Entry<String, Object> snode : ((Map<String, Object>) fnode.getValue()).entrySet()) {
//						map.put(fnode.getKey(), snode.getValue());
//					};
//				};
//				
//				map.remove("_links");
//			});
//			log.info("시즌리스트 2: " + leagueTable.toString());
			
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
