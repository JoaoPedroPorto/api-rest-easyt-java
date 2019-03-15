package com.easyt.service.impl;

import java.util.Calendar;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.Device;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.DeviceRepository;
import com.easyt.repository.UserRepository;
import com.easyt.request.UserRequest;
import com.easyt.response.UserResponse;
import com.easyt.service.UserService;
import com.easyt.util.Encryption;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Override
	public void updateMyData(Long id, UserRequest user) throws ApplicationException {
		if (id == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> userDB = userRepository.findOneByIdAndStatusNot(id, StatusEnum.INACTIVE);
		if (userDB == null || !userDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		userDB.get().setEditionDate(Calendar.getInstance());
		userDB.get().setName(user.getName());
		if (user.getCpf() != null && !user.getCpf().isEmpty()) {
			checkIfCpfExists(user.getId(), user.getCpf());
			userDB.get().setCpf(user.getCpf());
		}
		if (user.getTelephoneNumber() != null && !user.getTelephoneNumber().isEmpty()) {
			checkIfTelephoneNumberExists(user.getId(), user.getTelephoneNumber());
			userDB.get().setTelephoneNumber(user.getTelephoneNumber());
		}
		if (user.getMediaUrl() != null && !user.getMediaUrl().isEmpty())
			userDB.get().setMediaUrl(user.getMediaUrl());
		if (user.getDateOfBirth() != null && Calendar.getInstance().after(user.getDateOfBirth()))
			userDB.get().setDateOfBirth(user.getDateOfBirth());
		if (user.getPictureDocument() != null && !user.getPictureDocument().isEmpty())
			userDB.get().setPictureDocument(user.getPictureDocument());
		userRepository.save(userDB.get());
	}
	
	public void checkIfCpfExists(Long id, String cpf) throws ApplicationException {
		Optional<User> userDB = userRepository.findOneByCpfAndIdNot(cpf, id);
		if (userDB != null && userDB.isPresent()) 
			throw new ApplicationException(MessagesErroEnum.CPF_EXIST.getMessage());
	}
	
	public void checkIfTelephoneNumberExists(Long id, String tel) throws ApplicationException {
		Optional<User> userDB = userRepository.findOneByTelephoneNumberAndIdNot(tel, id);
		if (userDB != null && userDB.isPresent()) 
			throw new ApplicationException(MessagesErroEnum.CPF_EXIST.getMessage());
	}
	
	@Override
	public void changePassword(Long id, UserRequest user) throws ApplicationException {
		try {
			if (id == null || 
					(user.getPassword() == null || user.getPassword().isEmpty()) ||
					(user.getNewPassword() == null || user.getNewPassword().isEmpty()) ||
					(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()))
				throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
			if (user.getNewPassword().length() < 6 || user.getNewPassword().length() > 12)
				throw new ApplicationException(MessagesErroEnum.PASSWORD_OUT_OF_THE_STANDARD.getMessage());
			if (!user.getNewPassword().equals(user.getConfirmPassword()))
				throw new ApplicationException(MessagesErroEnum.PASSWORDS_ARE_NOT_THE_SAME.getMessage());
			Optional<User> userDB = userRepository.findOneByIdAndStatusNot(id, StatusEnum.INACTIVE);
			if (userDB == null || !userDB.isPresent())
				throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
			if (!Encryption.verify(user.getPassword(), userDB.get().getPassword()))
				throw new ApplicationException(MessagesErroEnum.PASSWORD_INVALID.getMessage());
			userDB.get().setPassword(Encryption.encrypt(user.getNewPassword()));
			userDB.get().setEditionDate(Calendar.getInstance());
			userRepository.save(userDB.get());		
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationException(MessagesErroEnum.CHANGE_PASSWORD_ERROR.getMessage());
		}
	}
	
	@Override
	public UserResponse getUserById(Long userId) throws ApplicationException {
		if (userId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> userDB = userRepository
				.findOneByIdAndStatusNot(userId, StatusEnum.INACTIVE);
		if (userDB == null || !userDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		return ConverterHelper.convertUserToResponse(userDB.get(), null, null);
	}
	
	@Override
	public void updateDeviceUser(Device newDevice, Device oldDevice) throws ApplicationException {
		if(newDevice.getToken() != null && !newDevice.getToken().trim().isEmpty()) {
			Optional<Device> newDeviceDB = deviceRepository
					.findOneByTokenAndUser_Id(newDevice.getToken(), newDevice.getUser().getId());
			if (newDeviceDB == null || !newDeviceDB.isPresent()) {
				deviceRepository.save(newDevice);				
			}
		}
		if(oldDevice.getToken() != null && !oldDevice.getToken().trim().isEmpty()) {
			Optional<Device> oldDeviceDB = deviceRepository
					.findOneByTokenAndUser_Id(oldDevice.getToken(), oldDevice.getUser().getId());
			if (oldDeviceDB != null && oldDeviceDB.isPresent()) {
				deviceRepository.delete(oldDeviceDB.get());				
			}
		}
	}
}
