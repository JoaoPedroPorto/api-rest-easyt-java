package com.easyt.controller;

import java.util.List;

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
import com.easyt.response.PermissionResponse;
import com.easyt.response.Response;
import com.easyt.service.AuthenticationService;
import com.easyt.service.PermissionService;

@RestController
@RequestMapping(ApiMapping.PERMISSION)
@CrossOrigin(origins = ApiMapping.CROSS_ORIGEN)
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private AuthenticationService authenticationService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
	private static final String AUTHENTICATION_PROPERTY = "Authentication";
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createPermissions(@Valid @RequestBody UserRequest request, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			if (request.getPermissions() == null || request.getPermissions().isEmpty())
				throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
			this.permissionService.createPermissions(request.getPermissions());
			res.setData(MessagesErroEnum.CREATE_PERMISSIONS_SUCCESS.getMessage());
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
	public ResponseEntity<Response<List<PermissionResponse>>> listAllPermissions(@RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<List<PermissionResponse>> res = new Response<List<PermissionResponse>>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			List<PermissionResponse> permissions = this.permissionService.listAllPermissions();
			res.setData(permissions);
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
	@GetMapping(value = "/permissionsByUser/{profile}")
	public ResponseEntity<Response<List<String>>> listAllPermissionsByUser(@PathVariable("profile") String profile, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<List<String>> res = new Response<List<String>>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			List<String> permissions = this.permissionService.listAllPermissionsByUser(profile);
			res.setData(permissions);
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
	public ResponseEntity<Response<String>> updateAllPermissions(@PathVariable("id") Long id, @Valid @RequestBody UserRequest request, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			this.permissionService.updateAllPermissions(id, request);
			res.setData(MessagesErroEnum.UPDATE_PERMISSIONS_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> deletePermission(@PathVariable("id") Long id, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			this.permissionService.deletePermission(id);
			res.setData(MessagesErroEnum.DELETE_PERMISSION_SUCCESS.getMessage());
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
