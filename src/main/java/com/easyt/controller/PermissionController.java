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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.Api;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.request.UserRequest;
import com.easyt.response.PermissionResponse;
import com.easyt.response.Response;
import com.easyt.service.PermissionService;

@RestController
@RequestMapping(Api.API + Api.PERMISSION)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
	
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createPermissions(@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			if (request.getPermissions() == null || request.getPermissions().isEmpty())
				throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
			this.permissionService.createPermissions(request.getPermissions());
			res.setData(MessagesErroEnum.CREATE_PERMISSIONS_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@GetMapping(value = "")
	public ResponseEntity<Response<List<PermissionResponse>>> listAllPermissions() throws ApplicationException {
		Response<List<PermissionResponse>> res = new Response<List<PermissionResponse>>();
		try {
			List<PermissionResponse> permissions = this.permissionService.listAllPermissions();
			res.setData(permissions);
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@GetMapping(value = "/permissionsByUser/{profile}")
	public ResponseEntity<Response<List<String>>> listAllPermissionsByUser(@PathVariable("profile") String profile) throws ApplicationException {
		Response<List<String>> res = new Response<List<String>>();
		try {
			List<String> permissions = this.permissionService.listAllPermissionsByUser(profile);
			res.setData(permissions);
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Response<String>> updateAllPermissions(	@PathVariable("id") Long id, 
																	@Valid @RequestBody UserRequest request) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			this.permissionService.updateAllPermissions(id, request);
			res.setData(MessagesErroEnum.UPDATE_PERMISSIONS_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deletePermission(@PathVariable("id") Long id) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			this.permissionService.deletePermission(id);
			res.setData(MessagesErroEnum.DELETE_PERMISSION_SUCCESS.getMessage());
			return ResponseEntity.ok(res);
		} catch (ApplicationException e) {
			res.setError(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.setError(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

}
