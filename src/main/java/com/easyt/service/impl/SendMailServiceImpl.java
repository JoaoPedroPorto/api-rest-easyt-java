package com.easyt.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.EnvEnum;
import com.easyt.constant.MessagesErroEnum;
import com.easyt.exception.ApplicationException;
import com.easyt.service.SendMailService;
import com.easyt.util.ApplicationUtil;
import com.easyt.util.EmailHelper;
import com.easyt.util.Encryption;
import com.easyt.util.SendMail;

@Service
@Transactional
public class SendMailServiceImpl implements SendMailService {
	
	@Override
	public void testSendMail(String parameter, String email) throws ApplicationException {
		if ((parameter == null || parameter.trim().isEmpty()) ||
				(email == null || email.trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		if(!ApplicationUtil.isValidEmail(email.trim()))
			throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());
		SendMail dest = new SendMail();
		dest.setName("TESTE E-MAIL");
		dest.setEmail(email);
		dest.setMediaUrl(EnvEnum.MEDIA_URL.getUrl());
		if (parameter.equals("NEW")) {
			String password = ApplicationUtil.generatePassword();
			dest.setPassword(Encryption.encrypt(password));
			dest.setSubject(ApplicationUtil.verifyEnvironmentSubject() + "[EASYT]" + "Bem vindo!");
			dest.setLink(	EnvEnum.ADDRESS_FRONT.getUrl() +
					EnvEnum.PORT_FRONT.getUrl() +
					EnvEnum.CONTEXT_FRONT.getUrl());
			EmailHelper.sendMailWelcomeUserNewOrExisting(dest);
		} else if (parameter.equals("REMEMBER")) {
			dest.setSubject(ApplicationUtil.verifyEnvironmentSubject() + "[EASYT]" + "Redefinição de senha!");
			dest.setLink(	EnvEnum.ADDRESS_FRONT.getUrl() +
							EnvEnum.PORT_FRONT.getUrl() +
							EnvEnum.CONTEXT_FRONT.getUrl() +
							EnvEnum.RESET_PASSWORD.getUrl() +
							"token");
			EmailHelper.sendMailFromChangePassword(dest);
		}
	}
	
	@Async
	@Override
	public void sendMailWelcomeUserNewOrExisting(SendMail dest) throws ApplicationException {
		dest.setMediaUrl(EnvEnum.MEDIA_URL.getUrl());
		dest.setSubject(ApplicationUtil.verifyEnvironmentSubject() + "[EASYT]" + "Bem vindo!");
		dest.setLink(	EnvEnum.ADDRESS_FRONT.getUrl() +
						EnvEnum.PORT_FRONT.getUrl() +
						EnvEnum.CONTEXT_FRONT.getUrl());
		EmailHelper.sendMailWelcomeUserNewOrExisting(dest);
	}
	
	@Async
	@Override
	public void sendMailFromChangePassword(SendMail dest) throws ApplicationException {
		dest.setMediaUrl(EnvEnum.MEDIA_URL.getUrl());
		dest.setSubject(ApplicationUtil.verifyEnvironmentSubject() + "[EASYT]" + "Redefinição de senha!");
		dest.setLink(	EnvEnum.ADDRESS_FRONT.getUrl() +
						EnvEnum.PORT_FRONT.getUrl() +
						EnvEnum.CONTEXT_FRONT.getUrl() +
						EnvEnum.RESET_PASSWORD.getUrl() +
						dest.getToken());
		EmailHelper.sendMailFromChangePassword(dest);
	}
}