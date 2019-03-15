package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.request.UserRequest;
import com.easyt.response.UserResponse;
import com.easyt.util.SendMail;

public interface GeneralManagerService {

	SendMail createGeneralManager(UserRequest manager) throws ApplicationException;

	void updateGeneralManager(Long id, UserRequest manager) throws ApplicationException;

	List<UserResponse> listAllGeneralManager() throws ApplicationException;

	void deleteGeneralManager(Long managerId) throws ApplicationException;

}
