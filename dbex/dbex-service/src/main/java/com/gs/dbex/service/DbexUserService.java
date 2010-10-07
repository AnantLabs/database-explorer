/**
 * 
 */
package com.gs.dbex.service;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.User;
import com.gs.dbex.model.vo.UserVO;

/**
 * @author sabuj.das
 *
 */

public interface DbexUserService {

	public User login(String userName, String password) throws DbexException;

	public User register(UserVO userVO) throws DbexException;
	
}
