/**
 * 
 */
package com.gs.dbex.serviceprovider;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.converter.ConnectionPropertiesVOConverter;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;
import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.DatabaseMetadataService;

/**
 * @author sabuj.das
 *
 */
public class DatabaseConnectionDelegate {

	private static final Logger logger = Logger.getLogger(DatabaseConnectionDelegate.class);
	
	private DatabaseConnectionService databaseConnectionService;
	private DatabaseMetadataService databaseMetadataService;
	
	public DatabaseConnectionDelegate() {
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseConnectionService getDatabaseConnectionService() {
		return databaseConnectionService;
	}

	public void setDatabaseConnectionService(
			DatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}

	public DatabaseMetadataService getDatabaseMetadataService() {
		return databaseMetadataService;
	}

	public void setDatabaseMetadataService(
			DatabaseMetadataService databaseMetadataService) {
		this.databaseMetadataService = databaseMetadataService;
	}



	public Database connect(ConnectionPropertiesVO connectionPropertiesVO) throws DbexException{
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- connect()");
		}
		ConnectionProperties connectionProperties 
			= ConnectionPropertiesVOConverter.convertVoToModel(connectionPropertiesVO);
		if(null == connectionProperties){
			throw new DbexException(ErrorCodeConstants.INVALID_CONNECTION_PROPERTIES, 
					"Invalid Connection Configuration.");
		}
		Database database = null;
		Boolean connected = getDatabaseConnectionService().connectToDatabase(connectionProperties);
		if(connected){
			database = getDatabaseMetadataService().getDatabaseDetails(connectionProperties, ReadDepthEnum.DEEP);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- connect()");
		}
		return database;
	}
	
	public Boolean testConnection(ConnectionPropertiesVO connectionPropertiesVO){
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- testConnection()");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- testConnection()");
		}
		return true;
	}
	
	public Boolean disconnect(ConnectionPropertiesVO connectionPropertiesVO){
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- disconnect()");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- disconnect()");
		}
		return true;
	}
}
