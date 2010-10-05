package com.gs.dbex.serviceprovider;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.User;
import com.gs.dbex.service.DbexUserService;

/**
 * @author Sabuj Das
 * 
 */
public class DbexUserDelegate {

	private static final Logger logger = Logger
			.getLogger(DbexUserDelegate.class);

	private DbexUserService dbexUserService;

	public DbexUserDelegate() {
		// TODO Auto-generated constructor stub
	}

	public DbexUserService getDbexUserService() {
		return dbexUserService;
	}

	public void setDbexUserService(DbexUserService dbexUserService) {
		this.dbexUserService = dbexUserService;
	}

	public User login(String loginName, String password) throws DbexException {
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- login() with loginName:=" + loginName
					+ " password:=" + password);
		}
		User u = getDbexUserService().login(loginName, password);
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- login()");
		}
		return u;
	}

}
