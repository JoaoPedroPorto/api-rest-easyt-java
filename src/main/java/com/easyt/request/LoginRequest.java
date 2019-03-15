package com.easyt.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 2148440124815115387L;
	
	private String email;
	private String password;
	private String facebookId;
	private String googleId;
	
	// GETTERS AND SETTERS
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
}
