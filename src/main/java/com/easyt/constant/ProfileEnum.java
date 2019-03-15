package com.easyt.constant;

public enum ProfileEnum {

	GENERALMANAGER("GENERALMANAGER"),
	MODERATOR("MODERATOR"),
	STUDENT("STUDENT"),
	DRIVER("DRIVER");
	
	private String desc;
	
	private ProfileEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}