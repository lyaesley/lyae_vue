package com.lyae.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

/**
 * 2017-07-19 박용서 작성
 */
@Slf4j
public class WebUtil2 {
	private WebUtil2 (){}
	
	/**
	 * url에 post 데이터를 보냅니다.<br>
	 * WebPost의 OutputStream, OutputStreamWriter을 close로 닫지 마시오
	 * @param url
	 * @param post
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String post(URL url, WebPost post, String charset) throws Exception {
		URLConnection urlc = url.openConnection();
		urlc.setDoOutput(true);
		StringBuilder sb = new StringBuilder(4096);
		final int BUF_SIZE = 4096;
		char[] buf = new char[BUF_SIZE];
		
		try (OutputStream os = urlc.getOutputStream() ; OutputStreamWriter osw = new OutputStreamWriter(os, charset)) {
			post.post(os, osw);
			osw.flush();
			os.flush();
		} catch (Exception e) {
			throw e;
		}

		try (InputStream is = urlc.getInputStream() ; InputStreamReader ir = new InputStreamReader(is, charset)) {
			int len;
			while ( (len = ir.read(buf, 0, BUF_SIZE)) > -1 ) {
				sb.append(buf, 0, len);
			}
		} catch (Exception e) {
			throw e;
		}
		
		return sb.toString();
	}
	
	/**
	 * Naver Open Aip POST 전용 
	 * HTTP Header 에 Client ID와 Client Secret 추가
	 * url에 post 데이터를 보냅니다.<br>
	 * WebPost의 OutputStream, OutputStreamWriter을 close로 닫지 마시오
	 * @param url
	 * @param post
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String naverPost(URL url, Map<String,String> param, WebPost post, String charset) throws Exception {
		URLConnection urlc = url.openConnection();
		
		Iterator<Entry<String,String>> it = param.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			log.info("Client Param///// " + entry.getKey().toString() + " : " +  entry.getValue().toString());
			urlc.setRequestProperty(entry.getKey().toString(),  entry.getValue().toString());
		}
		
		urlc.setDoOutput(true);
		StringBuilder sb = new StringBuilder(4096);
		final int BUF_SIZE = 4096;
		char[] buf = new char[BUF_SIZE];
		
		try (OutputStream os = urlc.getOutputStream() ; OutputStreamWriter osw = new OutputStreamWriter(os, charset)) {
			post.post(os, osw);
			osw.flush();
			os.flush();
		} catch (Exception e) {
			throw e;
		}
		
		try (InputStream is = urlc.getInputStream() ; InputStreamReader ir = new InputStreamReader(is, charset)) {
			int len;
			while ( (len = ir.read(buf, 0, BUF_SIZE)) > -1 ) {
				sb.append(buf, 0, len);
			}
		} catch (Exception e) {
			throw e;
		}
		
		return sb.toString();
	}
	
	/**
	 * Naver Open Aip GET 전용
	 * @throws Exception 
	 *  
	 */
	public static String naverGet(URL url, Map<String,String> param, String charset) throws Exception {
		URLConnection conn = url.openConnection();
		
		Iterator<Entry<String,String>> it = param.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			log.info("Client Param///// " + entry.getKey().toString() + " : " +  entry.getValue().toString());
			conn.setRequestProperty(entry.getKey().toString(),  entry.getValue().toString());
		}
		
		StringBuilder sb = new StringBuilder();
		
		try (InputStream is = conn.getInputStream() ; InputStreamReader isr = new InputStreamReader(is, charset) ; BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		}
		
		return sb.toString();
	}


	/**
	 * 절대 close로 닫지 마시오
	 */
	public interface WebPost {
		public void post(OutputStream os, OutputStreamWriter osw) throws Exception;
	}
}
