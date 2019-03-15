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

import com.easyt.constant.Api;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.request.NotificationRequest;
import com.easyt.response.NotificationResponse;
import com.easyt.response.Response;
import com.easyt.service.NotificationService;

@RestController
@RequestMapping(Api.API + Api.NOTIFICATION)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
	
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createNotification(@Valid @RequestBody NotificationRequest notification) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			NotificationResponse notify = notificationService.createNotification(notification);
			if (notify != null) {
				CompletableFuture.runAsync(new Runnable() {
					@Override
					public void run() {
						try {
							notificationService.associateNotificationToUsers(notify);
						} catch (Exception e) {
							e.printStackTrace();
							LOGGER.error(e.getMessage(), e);
						}
					}
				});
			}
			res.setData(MessagesErroEnum.CREATE_NOTIFICATION_SUCCESS.getMessage());
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
