package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.response.UserResponse;

public interface DriverService {

	void deleteDriver(Long driverId) throws ApplicationException;

	List<UserResponse> listAllDriver() throws ApplicationException;

}
