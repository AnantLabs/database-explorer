/**
 * 
 */
package com.gs.dbex.core;

import java.sql.SQLException;

import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 * 
 */
public interface DbGrabber {

	
	DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	
	public String grabSqlKeyWords(ConnectionProperties connectionProperties)
			throws SQLException;

	
}
