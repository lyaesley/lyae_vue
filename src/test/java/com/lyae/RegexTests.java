package com.lyae;

import java.util.regex.Pattern;

public class RegexTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://api.football-data.org/v1/teams/81";
		String url2 = "http://api.football-data.org/v1/teams/81/players";
		String regex = "^(?i)(http)\\S+.(?i)(teams/)\\d{1,}$";
		String regex2 = "^(?i)(http)\\S+.(?i)(teams/)\\d{1,}(?i)(/players)$";
		
		
		
		String a = "1 2 3 4 5";
		
		System.out.println(a.replaceAll("\\s", ""));
		System.out.println(Pattern.matches(regex, url));
		System.out.println(Pattern.matches(regex2, url2));
		
		String b = "(<br>|<br/>|<br />)"; 
		System.out.println("이건<br/>어떻지<br>ㅎㅎ".replaceAll(b, "\r\n"));
		
		System.out.println( "<javascript>".replaceAll("(?i)javascript", "1"));
		
		System.out.println("[화끈딜]동해물과 ".replaceAll("[화끈딜]", "특가몰"));
		
		System.out.println("-------------------------------------------------");
		
		String pwd = "S~`!@#$%^*()_-+={}[]>.?/9";
		String pwd2 = "S~`!@#$%^*()_-+=9";
		String pwd3 = "S~'!@#$%^*()_-+=9";
		boolean result = Pattern.compile("^(?=.*[,&:;\\\\|'\"<]).*$").matcher(pwd2).matches();
		
		System.out.println(result);
		
		System.out.println("-------------------------------------------------");
		
		System.out.println("안녕 난 [이름] 이야".replaceAll("\\[이름\\]", "이준영"));
		
		
	}
 
}
