package com.easyt.constant;

public enum SexEnum {

	MALE("MALE"),
	WOMEN("WOMEN");
	
	private String sex;
	
	private SexEnum(String sex){
		this.sex = sex;
	}
	public String getSex() {
		return sex;
	}
}