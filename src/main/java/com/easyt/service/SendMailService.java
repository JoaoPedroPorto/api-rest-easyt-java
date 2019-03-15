package com.easyt.service;

import com.easyt.exception.ApplicationException;
import com.easyt.util.SendMail;

public interface SendMailService {

	void sendMailWelcomeUserNewOrExisting(SendMail dest) throws ApplicationException;

	void sendMailFromChangePassword(SendMail dest) throws ApplicationException;

	void testSendMail(String parameter, String email) throws ApplicationException;

}
