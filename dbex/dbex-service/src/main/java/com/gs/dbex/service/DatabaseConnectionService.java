/**
 * 
 */
package com.gs.dbex.service;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public interface DatabaseConnectionService {

	String BEAN_NAME = "databaseConnectionService";
	
	public Boolean connectToDatabase(ConnectionProperties connectionProperties) throws DbexException;
	
	public Boolean closeConnection(ConnectionProperties connectionProperties) throws DbexException;
	
	
	
}
