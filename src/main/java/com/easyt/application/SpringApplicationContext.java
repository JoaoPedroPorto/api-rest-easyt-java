package com.easyt.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext context;
	private static SpringApplicationContext springApplicationContext;
	
	private SpringApplicationContext() {}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringApplicationContext.context = context;
	}
	
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return context.getBean(beanName, clazz);
	}
	
	public static SpringApplicationContext getInstance() {
		if(springApplicationContext == null){
			springApplicationContext = new SpringApplicationContext();
		}
		return springApplicationContext;
	}
}