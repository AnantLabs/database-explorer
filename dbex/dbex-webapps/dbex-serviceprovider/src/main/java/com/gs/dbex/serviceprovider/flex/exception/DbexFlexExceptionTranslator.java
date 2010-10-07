package com.gs.dbex.serviceprovider.flex.exception;

import org.apache.log4j.Logger;
import org.springframework.flex.core.ExceptionTranslator;
import org.springframework.util.ClassUtils;

import com.gs.dbex.common.exception.DbexException;

import flex.messaging.MessageException;


/**
 * @author Sabuj Das
 *
 */
public class DbexFlexExceptionTranslator implements ExceptionTranslator{

	private static final Logger logger = Logger.getLogger(DbexFlexExceptionTranslator.class);
	
	@Override
	public boolean handles(Class<?> clazz) {
		return !(ClassUtils.isAssignable(DbexException.class, clazz) || 
			    ClassUtils.isAssignable(DbexException.class, clazz));

	}

	@Override
	public MessageException translate(Throwable t) {
		MessageException ex = new MessageException();
	    ex.setCode("Application.Service");
	    ex.setMessage(t.getLocalizedMessage());
	    ex.setRootCause(t);
	    logger.error("Application.Service", ex);
	    return ex;
	}

}
