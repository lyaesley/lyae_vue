package com.lyae.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.lyae.util.WebUtil;
import com.lyae.util.WebUtil.WebResult;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class SoccerService {
	
	@Value("${api.soccer}") private String SEASONS;
	public final static String UTF8 = "UTF-8";
	
	public String seasonsList(HttpServletRequest req, Model model) {
		
		WebResult<String> result = null;
		try {
			result = new WebUtil(new URL(SEASONS), UTF8 ).get();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.getData();
	}
}
