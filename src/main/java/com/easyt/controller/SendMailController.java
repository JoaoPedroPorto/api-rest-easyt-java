package com.easyt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyt.constant.ApiMapping;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.response.Response;
import com.easyt.service.SendMailService;

@RestController
@RequestMapping(ApiMapping.SEND_MAIL)
@CrossOrigin(origins = ApiMapping.CROSS_ORIGEN)
public class SendMailController {
	
	@Autowired
	private SendMailService sendMailService;
	private static final Logger LOGGER = LoggerFactory.getLogger(SendMailController.class);
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "")
	public ResponseEntity<Response<String>> testSendMail(	@PathVariable("parameter")String parameter,
															@PathVariable("email")String email) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			this.sendMailService.testSendMail(parameter, email);
			res.setData(MessagesErroEnum.TEST_SEND_MAIL_SUCCESS.getMessage());
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
