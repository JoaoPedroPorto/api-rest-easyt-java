package com.easyt.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import com.easyt.constant.EnvironmentEnum;

public class ApplicationUtil {

	public static EnvironmentEnum ENVIRONMENT = EnvironmentEnum.DEVELOPMENT;
	

	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static boolean isValidEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email.trim());
			emailAddr.validate();
		} catch (AddressException ex) {
			return false;
		}
		return true;
	}
	
	public static String calendarToString(Calendar cal) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy'T'HH-mm-ss");
		return format1.format(cal.getTime());
	}
	
	public static String calendarToStringDate(Calendar cal) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		return format1.format(cal.getTime());
	}
	
	public static String timestampToString(Long longDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(longDate);
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		return format1.format(cal.getTime());
	}
	
	public static Calendar stringToCalendar(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			cal = null;
		}
		return cal;
	}
	
	public static long stringToTimestamp(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(data));
		return cal.getTimeInMillis();
	}
	
	public static Calendar timestampToCalendar(Long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		return cal;
	}
	
	public static String applyMaskCPF(String cpf) {
		return cpf.substring(0, 3)+"."+cpf.substring(3, 6)+"."+cpf.substring(6, 9)+"-"+cpf.substring(9);
	}
	
	public static String getHourCalendar(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date hora = cal.getTime();
		String dataFormatada = sdf.format(hora);
		return dataFormatada;
		
	}
	
	public static String generatePassword() {
		RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
		return randomStringGenerator.generate(6);
	}
	
	public static String removeMaskCpf(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		return cpf;
	}
	
	public static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Date result = null;
        try {
        	LocalDateTime now = LocalDateTime.now();
            String dateFormatted = now.plusMinutes(expiryTimeInMinutes)
            		.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss.SSS").withZone(ZoneId.of("Z")));
			result = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS").parse(dateFormatted);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return result;
    }
	
	public static String verifyEnvironmentSubject() {
		String subject = null;
		if(ApplicationUtil.ENVIRONMENT.equals(EnvironmentEnum.LOCAL)) {
			subject = "[LOCAL] ";
		} else if(ApplicationUtil.ENVIRONMENT.equals(EnvironmentEnum.DEVELOPMENT)) {
			subject = "[DEV] ";
		} else if(ApplicationUtil.ENVIRONMENT.equals(EnvironmentEnum.PRODUCTION)) {
			subject = "";
		}
		return subject;
	}
	
	public static String removeCaracterSpecial(String text) {
		text = text.trim();
		if (text != null && !text.trim().isEmpty()) {
			text = text.replaceAll("[ãâàáä]",	"a");
			text = text.replaceAll("[êèéë&]",	"e");
			text = text.replaceAll("[îìíï]",	"i");
			text = text.replaceAll("[õôòóö]",	"o");
			text = text.replaceAll("[ûúùü]",	"u");
			text = text.replaceAll("[ÃÂÀÁÄ]",	"A");
			text = text.replaceAll("[ÊÈÉË]",	"E");
			text = text.replaceAll("[ÎÌÍÏ]",	"I");
			text = text.replaceAll("[ÕÔÒÓÖ]",	"O");
			text = text.replaceAll("[ÛÙÚÜ]",	"U");
			text = text.replace('ç',	'c');
			text = text.replace('Ç',	'C');
			text = text.replace('ñ',	'n');
			text = text.replace('Ñ',	'N');
		}
		return text;
	}
	
	public static Long calendarToTimestamp(Calendar data) {
		if (data != null)
			return data.getTimeInMillis();
		return null;
	}

}