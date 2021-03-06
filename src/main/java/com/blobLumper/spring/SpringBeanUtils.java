package com.blobLumper.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		applicationContext = ctx;
	}
	
	public static <T extends Object> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
	
	
}
