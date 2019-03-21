package com.easyt.controller;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.ApiMapping;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.request.LoginRequest;
import com.easyt.request.UserRequest;
import com.easyt.response.Response;
import com.easyt.response.UserResponse;
import com.easyt.service.AuthenticationService;
import com.easyt.service.PermissionService;
import com.easyt.service.SendMailService;
import com.easyt.util.SendMail;

@RestController
@RequestMapping(ApiMapping.AUTHENTICATION)
@CrossOrigin(origins = ApiMapping.CROSS_ORIGEN)
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private SendMailService sendMailService;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/refreshToken")
	public ResponseEntity<Response<String>> refreshToken(@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			String acccessToken = authenticationService.refreshToken(request.getToken());
			res.setData(acccessToken);
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/loginPanel")
	public ResponseEntity<Response<UserResponse>> loginPanel(@Valid @RequestBody LoginRequest user) throws ApplicationException {
		Response<UserResponse> res = new Response<UserResponse>();
		try {
			UserResponse userDB = authenticationService.loginPanel(user);
			if (userDB == null)
				throw new ApplicationException(MessagesErroEnum.EMAIL_WITHOUT_ACCESS_PANEL.getMessage());
			userDB.setPermissions(permissionService.listAllPermissionsByUser(userDB.getProfile()));
			if (userDB.getPermissions() == null || userDB.getPermissions().isEmpty())
				throw new ApplicationException(MessagesErroEnum.USER_WITHOUT_PERMISSIONS_TO_PROFILE.getMessage());
			res.setData(userDB);
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);			
		}
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/loginMobile")
	public ResponseEntity<Response<UserResponse>> loginMobile(@Valid @RequestBody LoginRequest user) throws ApplicationException {
		Response<UserResponse> res = new Response<UserResponse>();
		try {
			UserResponse userDB = authenticationService.loginMobile(user);
			if (userDB == null) 
				throw new ApplicationException(MessagesErroEnum.EMAIL_WITHOUT_ACCESS_MOBILE.getMessage());
			userDB.setPermissions(permissionService.listAllPermissionsByUser(userDB.getProfile()));
			if (userDB.getPermissions() == null || userDB.getPermissions().isEmpty())
				throw new ApplicationException(MessagesErroEnum.USER_WITHOUT_PERMISSIONS_TO_PROFILE.getMessage());
			res.setData(userDB);
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/validateToken")
	public ResponseEntity<Response<String>> validateToken(@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.getUserByToken(request.getToken());
			res.setData(MessagesErroEnum.TOKEN_VALID.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/resetPassword")
	public ResponseEntity<Response<String>> resetPassword(@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.changeMyPassword(request.getToken(), request.getPassword());
			res.setData(MessagesErroEnum.CHANGE_PASSWORD_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/rememberPassword")
	public ResponseEntity<Response<String>> rememberPassword(@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			SendMail dest = authenticationService.forgotMyPassword(request.getChangePassword());
			if (dest != null) {
				CompletableFuture.runAsync(new Runnable() {
					@Override
					public void run() {
						try {
							sendMailService.sendMailFromChangePassword(dest);
						} catch (Exception e) {
							e.printStackTrace();
							LOGGER.error(e.getMessage(), e);
						}
					}
				});
				
			}
			res.setData(MessagesErroEnum.REMEMBER_PASSWORD_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
}
