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
import com.easyt.service.DriverService;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void deleteDriver(Long driverId) throws ApplicationException {
		if (driverId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> driverDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	driverId,
													ProfileEnum.DRIVER, 
													StatusEnum.INACTIVE);
		if (driverDB == null || !driverDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		driverDB.get().setEditionDate(Calendar.getInstance());
		driverDB.get().setStatus(StatusEnum.INACTIVE);
		userRepository.save(driverDB.get());
	}
	
	@Override
	public List<UserResponse> listAllDriver() throws ApplicationException {
		List<UserResponse> drivers = new ArrayList<>();
		userRepository
		.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.DRIVER)
		.forEach(user->{
			UserResponse objectUser = new UserResponse();
			objectUser = ConverterHelper.convertUserToResponse(user, null, null);
			drivers.add(objectUser);
		});
		return drivers;
	}
}