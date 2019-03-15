package com.easyt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easyt.exception.ApplicationException;
import com.easyt.handler.ResourceBundleHandler;

public class EmailHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailHelper.class);
	
	public static void sendMailWelcomeUserNewOrExisting(SendMail dest) throws ApplicationException {
		try {
			String emailBody = ResourceBundleHandler.getValue(ResourceBundleHandler.EMAIL_BUNDLE, "Template_Mail_New_User_Or_Existing");
			String subject = dest.getSubject();
			emailBody = emailBody.replaceAll("#imgLink", dest.getMediaUrl());
			emailBody = emailBody.replaceAll("#userName", dest.getName());
			emailBody = emailBody.replaceAll("#userEmail", dest.getEmail());
			emailBody = emailBody.replaceAll("#userPassword", dest.getPassword());
			emailBody = emailBody.replaceAll("#link", dest.getLink());
			sendEmail(dest.getEmail(), subject, emailBody);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static void sendMailFromChangePassword(SendMail dest) {
		try {
			String emailBody = ResourceBundleHandler.getValue(ResourceBundleHandler.EMAIL_BUNDLE, "Template_Mail_Change_Password");
			String subject = dest.getSubject();
			emailBody = emailBody.replaceAll("#imgLink", dest.getMediaUrl());
			emailBody = emailBody.replaceAll("#userName", dest.getName());
			emailBody = emailBody.replaceAll("#link", dest.getLink());
			sendEmail(dest.getEmail(), subject, emailBody);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private static void sendEmail(String destinatario, String assunto, String corpoEmail) throws ApplicationException {
		try {
			EmailUtil.getMyInstance().setTo(destinatario);
			EmailUtil.getMyInstance().setSubject(assunto);
			EmailUtil.getMyInstance().setContent(corpoEmail);
			EmailUtil.getMyInstance().send();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}