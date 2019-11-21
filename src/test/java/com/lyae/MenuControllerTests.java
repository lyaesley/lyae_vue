package com.lyae;

import java.lang.reflect.Method;
import java.util.List;

import com.lyae.menu.MenuUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.lyae.menu.MenuService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuControllerTests {
	
	@Autowired MenuUtil menuUtil;
//	static List<String> PATH;
	
//	@Value("#{'${path.controller}'.split(',')}")
//	public void setPath(List<String> val) {
//		MenuControllerTests.PATH = val;
//	}
	
	@Test
	public void test() {
//		ReflectionTestUtils.setField(menuUtil, "PATH", PATH);

		System.out.println("테스트");
		menuUtil.test();
	}
	
}
