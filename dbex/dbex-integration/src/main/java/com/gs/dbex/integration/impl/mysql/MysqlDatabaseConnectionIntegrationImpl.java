/**
 * 
 */
package com.gs.dbex.integration.impl.mysql;

import java.sql.SQLException;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.integration.impl.DatabaseConnectionIntegrationImpl;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class MysqlDatabaseConnectionIntegrationImpl extends
		DatabaseConnectionIntegrationImpl {

	private DatabaseConnectionHelper databaseConnectionHelper;
	
	public Boolean closeConnection(ConnectionProperties connectionProperties) {
		
		return null;
	}

	public Boolean connectToDatabase(ConnectionProperties connectionProperties)
			throws DbexException {
		try {
			if(getDatabaseConnectionHelper().testConnection(connectionProperties)){
				connectionProperties.setDataSource(getDatabaseConnectionHelper().createDataSource(connectionProperties));
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return true;
	}

	public DatabaseConnectionHelper getDatabaseConnectionHelper() {
		return databaseConnectionHelper;
	}

	public void setDatabaseConnectionHelper(
			DatabaseConnectionHelper databaseConnectionHelper) {
		this.databaseConnectionHelper = databaseConnectionHelper;
	}

	
}
