package com.easyt.service;

import com.easyt.exception.ApplicationException;
import com.easyt.request.LoginRequest;
import com.easyt.response.UserResponse;
import com.easyt.util.SendMail;

public interface AuthenticationService {

	UserResponse loginPanel(LoginRequest user) throws ApplicationException;

	UserResponse loginMobile(LoginRequest user) throws ApplicationException;

	SendMail forgotMyPassword(String request) throws ApplicationException;

	void getUserByToken(String token) throws ApplicationException;

	void changeMyPassword(String token, String password) throws ApplicationException;

	String refreshToken(String token) throws ApplicationException;

	Boolean verifyUserAuthenticated(String token);

}
