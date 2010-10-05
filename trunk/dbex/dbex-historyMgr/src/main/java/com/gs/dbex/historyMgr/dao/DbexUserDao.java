package com.gs.dbex.historyMgr.dao;

import com.gs.dbex.model.User;

public interface DbexUserDao {

	public User saveUser(User user);
	
	public User getUser(String userName);
	
	
}
