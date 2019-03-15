package com.easyt.service;

import com.easyt.entity.Device;
import com.easyt.exception.ApplicationException;
import com.easyt.request.UserRequest;
import com.easyt.response.UserResponse;

public interface UserService {

	UserResponse getUserById(Long userId) throws ApplicationException;

	void updateDeviceUser(Device newDevice, Device oldDevice) throws ApplicationException;

	void updateMyData(Long id, UserRequest user) throws ApplicationException;

	void changePassword(Long id, UserRequest user) throws ApplicationException;

}
