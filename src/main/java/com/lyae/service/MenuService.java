package com.lyae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyae.common.MenuUtil;
import com.lyae.common.MenuUtil.Menuu;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
	
	@Autowired MenuUtil menuUtil;
	List<Menuu> Menuu;
	
	public List<Menuu> getMenu() {
		if ( Menuu == null) {
			Menuu = menuUtil.getMenuList();
		}
		return Menuu;
	}

	public String getMenuTitle(String url) {
		for (Menuu menuu : getMenu()) {
			if ( menuu.getUrl().startsWith(url)) {
				return menuu.getName();
			}
		}
		return "";
	}
}
