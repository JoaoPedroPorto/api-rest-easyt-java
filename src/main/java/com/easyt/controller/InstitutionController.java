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
import com.easyt.request.InstitutionRequest;
import com.easyt.response.InstitutionResponse;
import com.easyt.response.Response;
import com.easyt.service.AuthenticationService;
import com.easyt.service.InstitutionService;

@RestController
@RequestMapping(ApiMapping.INSTITUTION)
@CrossOrigin(origins = ApiMapping.CROSS_ORIGEN)
public class InstitutionController {
	
	@Autowired
	private InstitutionService institutionService;
	@Autowired
	private AuthenticationService authenticationService;
	private static final Logger LOGGER = LoggerFactory.getLogger(InstitutionController.class);
	private static final String AUTHENTICATION_PROPERTY = "Authentication";
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createInstitution(@Valid @RequestBody InstitutionRequest institution, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			institutionService.createInstitution(institution);
			res.setData(MessagesErroEnum.CREATE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> updateInstitution(@PathVariable("id") Long id, @Valid @RequestBody InstitutionRequest institution, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			institutionService.updateInstitution(id, institution);
			res.setData(MessagesErroEnum.UPDATE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> deleteInstitution(@PathVariable("id") Long id, @RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<String> res = new Response<String>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			institutionService.deleteInstitution(id);
			res.setData(MessagesErroEnum.DELETE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<List<InstitutionResponse>>> listAllInstitution(@RequestHeader(AUTHENTICATION_PROPERTY) String authentication) throws ApplicationException, UnauthorizedException {
		Response<List<InstitutionResponse>> res = new Response<List<InstitutionResponse>>();
		try {
			authenticationService.verifyUserAuthenticated(authentication);
			List<InstitutionResponse> institutions = institutionService.listAllInstitution();
			res.setData(institutions);
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
