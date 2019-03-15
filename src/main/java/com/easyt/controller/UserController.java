package com.easyt.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.Api;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.entity.Device;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.request.DeviceRequest;
import com.easyt.request.UserRequest;
import com.easyt.response.Response;
import com.easyt.response.UserResponse;
import com.easyt.service.UserService;

@RestController
@RequestMapping(Api.API + Api.USER)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class UserController {
	
	@Autowired
	private UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
	
	@PatchMapping(value = "/myData/{id}")
	public ResponseEntity<Response<String>> myData(	@PathVariable("id") Long id, 
													@Valid @RequestBody UserRequest user) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			this.userService.updateMyData(id, user);
			res.setData(MessagesErroEnum.UPDATE_DATA_USER_SUCCESS.getMessage());
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
	
	@PatchMapping(value = "/changePassword/{id}")
	public ResponseEntity<Response<String>> changePassword(	@PathVariable("id") Long id, 
															@Valid @RequestBody UserRequest user) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			this.userService.changePassword(id, user);
			res.setData(MessagesErroEnum.CHANGE_PASSWORD_SUCCESS.getMessage());
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
	
	@GetMapping(value = "/getUser/{id}")
	public ResponseEntity<Response<UserResponse>> getUserById(@PathVariable("id") Long id) throws ApplicationException {
		Response<UserResponse> res = new Response<UserResponse>();
		try {
			UserResponse userDB = this.userService.getUserById(id);
			res.setData(userDB);
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
	
	@PatchMapping(value = "/updateDevice")
	public ResponseEntity<Response<String>> updateDeviceUser(DeviceRequest device) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			if ((device.getId() == null) || (device.getPlatform() == null) ||
					((device.getNewToken() == null || device.getNewToken().trim().isEmpty()) && 
							(device.getOldToken() == null || device.getOldToken().trim().isEmpty())))
				throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
			Device d0 = new Device();
			if (device.getNewToken() != null && !device.getNewToken().trim().isEmpty()) {
				d0 = new Device();
				d0.setToken(device.getNewToken());
				d0.setUser(new User());
				d0.getUser().setId(device.getId());
				d0.setPlatform(device.getPlatform());
			}
			Device d1 = new Device();
			if (device.getOldToken() != null && !device.getOldToken().trim().isEmpty()) {
				d1 = new Device();				
				d1.setToken(device.getOldToken());
				d1.setUser(new User());
				d1.getUser().setId(device.getId());
			}
			this.userService.updateDeviceUser(d0, d1);
			res.setData(MessagesErroEnum.UPDATE_DEVICE_SUCCESS.getMessage());
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
