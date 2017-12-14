package com.lyae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.qos.logback.core.net.SyslogOutputStream;

public class StreamTest {

	
	
	public static void main(String[] args) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> param2 = new HashMap<String,Object>();
		Map<String,Object> param3 = new HashMap<String,Object>();
		Map<String,Object> param4 = new HashMap<String,Object>();
		Map<String,Object> param5 = new HashMap<String,Object>();
		
		param.put("name", null);
		param.put("age", 250);
		param.put("memo", "이것은 무엇일까요??");
		
		param2.put("name", "토르");
		param2.put("age",250);
		param2.put("memo", "천둥의신?");
		
		param3.put("name", "로키");
		param3.put("age", 249);
		param3.put("memo", "구라쟁이");
		
		param4.put("name", "스파");
		param4.put("age", 251);
		param4.put("memo", "거미쟁이");
		
		param5.put("name", "닥스");
		param5.put("age", 250);
		param5.put("memo", "허세쟁이");
		
		list.add(param);
		list.add(param2);
		list.add(param3);
		list.add(param4);
		list.add(param5);
		
		System.out.println("테스트 : " +param.entrySet());
		
		Set<Entry<String,Object>> entrySet = param.entrySet();
		Iterator<Entry<String, Object>> iterator = entrySet.iterator();
		    while (iterator.hasNext()) {
		        Map.Entry entry = (Map.Entry) iterator.next();
		        System.out.println(entry.getKey().toString());
	    }
		
		System.out.println(list.toString());
		
		System.out.println( list.stream().filter( str -> str.get("name")  != null ).count() );

		List<Map<String,Object>> result = list.stream().filter( str -> str.get("name")  != null ).collect(Collectors.toList()) ;
		
		List<Map<String,Object>> result2 = list.stream().filter( str -> str.get("name")  == null ).collect(Collectors.toList()) ;
		
		List<Map<String,Object>> result3 = list.stream()
				.sorted( (a,b) -> ((Integer)a.get("age")).compareTo(((Integer)b.get("age"))) )
				.collect(Collectors.toList()) ;
		
		List<Map<String,Object>> result4 = list.stream().filter( str -> (Integer)str.get("age")  == 250).collect(Collectors.toList()) ;
		
		List<Map<String,Object>> result5 = list.stream().filter( str -> str.get("name")  != null && list.stream().filter(f -> ((Integer)f.get("age")).equals((Integer)str.get("age"))).count() >1 ).collect(Collectors.toList()) ;
		
		System.out.println(result.toString());
		System.out.println(result2.toString());
		System.out.println(result3.toString());
		System.out.println(result4.toString());
		System.out.println("555 : " + result5.toString());
		
		for( Map<String,Object> node : result){
			if(node.containsValue("로키")) System.out.println("이거 : " + node.toString());
		}
		
		
	}

}
