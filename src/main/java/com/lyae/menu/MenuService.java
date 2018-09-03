package com.lyae.menu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyae.controller.BoardController;
import com.lyae.controller.ImageBoardController;
import com.lyae.controller.MovieController;
import com.lyae.controller.SoccerController;
import com.lyae.controller.TestController;

import lombok.Data;
import lombok.Getter;

@Component
public class MenuService {
	
	@Getter
	List<MenuGroup> menuGroups;
//	MenuGroup[] menuGroups;
	
	public MenuService() {
		createMenu();
	}
	
	void createMenu() {
		menuGroups = new ArrayList<MenuGroup>();
		menuGroups.add(menu(BoardController.class));
		menuGroups.add(menu(ImageBoardController.class));
		menuGroups.add(menu(MovieController.class));
		menuGroups.add(menu(SoccerController.class));
		menuGroups.add(menu(TestController.class));
//		menuGroups.add(menu(BoardController.class, "게시판", "게시판 메뉴", "ion ion-ios-pulse-strong"));
//		menuGroups.add(menu(ImageBoardController.class, "이미지게시판", "이미지게시판", "fa fa-address-card"));
//		menuGroups.add(menu(MovieController.class, "영화", "영화 정보", "fa fa-address-book"));
//		menuGroups.add(menu(SoccerController.class, "축구", "축구 정보", "fa fa-credit-card"));
//		menuGroups.add(menu(TestController.class, "Test", "Test", "fa fa-credit-card"));
		
//		MenuGroup[] menuGroup = {
//			menu(BoardController.class),
//			menu(ImageBoardController.class),
//			menu(MovieController.class),
//			menu(SoccerController.class),
//			menu(TestController.class)
//		};
		this.menuGroups = menuGroups;
	}
	
	// 일단 전부 리턴
//		public MenuGroup[] getMyMenu(User user) {
//			if (user != null) {
//				List<MenuGroup> list = new ArrayList<>();
//				
//				for (MenuGroup menuGroup : menuGroups) {
//					// 원래 여기서는 룰을 확인해야하지만 패스
//					list.add(menuGroup);
//				}
//				
//				return list.toArray(new MenuGroup[list.size()]);
//			}
//			return new MenuGroup[0];
//		}
		
	MenuGroup menu(Class<?> clazz) {
		MenuGroup menu = new MenuGroup();
		String prePath = "";
		
		RequestMapping rmap = clazz.getAnnotation(RequestMapping.class);
		Menu menuInfo = clazz.getAnnotation(Menu.class);
		if (rmap != null && rmap.value().length > 0) {
			prePath = rmap.value()[0];
		}
		
		menu.setClazz(clazz);
		menu.setName(menuInfo.name());
		menu.setDesc(menuInfo.desc());
		menu.setIcon(menuInfo.icon());
		menu.setPurl(prePath);
		
		List<MenuItem> list = new ArrayList<MenuItem>();
		
		for (Method method : clazz.getDeclaredMethods()) {
			method.setAccessible(true);
			Menu subMenu = method.getAnnotation(Menu.class);			
			if (subMenu != null) {
				String iname = subMenu.name();
				String idesc = subMenu.desc();
				int iorder = subMenu.order();
				String iicon = subMenu.icon();
				if (iname != null && !iname.trim().equals("")) {
					GetMapping map = method.getAnnotation(GetMapping.class);
//					리퀘스트매핑, 포스트 매핑 추가해야함
//					PostMapping pmap = method.getAnnotation(PostMapping.class);
//					RequestMapping map = method.getAnnotation(RequestMapping.class);
					
					if (map != null) {
						String[] urls = map.value();
						if (urls != null && urls.length > 0 && urls[0] != null && !urls[0].trim().equals("")) {
							MenuItem item = new MenuItem();
							item.setName(iname.trim());
							item.setDesc(idesc.trim());
							item.setUrl(prePath + urls[0].trim());
							item.setOrder(iorder);
							item.setIcon(iicon);
							list.add(item);
						}
					}
				}
			}
		}
		
		menu.setItem(list.stream().sorted((x, y) -> x.order == y.order ? x.name.compareTo(y.name) : x.order - y.order).toArray(size -> new MenuItem[size]));
		
		return menu;
	}
	@Data
	public static class MenuGroup {
		Class<?> clazz;
		String purl;
		String name;
		String desc;
		String icon;
		MenuItem[] item;
	}
	
	@Data
	public static class MenuItem {
		String name;
		String desc;
		String url;
		String icon;
		int order;
	}
	
	/**
	 * 테스트용 코드
	 */
//	@Test
	public void test() {
		createMenu();
		
		for (MenuGroup mg : menuGroups) {
			System.out.println(mg.toString());
		}
	}
}
