package com.easyt.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.ApiMapping;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.exception.UnauthorizedException;
import com.easyt.request.UserRequest;
import com.easyt.response.Response;
import com.easyt.response.UserResponse;
import com.easyt.service.AuthenticationService;
import com.easyt.service.GeneralManagerService;
import com.easyt.service.SendMailService;
import com.easyt.util.SendMail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(ApiMapping.GENERAL_MANAGER)
@Api(value = "API RESTfull General Manager")
@CrossOrigin(origins = ApiMapping.CROSS_ORIGEN)
public class GeneralManagerController {
	
	@Autowired
	private GeneralManagerService generalManagerService;
	@Autowired
	private SendMailService sendMailService;
	@Autowired
	private AuthenticationService authenticationService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralManagerController.class);
	private static final String AUTHENTICATION_PROPERTY = "Authentication";
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "")
	@ApiOperation(value = "Realiza o cadastro de usuário administrador do sistema.")
	public ResponseEntity<Response<String>> createGeneralManager(@Valid @RequestBody UserRequest manager, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			SendMail dest = generalManagerService.createGeneralManager(manager);
			if (dest != null) {
				CompletableFuture.runAsync(new Runnable() {
					@Override
					public void run() {
						try {
							sendMailService.sendMailWelcomeUserNewOrExisting(dest);
						} catch (Exception e) {
							e.printStackTrace();
							LOGGER.error(e.getMessage(), e);
						}
					}
				});
			}
			res.setData(MessagesErroEnum.CREATE_GENERAL_MANAGER_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (UnauthorizedException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
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
	@GetMapping(value = "")
	@ApiOperation(value = "Lista todos administradores ativos no sistema.")
	public ResponseEntity<Response<List<UserResponse>>> listAllGeneralManager(@RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<List<UserResponse>> res = new Response<List<UserResponse>>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			List<UserResponse> managers = generalManagerService.listAllGeneralManager();
			res.setData(managers);
			return ResponseEntity.ok(res);
		} catch (UnauthorizedException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
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
	@PatchMapping(value = "/{id}")
	@ApiOperation(value = "Atualiza um administrador do sistema.")
	public ResponseEntity<Response<String>> updateGeneralManager(@PathVariable("id") Long id, @Valid @RequestBody UserRequest manager, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			generalManagerService.updateGeneralManager(id, manager);
			res.setData(MessagesErroEnum.UPDATE_GENERAL_MANAGER_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (UnauthorizedException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
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
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Remove um administrador do sistema.")
	public ResponseEntity<Response<String>> deleteGeneralManager(@PathVariable("id") Long id, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			generalManagerService.deleteGeneralManager(id);
			res.setData(MessagesErroEnum.DELETE_GENERAL_MANAGER_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (UnauthorizedException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
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
