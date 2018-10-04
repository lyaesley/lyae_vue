package com.lyae;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class sortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list	= new ArrayList<String>();
		
		list.add("201702");
		list.add("201705");
		list.add("201701");
		list.add("201703");
		list.add("201704");
		list.add("20170501");
		
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
		
		abc(1);
	}
	static void abc(int a){
		System.out.println("1");
		
		if( a ==10 ){
			System.out.println("10");
			return;
		}
		abc(a+1);
		System.out.println("여기는 몇번: " + a);
	}
}
