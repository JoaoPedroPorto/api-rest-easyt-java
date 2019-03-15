package com.easyt.constant;

public enum EnvEnum {
	
	ADDRESS_FRONT("http://localhost:"),
	RESET_PASSWORD("/#/reset-password?q="),
	PORT_FRONT("4200"),
	ADDRESS_BACK("https://localhost:"),
	PORT_BACK("8443"),
	CONTEXT_FRONT(""),
	MEDIA_URL("https://s3-buildbox.s3.amazonaws.com/logo_15-03-2019T17-55-28.jpg");
	
	private String env;
	
	private EnvEnum(String url) {
		this.env = url;
	}
	public String getUrl() {
		return env;
	}
}