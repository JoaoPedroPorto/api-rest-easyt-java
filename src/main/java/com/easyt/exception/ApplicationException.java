package com.easyt.exception;

public class ApplicationException extends Exception {
	private static final long serialVersionUID = 7655651414151465879L;

	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message) {
		super(message);
	}
}
