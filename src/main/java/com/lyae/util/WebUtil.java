package com.lyae.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 웹 유틸입니다.
 * @author		박용서
 * @since		2017. 12. 15.
 */
public class WebUtil {
	
	URL url;
	final String charset;
	final int bufsize = 1024;
	final Map<String, String> header = new HashMap<>();
	final StringBuilder param = new StringBuilder(bufsize);
	int connectTimeout;
	int readTimeout;
	
	/**
	 * 
	 * @param url
	 * @param charset
	 */
	public WebUtil(URL url, String charset) {
		this.url = url;
		this.charset = charset;
	}
	
	/**
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public WebUtil setHeader(String key, String val) {
		header.put(key, val);
		return this;
	}
	
	public WebUtil setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}
	
	public WebUtil setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}
	
	/**
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public WebUtil addParam(String key, String val) {
		if (param.length() != 0) {
			param.append('&');
		}
		try {
			param.append(key).append('=').append(URLEncoder.encode(val, charset));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	/**
	 * GET 방식으로 보냄.
	 * @return
	 */
	public WebResult<String> get() {
		WebResult<String> res = new WebResult<>(-1, "NONE", null);
		
		if (param.length() > 0) {
			String urls = url.toString();
			int pos;
			if ((pos = urls.indexOf('#')) != -1) {
				urls = urls.substring(0, pos);
			}
			if (urls.endsWith("?")) {
				urls = urls.substring(0, urls.length() - 1);
			}
			
			try {
				this.url = new URL(urls + (urls.indexOf('?') != -1 ? '&' : '?') + param.toString());
			} catch (Exception e) {
				res.setStatus(-1);
				res.setMessage(ConvUtil.toString(e));
				return res;
			}
		}
		
		HttpURLConnection conn = null;
		try {
			conn = open();
			
			res.setData(toResultData(conn.getInputStream()));
			
			res.setStatus(conn.getResponseCode());
		} catch (Exception e) {
			try {
				res.setStatus(conn.getResponseCode());
			} catch (IOException e1) {
				res.setStatus(-1);
			}
			res.setMessage(ConvUtil.toString(e));
		}
		
		return res;
	}
	
	public WebResult<Map<String, Object>> getJsonMap() {
		WebResult<String> json = get();
		WebResult<Map<String, Object>> rv = new WebResult<>();
		rv.setStatus(json.getStatus());
		rv.setMessage(json.getMessage());
		String data = json.getData();
		if (data != null && data.length() > 2) {
			try {
				rv.setData(ConvUtil.toMapByJsonObject(data));
			} catch (Exception e) {
				rv.setStatus(-1);
				rv.setMessage("json 형태의 데이터가 아닙니다. 원본 : " + data);
			}
		} else {
			rv.setData(null);
		}
		return rv;
	}
	
	public WebResult<List<Map<String, Object>>> getJsonArray() {
		WebResult<String> json = get();
		WebResult<List<Map<String, Object>>> rv = new WebResult<>();
		rv.setStatus(json.getStatus());
		rv.setMessage(json.getMessage());
		String data = json.getData();
		if (data != null && data.length() > 2) {
			try {
				rv.setData(ConvUtil.toListByJsonObject(data));
			} catch (Exception e) {
				rv.setStatus(-1);
				rv.setMessage("json 형태의 데이터가 아닙니다. 원본 : " + data);
			}
		} else {
			rv.setData(null);
		}
		return rv;
	}
	
	/**
	 * 작성한 param대로 post를 보냄
	 * @return
	 */
	public WebResult<String> post() {
		return post((os, osw) -> {
			osw.write(param.toString());
		});
	}
	
	/**
	 * param을 무시하고 수동으로 입력하여 post를 보냄
	 * @param post
	 * @return
	 */
	public WebResult<String> post(WebPost post) {
		WebResult<String> res = new WebResult<>(-1, "NONE", null);
		
		HttpURLConnection conn = null;
		try {
			(conn = open()).setDoOutput(true);			
			
			try (OutputStream os = conn.getOutputStream() ; OutputStreamWriter osw = new OutputStreamWriter(os, charset)) {
				post.post(os, osw);
				osw.flush();
				os.flush();
			} catch (Exception e) {
				throw e;
			}
			
			res.setData(toResultData(conn.getInputStream()));
			
			res.setStatus(conn.getResponseCode());
		} catch (Exception e) {
			try {
				res.setStatus(conn.getResponseCode());
			} catch (IOException e1) {
				res.setStatus(-1);
			}
			res.setMessage(ConvUtil.toString(e));
		}
		
		return res;
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	String toResultData(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder(bufsize);
		try (InputStreamReader ir = new InputStreamReader(is, charset)) {
			int len;
			char[] buf = new char[bufsize];
			while ( (len = ir.read(buf, 0, bufsize)) > -1 ) {
				sb.append(buf, 0, len);
			}
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}
	
	public HttpURLConnection open() throws IOException {
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		header.forEach((k, v) -> { if (v != null) {conn.addRequestProperty(k, v);} } );
		if (connectTimeout > 0) {
			conn.setConnectTimeout(connectTimeout);
		}
		if (readTimeout > 0) {
			conn.setReadTimeout(readTimeout);
		}
		return conn;
	}
	
	/**
	 * 
	 * @author		박용서
	 * @since		2017. 12. 15.
	 */
	public interface WebPost {
		public void post(OutputStream os, OutputStreamWriter osw) throws Exception;
	}
	
	/**
	 * 
	 * @author		박용서
	 * @since		2017. 12. 15.
	 */
	@Data @AllArgsConstructor @NoArgsConstructor
	public class WebResult<T> {
		int status;
		String message;
		T data;
		public boolean isSuccess() {
			return status == 200;
		}
	}
	
	public static void main(String[] args) throws MalformedURLException {
		String cs = "UTF-8";
//		conn.setRequestProperty("X-Naver-Client-Id", "CLIENT_ID");
//		conn.setRequestProperty("X-Naver-Client-Secret", "CLIENT_SECRET");
		WebResult<String> asdf =new WebUtil(new URL("https://openapi.naver.com/v1/search/movie.json"), cs).setHeader("X-Naver-Client-Id","K7zmP3v4JgM19FZIalYy").setHeader("X-Naver-Client-Secret", "Op7rz5s3F1").addParam("query", "범죄도시").get(); 
		System.out.println(asdf.getData());
		System.out.println(asdf.getMessage());
		System.out.println(asdf.getStatus());
		System.out.println(asdf.isSuccess());
	}
}
