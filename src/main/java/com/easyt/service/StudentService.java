package com.easyt.service;

import java.util.List;

import com.easyt.exception.ApplicationException;
import com.easyt.response.UserResponse;

public interface StudentService {

	List<UserResponse> listAllStudent() throws ApplicationException;

	Long deleteStudent(Long studentId) throws ApplicationException;

}
