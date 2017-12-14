package com.lyae.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * json 형태의 스트링을 만들어주는 빌더
 */
public class UrlParameterStringBuilder {
	
	// 여기에 데이터를 쌓아두었다가 출력한다.
	final StringBuilder data;
	// 캐릭터셋
	final String charset;
	// 첫번째 값 여부
	boolean isFirstItem = true;
	
	/**
	 * 아이템을 더합니다.<br>
	 * 중복을 처리하지 않습니다. 
	 * @param name
	 * @param value
	 */
	public UrlParameterStringBuilder addParam(String name, String value) {
		if (isFirstItem) {
			isFirstItem = false;
		} else {
			data.append('&');
		}
		data.append(name).append("=");
		if (value != null) {
			try {
				data.append(URLEncoder.encode(value, charset).replace("+", "%20"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		} else {
			data.append("");
		}
		return this;
	}
	
	
	/**
	 * json을 빌드합니다.<br>
	 * 여러번 사용할 수 없습니다.
	 * @return
	 */
	public String build() {
		return data.toString();
	}
	
	// ============================================================
	// - 생성자/내부함수 (건들지 마세요)
	// ============================================================
	
	/** 생성자를 만듭니다. */
	public UrlParameterStringBuilder(String charset) {
		data = new StringBuilder(1024);
		this.charset = charset;
	}
	
	/** 쓰기 메모리 단위를 지정하여 생성자를 만듭니다. */
	public UrlParameterStringBuilder(int capacity, String charset) {
		data = new StringBuilder(capacity);
		this.charset = charset;
	}
	
	@Override
	public String toString() {
		return build();
	}
}
