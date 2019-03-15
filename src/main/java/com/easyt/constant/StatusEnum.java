package com.easyt.constant;

public enum StatusEnum {

	INACTIVE("INACTIVE"),
	ACTIVE("ACTIVE"),
	PENDING("PENDING");
	
	private String desc;
	
	private StatusEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}