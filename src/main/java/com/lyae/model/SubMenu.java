package com.lyae.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class SubMenu {
	
	private String parentAddr;
	private String name;
	private String desc;
 	private String faicon;
 	private int order;
}
