package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.request.InstitutionRequest;
import com.easyt.response.InstitutionResponse;

public interface InstitutionService {

	void createInstitution(InstitutionRequest institution) throws ApplicationException;

	void updateInstitution(Long id, InstitutionRequest institution) throws ApplicationException;

	void deleteInstitution(Long institutionId) throws ApplicationException;

	List<InstitutionResponse> listAllInstitution() throws ApplicationException;

}
