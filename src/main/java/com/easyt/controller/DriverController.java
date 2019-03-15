package com.easyt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.Api;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.response.Response;
import com.easyt.response.UserResponse;
import com.easyt.service.DriverService;

@RestController
@RequestMapping(Api.API + Api.DRIVER)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class);
	
	@GetMapping(value = "")
	public ResponseEntity<Response<List<UserResponse>>> listAllDriver() throws ApplicationException {
		Response<List<UserResponse>> res = new Response<List<UserResponse>>();
		try {
			List<UserResponse> drivers = driverService.listAllDriver();
			res.setData(drivers);
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
	public ResponseEntity<Response<String>> deleteDriver(@PathVariable("id") Long id) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			// TODO: NA SPRINT 3 FAZER LÓGICA PARA MANDAR NOTIFICAÇÃO PARA OS ALUNOS DESTE MOTORISTA
			driverService.deleteDriver(id);
			res.setData(MessagesErroEnum.DELETE_DRIVER_SUCCESS.getMessage());
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
