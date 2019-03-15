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
import com.easyt.service.ModeratorService;
import com.easyt.service.SendMailService;
import com.easyt.util.SendMail;

@RestController
@RequestMapping(Api.API + Api.MODERATOR)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class ModeratorController {
	
	@Autowired
	private ModeratorService moderatorService;
	@Autowired
	private SendMailService sendMailService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ModeratorController.class);
	
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createModerator(@Valid @RequestBody UserRequest moderator) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			SendMail dest = moderatorService.createModerator(moderator);
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
			res.setData(MessagesErroEnum.CREATE_MODERATOR_SUCCESS.getMessage());
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
	public ResponseEntity<Response<List<UserResponse>>> listAllModerator() throws ApplicationException {
		Response<List<UserResponse>> res = new Response<List<UserResponse>>();
		try {
			List<UserResponse> moderators = moderatorService.listAllModerator();
			res.setData(moderators);
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
	public ResponseEntity<Response<String>> updateModerator(@PathVariable("id") Long id, @Valid @RequestBody UserRequest moderator) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			moderatorService.updateModerator(id, moderator);
			res.setData(MessagesErroEnum.UPDATE_MODERATOR_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> deleteModerator(@PathVariable("id") Long id) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			moderatorService.deleteModerator(id);
			res.setData(MessagesErroEnum.DELETE_MODERATOR_SUCCESS.getMessage());
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
