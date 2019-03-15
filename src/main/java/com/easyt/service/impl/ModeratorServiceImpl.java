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
import com.easyt.service.ModeratorService;
import com.easyt.util.ApplicationUtil;
import com.easyt.util.Encryption;
import com.easyt.util.SendMail;

@Service
@Transactional
public class ModeratorServiceImpl implements ModeratorService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void deleteModerator(Long moderatorId) throws ApplicationException {
		if (moderatorId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> moderatorDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	moderatorId,
													ProfileEnum.MODERATOR, 
													StatusEnum.INACTIVE);
		if (moderatorDB == null || !moderatorDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		moderatorDB.get().setEditionDate(Calendar.getInstance());
		moderatorDB.get().setStatus(StatusEnum.INACTIVE);
		userRepository.save(moderatorDB.get());
	}
	
	@Override
	public List<UserResponse> listAllModerator() throws ApplicationException {
		List<UserResponse> moderators = new ArrayList<>();
		userRepository
		.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.MODERATOR)
		.forEach(user->{
			UserResponse objectUser = new UserResponse();
			objectUser.setId(user.getId());
			objectUser.setName(user.getName());
			objectUser.setEmail(user.getEmail());
			objectUser.setStatus(user.getStatus().toString());
			objectUser.setProfile(user.getProfile().toString());
			moderators.add(objectUser);
		});
		return moderators;
	}
	
	@Override
	public void updateModerator(Long id, UserRequest moderator) throws ApplicationException {
		if ((moderator.getName() == null || moderator.getName().trim().isEmpty()) ||
				id == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> moderatorDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	id, 
													ProfileEnum.MODERATOR, 
													StatusEnum.INACTIVE);
		if (moderatorDB == null || !moderatorDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		moderatorDB.get().setEditionDate(Calendar.getInstance());
		moderatorDB.get().setName(moderator.getName().trim());
		userRepository.save(moderatorDB.get());
	}
	
	@Override
	public SendMail createModerator(UserRequest moderator) throws ApplicationException {
		if ((moderator.getEmail() == null || moderator.getEmail().trim().isEmpty()) || 
				(moderator.getName() == null || moderator.getName().trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		if(!ApplicationUtil.isValidEmail(moderator.getEmail().trim()))
			throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());
		SendMail dest = new SendMail();
		Optional<User> moderatorDB = userRepository
				.findOneByEmail(moderator.getEmail().trim());
		// VERIFICA REGISTRO ENCONTRADO
		if (moderatorDB != null && moderatorDB.isPresent()) { 
			// USUÁRIO ATIVO NO SISTEMA
			if (!moderatorDB.get().getStatus().equals(StatusEnum.INACTIVE))
				throw new ApplicationException(MessagesErroEnum.EMAIL_BELONGS_TO_AN_ACTIVE_USER_IN_THE_SYSTEM.getMessage());
			// POSSUI OUTRO TIPO DE PERFIL VINCULADO AO E-MAIL
			if (!moderatorDB.get().getProfile().equals(ProfileEnum.MODERATOR))
				throw new ApplicationException(MessagesErroEnum.EMAIL_HAS_A_DIFFERENT_PROFILE_FROM_THE_REQUESTED_PROFILE.getMessage());
			// EDITA ADMINISTRADOR CANCELADO NO SISTEMA
			moderatorDB.get().setEditionDate(Calendar.getInstance());
			moderatorDB.get().setName(moderator.getName().trim());
			moderatorDB.get().setStatus(StatusEnum.PENDING);
			// PASSWORD
			String password = ApplicationUtil.generatePassword();
			moderatorDB.get().setPassword(Encryption.encrypt(password));
			User userEdited = userRepository.save(moderatorDB.get());
			// PREPARA PARA ENVIAR O E-MAIL
			dest.setName(userEdited.getName());
			dest.setEmail(userEdited.getEmail());
			dest.setPassword(password);
			return dest;		
		} 
		// CRIA MODERATOR
		User newModerator = new User();
		newModerator.setCreationDate(Calendar.getInstance());
		newModerator.setName(moderator.getName().trim());
		newModerator.setEmail(moderator.getEmail().trim());
		newModerator.setStatus(StatusEnum.PENDING);
		newModerator.setProfile(ProfileEnum.MODERATOR);
		// PASSWORD
		String password = ApplicationUtil.generatePassword();
		newModerator.setPassword(Encryption.encrypt(password));
		User userSaved = userRepository.save(newModerator);	
		// PREPARA PARA ENVIAR O E-MAIL
		dest.setName(userSaved.getName());
		dest.setEmail(userSaved.getEmail());
		dest.setPassword(password);
		return dest;
	}

}
