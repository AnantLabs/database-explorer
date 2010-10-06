package com.gs.dbex.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.historyMgr.dao.DbexUserDao;
import com.gs.dbex.model.User;
import com.gs.dbex.service.DbexUserService;
import com.gs.utils.text.StringUtil;

@Transactional
public class DbexUserServiceImpl implements DbexUserService {

	private DbexUserDao dbexUserDao;

	public DbexUserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public DbexUserDao getDbexUserDao() {
		return dbexUserDao;
	}

	public void setDbexUserDao(DbexUserDao dbexUserDao) {
		this.dbexUserDao = dbexUserDao;
	}

	public User login(String userName, String password)  throws DbexException{
		if(!StringUtil.hasValidContent(userName)){
			throw new DbexException("INVALID_USER_NAME", "Invalid User Name");
		}
		User user = getDbexUserDao().getUser(userName);
		if(null == user){
			throw new DbexException("INVALID_USER_NAME", "Invalid User Name");
		}
		if(null != password && password.equals(user.getPassword())){
			throw new DbexException("INVALID_PASSWORD", "Invalid Password");
		}
		return user;
	}
}
