package com.easyt.constant;

public enum TargetAudienceEnum {
	
	ALLUSERS("ALLUSERS"),
	STUDENT("STUDENT"),
	DRIVER("DRIVER");
	
	private String desc;
	
	private TargetAudienceEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}