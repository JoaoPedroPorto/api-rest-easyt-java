package com.easyt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.UserRepository;
import com.easyt.response.UserResponse;
import com.easyt.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Long deleteStudent(Long studentId) throws ApplicationException {
		if (studentId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> studentDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	studentId,
													ProfileEnum.STUDENT, 
													StatusEnum.INACTIVE);
		if (studentDB == null || !studentDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		studentDB.get().setEditionDate(Calendar.getInstance());
		studentDB.get().setStatus(StatusEnum.INACTIVE);
		userRepository.save(studentDB.get());
		return studentDB.get().getDriver().getId();
	}
	
	@Override
	public List<UserResponse> listAllStudent() throws ApplicationException {
		List<UserResponse> students = new ArrayList<>();
		userRepository
		.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.STUDENT)
		.forEach(user->{
			UserResponse objectUser = new UserResponse();
			objectUser = ConverterHelper.convertUserToResponse(user, null, null);
			students.add(objectUser);
		});
		return students;
	}
}