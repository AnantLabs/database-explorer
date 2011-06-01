/**
 * 
 */
package com.gs.dbex.core;

import java.sql.Connection;
import java.sql.SQLException;

import com.gs.dbex.model.DatabaseReservedWordsUtil;

/**
 * @author sabuj.das
 * 
 */
public interface DbGrabber {

	
	DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	
	public String grabSqlKeyWords(String connectionName, Connection connection)
			throws SQLException;

	
}
