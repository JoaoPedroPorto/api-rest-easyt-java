package com.easyt.constant;

public enum EnvironmentEnum {
	
	LOCAL("LOCAL"),
	DEVELOPMENT("DEVELOPMENT"),
	PRODUCTION("PRODUCTION");
	
	private String environment;
	
	private EnvironmentEnum(String platform) {
		this.environment = platform;
	}

	/**
	 * @return the ambiente
	 */
	public String getPlatform() {
		return environment;
	}
}