package com.easyt.exception;

public class UnauthorizedException extends Exception {
	private static final long serialVersionUID = 1502111831219338554L;

	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
}
