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
import com.easyt.request.InstitutionRequest;
import com.easyt.response.InstitutionResponse;
import com.easyt.response.Response;
import com.easyt.service.InstitutionService;

@RestController
@RequestMapping(Api.API + Api.INSTITUTION)
@CrossOrigin(origins = Api.CROSS_ORIGEN)
public class InstitutionController {
	
	@Autowired
	private InstitutionService institutionService;
	private static final Logger LOGGER = LoggerFactory.getLogger(InstitutionController.class);
	
	@PostMapping(value = "")
	public ResponseEntity<Response<String>> createInstitution(@Valid @RequestBody InstitutionRequest institution) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			institutionService.createInstitution(institution);
			res.setData(MessagesErroEnum.CREATE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> updateInstitution(@PathVariable("id") Long id, @Valid @RequestBody InstitutionRequest institution) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			institutionService.updateInstitution(id, institution);
			res.setData(MessagesErroEnum.UPDATE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<String>> deleteInstitution(@PathVariable("id") Long id) throws ApplicationException {
		Response<String> res = new Response<String>();
		try {
			institutionService.deleteInstitution(id);
			res.setData(MessagesErroEnum.DELETE_INSTITUTION_SUCCESS.getMessage());
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
	public ResponseEntity<Response<List<InstitutionResponse>>> listAllInstitution() throws ApplicationException {
		Response<List<InstitutionResponse>> res = new Response<List<InstitutionResponse>>();
		try {
			List<InstitutionResponse> institutions = institutionService.listAllInstitution();
			res.setData(institutions);
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
