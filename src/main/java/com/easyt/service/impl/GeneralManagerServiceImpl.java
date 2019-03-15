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
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.UserRepository;
import com.easyt.request.UserRequest;
import com.easyt.response.UserResponse;
import com.easyt.service.GeneralManagerService;
import com.easyt.util.ApplicationUtil;
import com.easyt.util.Encryption;
import com.easyt.util.SendMail;

@Service
@Transactional
public class GeneralManagerServiceImpl implements GeneralManagerService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void deleteGeneralManager(Long managerId) throws ApplicationException {
		if (managerId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> managerDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	managerId,
													ProfileEnum.GENERALMANAGER, 
													StatusEnum.INACTIVE);
		if (managerDB == null || !managerDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		managerDB.get().setEditionDate(Calendar.getInstance());
		managerDB.get().setStatus(StatusEnum.INACTIVE);
		userRepository.save(managerDB.get());
	}
	
	@Override
	public List<UserResponse> listAllGeneralManager() throws ApplicationException {
		List<UserResponse> managers = new ArrayList<>();
		userRepository
		.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.GENERALMANAGER)
		.forEach(user->{
			UserResponse objectUser = new UserResponse();
			objectUser.setId(user.getId());
			objectUser.setName(user.getName());
			objectUser.setEmail(user.getEmail());
			objectUser.setStatus(user.getStatus().toString());
			objectUser.setProfile(user.getProfile().toString());
			managers.add(objectUser);
		});
		return managers;
	}
	
	@Override
	public void updateGeneralManager(Long id, UserRequest manager) throws ApplicationException {
		if ((manager.getName() == null || manager.getName().trim().isEmpty()) ||
				id == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> managerDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	id, 
													ProfileEnum.GENERALMANAGER, 
													StatusEnum.INACTIVE);
		if (managerDB == null || !managerDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		managerDB.get().setEditionDate(Calendar.getInstance());
		managerDB.get().setName(manager.getName().trim());
		userRepository.save(managerDB.get());
	}
	
	@Override
	public SendMail createGeneralManager(UserRequest manager) throws ApplicationException {
		if ((manager.getEmail() == null || manager.getEmail().trim().isEmpty()) || 
				(manager.getName() == null || manager.getName().trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		// TODO: VERIFICAR OUTRA FORMA DE VALIDAR E-MAIL
		/*if(!ApplicationUtil.isValidEmail(manager.getEmail().trim()))
			throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());*/
		SendMail dest = new SendMail();
		Optional<User> managerDB = userRepository
				.findOneByEmail(manager.getEmail().trim());
		// VERIFICA REGISTRO ENCONTRADO
		if (managerDB != null && managerDB.isPresent()) {
			// USUÁRIO ATIVO NO SISTEMA
			if (!managerDB.get().getStatus().equals(StatusEnum.INACTIVE))
				throw new ApplicationException(MessagesErroEnum.EMAIL_BELONGS_TO_AN_ACTIVE_USER_IN_THE_SYSTEM.getMessage());
			// POSSUI OUTRO TIPO DE PERFIL VINCULADO AO E-MAIL
			if (!managerDB.get().getProfile().equals(ProfileEnum.GENERALMANAGER))
				throw new ApplicationException(MessagesErroEnum.EMAIL_HAS_A_DIFFERENT_PROFILE_FROM_THE_REQUESTED_PROFILE.getMessage());
			// EDITA ADMINISTRADOR CANCELADO NO SISTEMA
			managerDB.get().setEditionDate(Calendar.getInstance());
			managerDB.get().setName(manager.getName().trim());
			managerDB.get().setStatus(StatusEnum.PENDING);
			// PASSWORD
			String password = ApplicationUtil.generatePassword();
			managerDB.get().setPassword(Encryption.encrypt(password));
			User userEdited = userRepository.save(managerDB.get());
			// PREPARA PARA ENVIAR O E-MAIL
			dest.setName(userEdited.getName());
			dest.setEmail(userEdited.getEmail());
			dest.setPassword(password);
			return dest;
		} 
		// CRIA ADMINISTRADOR
		User newManager = new User();
		newManager.setCreationDate(Calendar.getInstance());
		newManager.setName(manager.getName().trim());
		newManager.setEmail(manager.getEmail().trim());
		newManager.setStatus(StatusEnum.PENDING);
		newManager.setProfile(ProfileEnum.GENERALMANAGER);
		// PASSWORD
		String password = ApplicationUtil.generatePassword();
		newManager.setPassword(Encryption.encrypt(password));
		User userSaved = userRepository.save(newManager);
		// PREPARA PARA ENVIAR O E-MAIL
		dest.setName(userSaved.getName());
		dest.setEmail(userSaved.getEmail());
		dest.setPassword(password);
		return dest;
	}

}
