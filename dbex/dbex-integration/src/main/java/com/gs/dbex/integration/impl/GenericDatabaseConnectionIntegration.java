/**
 * 
 */
package com.gs.dbex.integration.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.DatabaseConnectionIntegration;
import com.gs.dbex.integration.helper.GenericDatabaseConnectionHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class GenericDatabaseConnectionIntegration implements
		DatabaseConnectionIntegration {
	private static final Logger logger = Logger.getLogger(GenericDatabaseConnectionIntegration.class);
	
	private GenericDatabaseConnectionHelper databaseConnectionHelper;
	
	public GenericDatabaseConnectionIntegration() {
		// TODO Auto-generated constructor stub
	}
	
	
	public GenericDatabaseConnectionHelper getDatabaseConnectionHelper() {
		return databaseConnectionHelper;
	}


	public void setDatabaseConnectionHelper(
			GenericDatabaseConnectionHelper databaseConnectionHelper) {
		this.databaseConnectionHelper = databaseConnectionHelper;
	}


	public Boolean connectToDatabase(ConnectionProperties connectionProperties) throws DbexException {
		if(null == connectionProperties)
			return false;
		if(logger.isDebugEnabled()){
			logger.debug("ENTER:: connectToDatabase() with ConnectionProperties [ " + connectionProperties.getConnectionName() + " ].");
		}
		try {
			if(getDatabaseConnectionHelper().testConnection(connectionProperties)){
				connectionProperties.setDataSource(getDatabaseConnectionHelper().createDataSource(connectionProperties));
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("EXIT:: connectToDatabase()");
		}
		return true;
	}

	
	public Boolean closeConnection(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}

}
