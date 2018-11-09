package com.lyae;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class MapTest {

//	@Test
	public void mapTest(){
		HashMap<String,String> one = new HashMap<String,String>();
		HashMap<String,String> two = new HashMap<String,String>();
		HashMap<String,String> three = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listOne = new ArrayList<HashMap<String,String>>();
		one.put("model", "a");
		one.put("cnt", "1");
		one.put("month", "201601");
		two.put("model", "b");
		two.put("cnt", "2");
		two.put("month", "201601");
		three.put("model", "c");
		three.put("cnt", "3");
		three.put("month", "201601");
		listOne.add(one);
		listOne.add(two);
		listOne.add(three);
		System.out.println(listOne);
		System.out.println(one.get("abc"));
		System.out.println(one.get("model"));
		
		HashMap<String,String> four = new HashMap<String,String>();
		HashMap<String,String> five = new HashMap<String,String>();
		HashMap<String,String> six = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listTwo = new ArrayList<HashMap<String,String>>();
		four.put("model", "b");
		four.put("cnt", "4");
		four.put("month", "201602");
		five.put("model", "c");
		five.put("cnt", "5");
		five.put("month", "201602");
		six.put("model", "d");
		six.put("cnt", "6");
		six.put("month", "201602");
		listTwo.add(four);
		listTwo.add(five);
		listTwo.add(six);
		System.out.println(listTwo);
		
		HashMap<String,String> seven = new HashMap<String,String>();
		HashMap<String,String> eight = new HashMap<String,String>();
		HashMap<String,String> nine = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listThree = new ArrayList<HashMap<String,String>>();
		seven.put("model", "c");
		seven.put("cnt", "7");
		seven.put("month", "201603");
		eight.put("model", "d");
		eight.put("cnt", "8");
		eight.put("month", "201603");
		nine.put("model", "e");
		nine.put("cnt", "9");
		nine.put("month", "201603");
		listThree.add(seven);
		listThree.add(eight);
		listThree.add(nine);
		System.out.println(listThree);
		ArrayList<HashMap<String, String>> listadd = new ArrayList<HashMap<String,String>>();
		
		listadd.addAll(listOne);
		listadd.addAll(listTwo);
		listadd.addAll(listThree);
		System.out.println("listadd : " + listadd);
		
		ArrayList<ArrayList<HashMap<String, String>>> allStat = new ArrayList<ArrayList<HashMap<String, String>>>();
		allStat.add(listOne);
		allStat.add(listTwo);
		allStat.add(listThree);

		ArrayList<HashMap<String, String>> all = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> allMap;
		for(ArrayList<HashMap<String,String>> listMonth : allStat){
			for(HashMap<String,String> listNode : listMonth){
				String model = listNode.get("model");
				String month = listNode.get("month");
				String cnt = listNode.get("cnt");
				
				//모델 유무 확인하기 위함
				boolean addYn = true;
				for (HashMap<String,String> workAllMap: all){
					if(workAllMap.containsValue(model)){
						workAllMap.put(month, cnt);
						addYn = false;
					}
				}
				//기존에 없는 모델 일 경우 추가
				if(addYn){
					allMap = new HashMap<String,String>();
					allMap.put("model", model);
					allMap.put(month, cnt);
					all.add(allMap);
				}
			}	
		}
		System.out.println("all : " + all);
	}

	
	public static void main(String[] args) {
		new MapTest().mapTest();
	}

}
