package com.easyt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Encryption.class);

	public static String encrypt(String value) {
		BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
		return BCryptPasswordEncoder.encode(value);
	}

	public static String encrypt(String value, Integer salt) {
		String result = null;
		try {
			result = BCrypt.hashpw(value, BCrypt.gensalt(salt));
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static Boolean verify(String clearValue, String encryptedValue) {
		return BCrypt.checkpw(clearValue, encryptedValue);
	}
}