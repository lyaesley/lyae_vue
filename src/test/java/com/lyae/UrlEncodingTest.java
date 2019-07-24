package com.lyae;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

public class UrlEncodingTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String pw = "S~`!@#$%^*()_-+={}[]>.?/9";
		
		pw = URLEncoder.encode(pw, "UTF-8");
		System.out.println("0 : " + pw);
		
		pw = URLDecoder.decode(pw, "UTF-8");
		System.out.println("00 : " + pw);
		
		String epw = HtmlUtils.htmlEscape(pw);
		System.out.println("1 : " + epw);
		
		//String dpw = URLDecoder.decode(epw, "UTF-8");
		//System.out.println(dpw);
		
		System.out.println("2 : " +HtmlUtils.htmlUnescape(epw));
		
		//System.out.println("3 : " +StringEscapeUtils.escapeHtml(pw));
		
	}

}
