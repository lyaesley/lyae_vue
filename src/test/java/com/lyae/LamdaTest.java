package com.lyae;

import java.io.File;
import java.util.function.Function;

public class LamdaTest {

	
	@FunctionalInterface
	interface LamdaIF{
		public int abc(int a, int b);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abc(34, LamdaTest::aaab );
//		test( (e, f) ->  e + f );
		test();
		
		
		
		new File("어떤경로").listFiles((f) -> f.isDirectory());
		//.listFiles(File::isDirectory);
		
		
		
	}
	
	public static void abc(int aa, Function<Integer, Integer> fn) {
		System.out.println(fn.apply(aa));
	}
	
	public static int aaa(int a, int b) {
		return a + b * a * b;
	}

	public static int aaab(int a) {
		return a * 34234;
	}

	
	public static void test(){
		
		LamdaIF lam = LamdaTest::aaa; //= (a, b) -> a* b; 
		
		System.out.println(lam.abc(8, 2));
	}
	
}
