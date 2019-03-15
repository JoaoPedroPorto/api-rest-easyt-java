package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.request.UserRequest;
import com.easyt.response.UserResponse;
import com.easyt.util.SendMail;

public interface ModeratorService {

	void deleteModerator(Long moderatorId) throws ApplicationException;

	List<UserResponse> listAllModerator() throws ApplicationException;

	SendMail createModerator(UserRequest moderator) throws ApplicationException;

	void updateModerator(Long id, UserRequest moderator) throws ApplicationException;

}
