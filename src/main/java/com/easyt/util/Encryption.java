package com.easyt.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {
	
	private static Logger logger = LogManager.getLogger(Encryption.class.getName()); 

	public static String encrypt(String value) {
		BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
		return BCryptPasswordEncoder.encode(value);
	}

	public static String encrypt(String value, Integer salt) {
		String result = null;
		try {
			result = BCrypt.hashpw(value, BCrypt.gensalt(salt));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static Boolean verify(String clearValue, String encryptedValue) {
		return BCrypt.checkpw(clearValue, encryptedValue);
	}
}