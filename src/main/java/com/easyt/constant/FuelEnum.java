package com.easyt.constant;

public enum FuelEnum {

	DIESEL("DIESEL");
	
	private String desc;
	
	private FuelEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}