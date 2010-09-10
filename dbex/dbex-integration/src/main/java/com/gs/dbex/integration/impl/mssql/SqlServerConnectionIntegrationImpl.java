/**
 * 
 */
package com.gs.dbex.integration.impl.mssql;

import java.sql.SQLException;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.integration.impl.DatabaseConnectionIntegrationImpl;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj.das
 *s
 */
public class SqlServerConnectionIntegrationImpl extends
		DatabaseConnectionIntegrationImpl {

	private DatabaseConnectionHelper databaseConnectionHelper;
	
	@Override
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

	
	@Override
	public Boolean closeConnection(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}


	public DatabaseConnectionHelper getDatabaseConnectionHelper() {
		return databaseConnectionHelper;
	}


	public void setDatabaseConnectionHelper(
			DatabaseConnectionHelper databaseConnectionHelper) {
		this.databaseConnectionHelper = databaseConnectionHelper;
	}

	
}
