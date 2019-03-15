package com.easyt.constant;

public enum PlatformEnum {
	
	ANDROID("ANDROID"),
	IOS("IOS"),
	WEB("WEB");
	
	private String platform;
	
	private PlatformEnum(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}
}