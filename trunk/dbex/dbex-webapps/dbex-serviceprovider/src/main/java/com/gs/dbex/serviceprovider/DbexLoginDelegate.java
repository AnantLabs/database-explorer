/**
 * 
 */
package com.gs.dbex.serviceprovider;

import com.gs.dbex.model.User;

/**
 * @author sabuj.das
 *
 */
public class DbexLoginDelegate {

	
	public User login(String loginName, String password){
		User u = new User();
		u.setFullName("Test Name");
		u.setUserId(1L);
		u.setEmailAddress("some.email@xyz.com");
		u.setUserName(loginName);
		u.setPassword(password);
		return u;
	}
	
}
