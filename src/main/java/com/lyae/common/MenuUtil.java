package com.lyae.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author 이준영
 * 메뉴 유틸 
 * 2017-08-02
 *
 */
public class MenuUtil {

	/**
	 * 모든 메뉴를 가져옴.
	 * @return
	 */
	public static List<Menu> getMenuList(){
		List<Menu> list = new ArrayList<MenuUtil.Menu>();
		
		return list;
	}
	
	@Data @Getter @Setter @ToString
	public static class Menu implements Comparable<Menu>{
		String url;
		String name;
		String desc;
		int order;
		
		@Override
		public int compareTo(Menu o) {
			int rv = this.order - o.order;
			return rv != 0 ? rv : this.name.compareTo(o.name);
		}
		
		
	}
}
