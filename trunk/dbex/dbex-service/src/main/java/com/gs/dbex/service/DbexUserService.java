/**
 * 
 */
package com.gs.dbex.service;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.User;

/**
 * @author sabuj.das
 *
 */

public interface DbexUserService {

	public User login(String userName, String password)  throws DbexException;
	
}
