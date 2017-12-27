package com.lyae;

import java.util.regex.Pattern;

public class RegexTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://api.football-data.org/v1/teams/81";
		String url2 = "http://api.football-data.org/v1/teams/81/players";
		String regex = "^(?i)(http)\\S+.(?i)(teams/)\\d{1,}$";
		String regex2 = "^(?i)(http)\\S+.(?i)(teams/)\\d{1,}(?i)(/players)$";
		
		System.out.println(Pattern.matches(regex, url));
		System.out.println(Pattern.matches(regex2, url2));
	}

}
