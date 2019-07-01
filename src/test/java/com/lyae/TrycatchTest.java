package com.lyae;

import javassist.expr.NewArray;

public class TrycatchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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

	}

}
