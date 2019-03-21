package com.easyt.util;

import static com.easyt.handler.ResourceBundleHandler.EMAIL_BUNDLE;
import static com.easyt.handler.ResourceBundleHandler.VALIDATION_BUNDLE;
import static com.easyt.handler.ResourceBundleHandler.getValue;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easyt.exception.ApplicationException;

public class EmailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

	private static EmailUtil myInstance;
	private String to;
	private String from;
	private String host;
	private String port;
	private String subject;
	private String content;
	private String startTLS;
	private String username;
	private String password;

	private EmailUtil() {
		try {
			from = getValue(EMAIL_BUNDLE, "DefaultEmailFrom");
			host = getValue(EMAIL_BUNDLE, "DefaultEmailHost");
			port = getValue(EMAIL_BUNDLE, "DefaultEmailPort");
			startTLS = getValue(EMAIL_BUNDLE, "EnableStartTLS");
			this.username = getValue(EMAIL_BUNDLE, "DefaultEmailUsername");
			this.password = getValue(EMAIL_BUNDLE, "DefaultEmailPassword");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static EmailUtil getMyInstance() {
		if (myInstance == null)
			myInstance = new EmailUtil();
		return myInstance;
	}

	public void send() throws ApplicationException {
		if (to.isEmpty())
			throw new ApplicationException(getValue(VALIDATION_BUNDLE, "util.mail.wrong.to"));
		if (subject.isEmpty())
			throw new ApplicationException(getValue(VALIDATION_BUNDLE, "util.mail.wrong.subject"));
		if (content.isEmpty())
			throw new ApplicationException(getValue(VALIDATION_BUNDLE, "util.mail.wrong.content"));
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", this.startTLS);
		props.put("mail.smtp.host", this.host);
		props.put("mail.smtp.socketFactory.port", this.port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", this.port);
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(this.username, "EasyT", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
			}
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(content, "text/html; charset=utf-8");
			Transport.send(message);
			// CLEAN VARIABLES
			to = "";
			subject = "";
			content = "";
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getStartTLS() {
		return startTLS;
	}

}