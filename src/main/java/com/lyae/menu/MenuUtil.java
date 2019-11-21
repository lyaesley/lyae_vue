package com.lyae.menu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Component
public class MenuUtil {
	
	static List<String> PATH;
	
//	@Value("#{'${path.controller}'.split(',')}")
//	public void setPath(List<String> val) {
//		MenuUtil.PATH = val;
//	}
	/**
	 * 모든 메뉴를 가져옴.
	 * @return
	 */
	public List<Menuu> getMenuList() {
		
		List<Menuu> list = new ArrayList<MenuUtil.Menuu>();
		List<Menuu> listZo = new ArrayList<MenuUtil.Menuu>();
		List<Menuu> listZu = new ArrayList<MenuUtil.Menuu>();
		
		try {
				for (Method method : getAllMethodsAnnotatedWithInPackage("com.lyae.controller",Menu.class)){
					method.setAccessible(true);
					Menu menu = method.getAnnotation(Menu.class);
					if (menu != null) {
						String name = menu.name();
						String desc = menu.desc();
						String faicon = menu.icon();
						int order = menu.order();
						if (name != null && !"".equals(name.trim())) {
							//Controller 의 RequestMapping 가져옴.
							RequestMapping reqParentMap = (RequestMapping) method.getDeclaringClass().getAnnotation(RequestMapping.class);
							RequestMapping reqMap = method.getAnnotation(RequestMapping.class);
							if (reqMap != null) {
								String[] urls = reqMap.value();
								if (urls != null && urls.length > 0 && urls[0] != null && !"".equals(urls[0].trim())) {
									Menuu subMenu = new Menuu();
									subMenu.setName(name.trim());
									subMenu.setDesc(desc.trim());
									subMenu.setUrl(reqParentMap.value()[0].trim()+urls[0].trim());
									subMenu.setFaicon("fa " + faicon.trim());
									subMenu.setOrder(order);
									if (order > 0) {
										listZo.add(subMenu);
									} else if (order < 0) {
										listZu.add(subMenu);
									} else {
										list.add(subMenu);
									}
								}
							}
						}
					}
				} //end of method
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(listZo);
		Collections.sort(listZu);
		
		listZu.addAll(list);
		listZu.addAll(listZo);
		
		return listZu;
	}
	
	/*
	public List<Menuu> getMenuList() {
					
		List<Menuu> list = new ArrayList<MenuUtil.Menuu>();
		List<Menuu> listZo = new ArrayList<MenuUtil.Menuu>();
		List<Menuu> listZu = new ArrayList<MenuUtil.Menuu>();
		
		try {
			for(String classPath : PATH) {
				Class clazz = Class.forName(classPath);
				for (Method method : clazz.getDeclaredMethods()){
					method.setAccessible(true);
					Menu menu = method.getAnnotation(Menu.class);
					if (menu != null) {
						String name = menu.name();
						String desc = menu.desc();
						int order = menu.order();
						if (name != null && !"".equals(name.trim())) {
							//Controller 의 RequestMapping 가져옴.
							RequestMapping reqParentMap = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
							RequestMapping reqMap = method.getAnnotation(RequestMapping.class);
							if (reqMap != null) {
								String[] urls = reqMap.value();
								if (urls != null && urls.length > 0 && urls[0] != null && !"".equals(urls[0].trim())) {
									Menuu menuu = new Menuu();
									menuu.setName(name.trim());
									menuu.setDesc(desc.trim());
									menuu.setUrl(reqParentMap.value()[0].trim()+urls[0].trim());
									menuu.setOrder(order);
									if (order > 0) {
										listZo.add(menuu);
									} else if (order < 0) {
										listZu.add(menuu);
									} else {
										list.add(menuu);
									}
								}
							}
						}
					}
				} //end of method
			} // end of classPath
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(listZo);
		Collections.sort(listZu);
		
		listZu.addAll(list);
		listZu.addAll(listZo);
		
		return listZu;
	}
	*/
	@Data @Getter @Setter @ToString
	public static class Menuu implements Comparable<Menuu>{
		String url;
		String name;
		String desc;
		String faicon;
		int order;
		
		@Override
		public int compareTo(Menuu o) {
			int rv = this.order - o.order;
			return rv != 0 ? rv : this.name.compareTo(o.name);
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<Method> getAllMethodsAnnotatedWithInPackage(String strPackage, Class annotation){
		 Reflections reflections = new Reflections(new ConfigurationBuilder()
		            .setUrls(ClasspathHelper.forPackage(strPackage))
		            .setScanners(new MethodAnnotationsScanner()));
     
		 Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);
		 return methods;
	}
	
	public void test(){
//		Reflections reflections = new Reflections("com.lyae.controller");
		System.out.println("222");
		 Reflections reflections = new Reflections(new ConfigurationBuilder()
		            .setUrls(ClasspathHelper.forPackage("com.lyae.controller"))
		            .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Menu.class);

        System.out.println(methods.size());
	}

	public void typeAnnoTest(){
//		Reflections reflections = new Reflections("com.lyae.controller");
		 Reflections reflections = new Reflections(new ConfigurationBuilder()
		            .setUrls(ClasspathHelper.forPackage("com.lyae.controller"))
		            .setScanners(new TypeAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Menu.class);

        System.out.println(methods.size());
	}

}
