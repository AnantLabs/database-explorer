/**
 * 
 */
package com.gs.dbex.integration;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public interface DatabaseConnectionIntegration {

	public Boolean connectToDatabase(ConnectionProperties connectionProperties) throws DbexException;
	
	public Boolean closeConnection(ConnectionProperties connectionProperties);
	
}
