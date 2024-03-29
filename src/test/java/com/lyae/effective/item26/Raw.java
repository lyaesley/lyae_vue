package com.lyae.effective.item26;

import java.util.*;

// 코드 26-4 런타임에 실패한다. - unsafeAdd 메서드가 로 타입(List)을 사용 (156-157쪽)
public class Raw {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		unsafeAdd(strings, Integer.valueOf(42));
		String s = strings.get(0); // 컴파일러가 자동으로 형변환 코드를 넣어준다.


	}

	private static void unsafeAdd(List list, Object o) {
		list.add(o);
	}

	static int numElementsInCommon(Set<?> s1, Set<?> s2) {
		int result = 0;
		for (Object o1 : s1) {
			if (s2.contains(o1)) {
				result++;
			}
		}
		return result;
	}
	
	static void abc(List<?> list) {
		//Collection<?> 에는 (null 외에는) 어떤 원소도 넣을 수 없다.
		//list.add(1);  //컴파일 오류
		//list.add("스트링"); //컴파일 오류
		list.add(null);
	}
}
