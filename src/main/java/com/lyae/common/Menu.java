package com.lyae.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Menu {

	/**
	 * 이름
	 *@return 
	 */
	String name() default "이름 추가";
	
	/**
	 * 설명
	 * @return
	 */
	String desc() default "설명 추가";
	
	/**
	 *  font awesome 아이폰
	 */
	String icon() default "fa fa-minus";
	/**
	 * 메뉴 정렬 순서
	 * 기본값 0, 오름차순 정렬
	 * @return
	 */
	int order() default 0;
}
