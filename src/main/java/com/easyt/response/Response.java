package com.easyt.response;

public class Response<T> {

	private T data;
	private String error;

	public Response() {}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
