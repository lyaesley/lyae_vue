package com.lyae;

import java.io.IOException;

import javassist.expr.NewArray;

public class TrycatchTest {

	public static void method1() throws IOException {
		System.out.println("메서드1 시작");
		throw new IOException("오류발생");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("메인시작");
//		method1();
		
		try {
			method1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("캐치");
			e.printStackTrace();
		}
		
		System.out.println("메인종료");
/*
		for(int i = 0; i < 10; i++){
			try {
				System.out.println("try 시작 : " + i);
				
				if(i==2){
					System.out.println("exception/" + i);
					throw new Exception();
				}
				
				if(i==5) {
					System.out.println("555" + i);
					continue;
				}
				
				System.out.println("try 마지막 : " + i);
			} catch (Exception e) {
				System.out.println("catch : " + i);
			}
			System.out.println("for문 : " + i);
		}
*/
	}

}
