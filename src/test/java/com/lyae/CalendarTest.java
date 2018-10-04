package com.lyae;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
	
	public static void main(String args[]){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("M"); 
		System.out.println(cal.getTime());
		
		System.out.println(cal.get(Calendar.YEAR));
		System.out.println(cal.get(Calendar.MONTH)+1);
		System.out.println(cal.get(Calendar.DATE));
		
		System.out.println(cal.get(Calendar.YEAR) +"년"+cal.get(Calendar.MONTH) +"월"+ cal.get(Calendar.DATE));
		System.out.println(cal.get(Calendar.YEAR) +"년"+(cal.get(Calendar.MONTH)+2) +"월"+ cal.get(Calendar.DATE));
		
		cal.set(Calendar.DATE, 1);
		System.out.println("이거 : " + cal.get(Calendar.DATE));
		System.out.println("이거2 : " + cal.get(Calendar.MONTH));
		System.out.println(dateFormat.format(cal.getTime()));
		cal.add(Calendar.HOUR, 3);
		System.out.println(dateFormat.format(cal.getTime()));
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DATE, 5);
		
		System.out.println("DAY_OF_WEEK : "+cal2.get(Calendar.DAY_OF_WEEK)); // 요일 1:일 2: 월 .. 7: 토
		System.out.println("DAY_OF_MONTH : "+cal2.get(Calendar.DAY_OF_MONTH)); // 요일 1:일 2: 월 .. 7: 토
		System.out.println("WEEK_OF_MONTH : "+cal2.get(Calendar.WEEK_OF_MONTH)); //월의몇번째 주?
		System.out.println("DAY_OF_WEEK_IN_MONTH : "+cal2.get(Calendar.DAY_OF_WEEK_IN_MONTH)); // 이번달의 몇번째 요일인지?
		System.out.println(Calendar.SUNDAY);
		
		if (Calendar.SUNDAY == cal2.get(Calendar.DAY_OF_WEEK) && cal2.get(Calendar.DAY_OF_MONTH) <= 7) {
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy 년 MM월 dd일 ");
		System.out.println(sdf.format(today));
		
		System.out.println("=================");
		String ymd = "20170829";
		System.out.println(ymd);
		String eYmd = String.valueOf( (Integer.parseInt(ymd) + 1) );
		System.out.println("eYmd : " + eYmd);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.DATE, Integer.parseInt(ymd.substring(6)));
		cal3.set(Calendar.MONTH, Integer.parseInt(ymd.substring(4,6)) - 1);
		cal3.set(Calendar.YEAR, Integer.parseInt(ymd.substring(0, 4)));
		
		System.out.println("이거 확인 : " + cal3.get(Calendar.YEAR) +"년"+(cal3.get(Calendar.MONTH )+1) +"월"+ cal3.get(Calendar.DATE));

		cal3.add(Calendar.DATE, 31);
		System.out.println(cal3.get(Calendar.YEAR) +"년"+(cal3.get(Calendar.MONTH )+1) +"월"+ cal3.get(Calendar.DATE));
		
		System.out.println("=================");
		
		Calendar cal4 = Calendar.getInstance();
		System.out.println(cal4.getTime());
		
		Date today2 = new Date();
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
		System.out.println(sdf2.format(today));
		
		String aa = "20180514" + sdf2.format(today);
		System.out.println(aa);
		
		
	}
	
	
}
