package com.lyae;

import java.io.IOException;

import com.lyae.util.ByteBuilder;


public class StringBuilderTest {
	static final String CHARSET = "EUC-KR";
	static final String CHARSET_UTF8 = "UTF-8";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ByteBuilder data = new ByteBuilder(4000, CHARSET);
		int fixedSize = 100;
		String acdntAddr="세종 조치원읍 침산리 고운동 1375 (세종특별차시 고운동)인데 선택이 불가능해 임의로 선택했습니다.";
		
		data.write(acdntAddr, 100);
		
		byte[] euc = acdntAddr.getBytes(CHARSET);
		System.out.println("byte size : " + euc.length);
		System.out.println( new String(euc));
		
		for(byte node : euc) {
			System.out.print( node);
		}
		
		System.out.println(" ");
		
		byte[] utf = acdntAddr.getBytes(CHARSET_UTF8);
		System.out.println("byte size : " + utf.length);
		System.out.println( new String(utf));
		for(byte node : utf) {
			System.out.print( node);
		}
		System.out.println("----");
		System.out.println("안녕하세요a".getBytes("euc-kr").length);

		String aaa = "'I'P`A'R'K'";
		System.out.println( aaa.replaceAll("'", "`"));
		
	}
}
