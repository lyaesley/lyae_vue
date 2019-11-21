package com.lyae;

import java.util.Arrays;

import lombok.Data;


public class VoTest {

	private String age;
	private String sex;
	private String name;
	private String[] a = {"1","2","3"};

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getA() {
		return a;
	}

	public void setA(String[] a) {
		this.a = a;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VoTest vo1 = new VoTest() {{
			setAge("11");
			setName("원빈");
		}};

		vo1.sex= "asdf";
		vo1.setSex("asdf");;
		
		VoTest vo2 = new VoTest() {{
			setAge("11");
			setName("원빈");
		}};
		
		String[] b = {"4"	,"5","6"};
		
		vo1.setA(b);
		
		for(String node : vo1.getA()) {
			System.out.println("vo1 : " + node);
		}
		
		for(String node : vo2.getA()) {
			System.out.println("vo2 : " + node);
		}
		
		//테스트 //마스터 커밋


		//테스트
		//text1 브랜치 커밋
	}

}
