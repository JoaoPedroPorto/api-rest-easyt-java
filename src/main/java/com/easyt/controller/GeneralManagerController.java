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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.Api;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.request.UserRequest;
import com.easyt.response.Response;
import com.easyt.response.UserResponse;
import com.easyt.service.GeneralManagerService;
import com.easyt.service.SendMailService;
import com.easyt.util.SendMail;

@RestController
@RequestMapping(Api.API + Api.GENERAL_MANAGER)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class GeneralManagerController {
	
	@Autowired
	private GeneralManagerService generalManagerService;
	@Autowired
	private SendMailService sendMailService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralManagerController.class);
	
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createGeneralManager(@Valid @RequestBody UserRequest manager) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
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
	public ResponseEntity<Response<List<UserResponse>>> listAllGeneralManager() throws ApplicationException {
		Response<List<UserResponse>> res = new Response<List<UserResponse>>();
		try {
			List<UserResponse> managers = generalManagerService.listAllGeneralManager();
			res.setData(managers);
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
	public ResponseEntity<Response<String>> updateGeneralManager(@PathVariable("id") Long id, @Valid @RequestBody UserRequest manager) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			generalManagerService.updateGeneralManager(id, manager);
			res.setData(MessagesErroEnum.UPDATE_GENERAL_MANAGER_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> deleteGeneralManager(@PathVariable("id") Long id) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			generalManagerService.deleteGeneralManager(id);
			res.setData(MessagesErroEnum.DELETE_GENERAL_MANAGER_SUCCESS.getMessage());
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
