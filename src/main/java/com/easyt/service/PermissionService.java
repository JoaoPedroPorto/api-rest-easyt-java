package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.request.PermissionRequest;
import com.easyt.request.UserRequest;
import com.easyt.response.PermissionResponse;

public interface PermissionService {

	void createPermissions(List<PermissionRequest> permissions) throws ApplicationException;

	void deletePermission(Long permissionId) throws ApplicationException;

	List<PermissionResponse> listAllPermissions() throws ApplicationException;

	List<String> listAllPermissionsByUser(String profile) throws ApplicationException;

	void updateAllPermissions(Long id, UserRequest request) throws ApplicationException;

}
