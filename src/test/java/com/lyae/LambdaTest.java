package com.lyae;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class LambdaTest {

	 public static interface Abc {
		  public int calc(int a, int b);
	 }
	
	@FunctionalInterface
	interface LamdaIF{
		public void run(Map map);
	}
	
	@FunctionalInterface
	interface Func{
		public int run(int a , int b);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abc(34, LambdaTest::aaab );
//		test( (e, f) ->  e + f );
		test( (map) ->{
			map.put("b", "asdf");
			System.out.println("1"); 
			});
		
		calc( 6, 8, (a, b) -> {
			int result  = a-b;
			System.out.println(result);
			return result;
		});
		
//		new File("어떤경로").listFiles((f) -> f.isDirectory());
//		//.listFiles(File::isDirectory);
//		
//		new File("어떤경로").listFiles(e -> e.getName().startsWith("ML03."));
//		
//		List<File> list = Arrays.asList(new File("경로").listFiles());
//		list.stream().filter(e -> e.isDirectory()).collect(Collectors.toList());
//		
//		
		
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

	public static void calc(int a, int b, Func func) {
		System.out.println("시작");
		func.run(a, b);
		System.out.println("종료");
		
	}
	
	public static void test(LamdaIF lambda){
		
//		LamdaIF lam = LamdaTest::aaa; //= (a, b) -> a* b; 
		Map<Object, Object> map = new HashMap<Object, Object>(); 
		map.put("a", 1);
		
		lambda.run(map);
		
		map.put("a", 12345);
		
		System.out.println(map.toString());
		
	}
	
}
