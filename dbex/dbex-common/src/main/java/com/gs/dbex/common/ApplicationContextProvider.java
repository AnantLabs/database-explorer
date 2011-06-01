package com.gs.dbex.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	private static ApplicationContextProvider instance;
	
	private ApplicationContextProvider(){}
	
	
	public synchronized static ApplicationContextProvider getInstance() {
		if(null == instance){
			instance = new ApplicationContextProvider();
		}
		return instance;
	}



	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}


	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	
}
