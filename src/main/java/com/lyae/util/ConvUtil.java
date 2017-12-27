package com.lyae.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyae.model.LeagueTable;

import lombok.extern.log4j.Log4j;

@Log4j
public class ConvUtil {
	
	final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	/** 익셉션을 자세한 스트링으로 치환합니다. */
	public static String toString(Exception e) {
		return toString(null, e);
	}
	
	public static String toString(String preMessage, Exception e) {
		StringBuilder sb = new StringBuilder();
		if (preMessage != null) {
			sb.append(preMessage).append('\n');
		}
		sb.append(e.toString()).append('\n');
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(ste).append('\n');
		}
		return sb.toString();
	}
	
	
	public static String toString(Map<String, String> map) {
		StringBuilder sb = new StringBuilder(1024);
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(", ").append(entry.getKey()).append(" : ").append(entry.getValue());
		}
		return map.size() > 0 ? sb.substring(2) : "";
	}
	
	public static String toParamString(Map<String, String> map) {
		try {
			boolean isAnd = false;
			StringBuilder sb = new StringBuilder(1024);
			
			for (Entry<String, String> entry : map.entrySet()) {
				if (isAnd) { sb.append('&'); } else { isAnd = true; }
				sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}
			
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			// 사실 일어날 수 없는 오류이다.
			log.error(toString("변환중 오류 발생 : ", e));
		}
		return null;
	}
	
	/** getParameterMap를 값이 1개인 맵으로 변경한다. */
	public static Map<String, String> toParamMap(HttpServletRequest req) {
		Map<String, String[]> map = req.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		
		for (Entry<String, String[]> entry : map.entrySet()) {
			String[] val = entry.getValue();
			if (val != null && val.length > 0) {
				result.put(entry.getKey(), val[0]);
			}
		}
		
		return result;
	}
	
	public static String toJsonObjectByClass(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static Object toClassByJsonObject(String json, Class clazz) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, clazz);
	}
	
	public static <T> T toListClassByJsonObject(String json,  Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return objectMapper.readValue(json, type);
	}
	
	public static <K, V> Map<K, V> toMapByJsonObject(String json) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, HashMap.class);
	}

	public static <K> List<K> toListByJsonObject(String json) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, ArrayList.class );
	}
	
	/** 컨버팅 : yyyy-MM-dd HH:mm:ss */
	public static String toY_M_D_H_M_S(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toYMDHMS(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toYMD(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toHMS(Date date) {
		return new SimpleDateFormat("HHmmss").format(date);
	}
	
	/** 현재 시간으로부터 밀리세컨을 계산하여 Date를 반환한다. */
	public static Date addTime(long time) {
		return new Date(System.currentTimeMillis() + time);
	}
	
	/** 현재 시간으로부터 날짜를 계산하여 Date를 반환한다. */
	public static Date addDay(long day) {
		return new Date(System.currentTimeMillis() + (day * 86400000L));
	}
	
	/** 숫자 0을 채운 스트링 만들기 */
	public static String toStringZeroFill(int unit, int val) {
		return String.format("%0"+unit+"d", val);
	}
	
	/** hex 스트링을 만든다. */
	public static String toHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	/** 바이트를 만든다 */
	public static byte[] toBytesByHexString(String hex) {
		return new BigInteger(hex, 16).toByteArray();
	}
	
	public static String toCamelHaveSnake(String snake) {
		char[] chars = snake.toCharArray();
		char[] rv = new char[chars.length];
		int len = 0;
		boolean isUpper = false;
		for (char ch : chars) {
			if (ch == '_') {
				isUpper = true;
				continue;
			}
			rv[len++] = isUpper ? Character.toUpperCase(ch) : Character.toLowerCase(ch);
			isUpper = false;
		}
		
		if (len != 0) {
			return new String(rv, 0, len);
		}
		return "";
	}
	
	public static String htmlUnescape(String text) {
		return text != null ? HtmlUtils.htmlUnescape(text.replace("&amp;", "&")) : null;
	}
	
	public static String toMasking(String text){
		String regex_hp ="^01(?:0|1[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
		String regex_birth ="^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
		String regex_jumin ="\\d\\d(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])[1-9]\\d{6}";
		String regex_name ="^[가-힣a-zA-Z\\s]{2,}";
		String regex_addr ="^\\([0-9]{5}\\).*";
		
		if ("".equals(text) || text == null){
			return text;
		}
		
		if (Pattern.matches(regex_hp, text)){
			if(text.length() == 10){
				text = text.substring(0, 3) + "***" + text.substring(6);
			}else if(text.length() == 11){
				text = text.substring(0, 3) + "****" + text.substring(7);
			}
		} else if (Pattern.matches(regex_jumin, text)){
			text = text.substring(0,7)+"******";
		} else if (Pattern.matches(regex_name, text)){
			switch(text.length()){
			case 2 : 
				text = text.substring(0,1) + "*";
				break;
			case 3 : 
				text = text.substring(0,1) + "*" + text.substring(2); 
				break;
			case 4 : 
				text = text.substring(0,1) + "**" + text.substring(3);
				break;
			case 5 : 
				text = text.substring(0,1) + "***" + text.substring(4);
				break;
			default :
				text = text.substring(0,1) + "***" + text.substring(text.length()-1);
				break;
				}
			
		} else if(Pattern.matches(regex_addr, text)){
			text = text.substring(0,7) + " **** " +  text.substring(text.length()-5);
		} else if(Pattern.matches(regex_birth, text)){
			text = text.substring(0,4) + "****";
		} else{
			text = text.substring(0,3) + "***" +  text.substring(text.length()-3);
		}
		return text;
	}
	
//	
	
	
	@Test
	public void test() {
		try {
			String regex_addr ="^\\([0-9]{5}\\).*";
			String str = "01062228988";
			System.out.println(str.substring(7));
			System.out.println(Pattern.matches(regex_addr, str));
		}
		catch (Exception e) {
			System.out.println(toString(e));
		}
	}
}
