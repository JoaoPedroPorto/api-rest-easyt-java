package com.easyt.constant;

public enum DocumentCategoryEnum {

	D("D"),
	E("E");
	
	private String desc;
	
	private DocumentCategoryEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}