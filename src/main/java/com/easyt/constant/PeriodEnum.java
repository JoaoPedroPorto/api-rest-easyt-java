package com.easyt.constant;

public enum PeriodEnum {

	MORNING("MORNING"),
	AFTERNOON("AFTERNOON"),
	FULLTIME("FULLTIME"),
	EVENING("EVENING");
	
	private String desc;
	
	private PeriodEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}