package com.easyt.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;



public class ResourceBundleHandler {
	
	public static final String LABELS_BUNDLE = "properties.messages";
	public static final String VALIDATION_BUNDLE = "properties.messages_validation";
	public static final String EMAIL_BUNDLE = "properties.email";
	public static final String SECURITY_BUNDLE = "security";

	public static String getValue(String nameBundle, String key, Locale locale, Object[] params) {
		String result = "??????";
		ResourceBundle bundle = null;
		if(locale == null) {
			bundle = ResourceBundle.getBundle(nameBundle);
		} else {
			bundle = ResourceBundle.getBundle(nameBundle, locale);
		}
		if(bundle.containsKey(key)) {
			if(params == null || params.length <= 0) {
				result = bundle.getString(key);
			} else {
				result = MessageFormat.format(bundle.getString(key),params);
			}			
		}
		return result;
	}

	public static String getValue(String nameBundle, String key, Locale locale) {
		return getValue(nameBundle, key, locale, null);
	}

	public static String getValue(String nameBundle, String key) {
		return getValue(nameBundle, key, null, null);
	}

	public static String getValue(String key, Locale locale) {
		return getValue(LABELS_BUNDLE, key, locale, null);
	}

	public static String getValue(String key) {
		return getValue(LABELS_BUNDLE, key, null, null);
	}

	public static String getValue(String nameBundle, String key, Object[] params) {
		return getValue(nameBundle, key, null, params);
	}

	public static String getValue(String key, Locale locale, Object[] params) {
		return getValue(LABELS_BUNDLE, key, locale, params);
	}

	public static String getValue(String key, Object[] params) {
		return getValue(LABELS_BUNDLE, key, null, params);
	}

	public static String getCompositeValue(String nameBundle, Locale locale, List<String> keys, List<String> separators, Object[] params, Boolean repeatSeparators) {
		StringBuilder result = new StringBuilder();
		int separatorCount = 0;
		for(int i=0; i < keys.size(); i++) {
			String key = keys.get(i);
			result.append(getValue(nameBundle, key, locale, params));
			if((i + 1) < keys.size()) {
				if(separators != null && separatorCount < separators.size()) {
					result.append(StringUtils.SPACE)
					.append(separators.get(separatorCount))
					.append(StringUtils.SPACE);
					separatorCount++;
					if(repeatSeparators && separatorCount >= separators.size()) {
						separatorCount = 0;
					}
				} else {
					result.append(StringUtils.SPACE);
				}
			}
		}
		return result.toString();
	}

	public static String getCompositeValue(List<String> keys, List<String> separators, Boolean repeatSeparators) {
		return getCompositeValue(LABELS_BUNDLE, null, keys, separators, null, repeatSeparators);
	}

	public static String getCompositeValue(String nameBundle, Locale locale, List<String> keys) {
		return getCompositeValue(nameBundle, locale, keys, null, null, null);
	}

	public static String getCompositeValue(Locale locale, List<String> keys, List<String> separators, Boolean repeatSeparators) {
		return getCompositeValue(LABELS_BUNDLE, locale, keys, separators, null, repeatSeparators);
	}

	public static String getCompositeValue(String nameBundle, List<String> keys, List<String> separators, Boolean repeatSeparators) {
		return getCompositeValue(nameBundle, null, keys, separators, null, repeatSeparators);
	}

	public static String getCompositeValue(String nameBundle, Locale locale, List<String> keys, List<String> separators, Boolean repeatSeparators) {
		return getCompositeValue(nameBundle, locale, keys, separators, null, repeatSeparators);
	}

	public static String getCompositeValue(List<String> keys, List<String> separators, Object[] params, Boolean repeatSeparators) {
		return getCompositeValue(LABELS_BUNDLE, null, keys, separators, params, repeatSeparators);
	}

	public static String getCompositeValue(String nameBundle, Locale locale, List<String> keys, Object[] params) {
		return getCompositeValue(nameBundle, locale, keys, null, params, null);
	}

	public static String getCompositeValue(Locale locale, List<String> keys, List<String> separators, Object[] params, Boolean repeatSeparators) {
		return getCompositeValue(LABELS_BUNDLE, locale, keys, separators, params, repeatSeparators);
	}

	public static String getCompositeValue(String nameBundle, List<String> keys, List<String> separators, Object[] params, Boolean repeatSeparators) {
		return getCompositeValue(nameBundle, null, keys, separators, params, repeatSeparators);
	}
	
}