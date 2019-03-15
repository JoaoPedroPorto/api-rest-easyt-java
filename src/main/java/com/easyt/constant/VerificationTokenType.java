package com.easyt.constant;

public enum VerificationTokenType {
	
	REDEFINE_PASSWORD("REDEFINE_PASSWORD"),
	SESSION("SESSION"),
	REFRESH_SESSION("REFRESH_SESSION");
	
	private String name;
	
	private VerificationTokenType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
