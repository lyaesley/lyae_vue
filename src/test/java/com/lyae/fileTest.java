package com.lyae;

import java.io.File;
import java.io.IOException;

public class fileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File testFile = new File("D:\\test.txt");
		
		if (testFile.exists()){
			System.out.println("있음");
		}else{
			System.out.println("없음");
			try {
				testFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(testFile.getAbsolutePath());
	}

}
