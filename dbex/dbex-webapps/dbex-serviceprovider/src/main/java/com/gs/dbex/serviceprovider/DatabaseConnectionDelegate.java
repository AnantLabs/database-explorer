/**
 * 
 */
package com.gs.dbex.serviceprovider;

import java.util.List;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.converter.ConnectionPropertiesVOConverter;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;
import com.gs.dbex.service.ConnectionPropertiesService;
import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.DatabaseMetadataService;

/**
 * @author sabuj.das
 *
 */
public class DatabaseConnectionDelegate {

	private static final Logger logger = Logger.getLogger(DatabaseConnectionDelegate.class);
	
	private static final DbexServiceProviderContext serviceProviderContext = DbexServiceProviderContext.getInstance();
	
	private DatabaseConnectionService databaseConnectionService;
	private DatabaseMetadataService databaseMetadataService;
	private ConnectionPropertiesService connectionPropertiesService;
	
	public DatabaseConnectionDelegate() {
		// TODO Auto-generated constructor stub
	}
	
	public ConnectionPropertiesService getConnectionPropertiesService() {
		return connectionPropertiesService;
	}

	public void setConnectionPropertiesService(
			ConnectionPropertiesService connectionPropertiesService) {
		this.connectionPropertiesService = connectionPropertiesService;
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
			serviceProviderContext.connectedConnectionPropertiesMap.put(connectionProperties.getConnectionName(), connectionProperties);
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
	
	public ConnectionPropertiesVO saveConnectionProperties(ConnectionPropertiesVO connectionPropertiesVO){
		if(null == connectionPropertiesVO)
			return null;
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- saveConnectionProperties()");
		}
		ConnectionProperties connectionProperties = ConnectionPropertiesVOConverter.convertVoToModel(connectionPropertiesVO);
		try {
			connectionProperties = getConnectionPropertiesService().saveConnectionProperties(connectionProperties);
		} catch (DbexException e) {
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- saveConnectionProperties()");
		}
		return ConnectionPropertiesVOConverter.convertModelToVO(connectionProperties);
	}
	
	public List<ConnectionPropertiesVO> saveAllConnectionProperties(List<ConnectionPropertiesVO> connectionPropertiesVOList){
		if(null == connectionPropertiesVOList)
			return null;
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- saveAllConnectionProperties()");
		}
		List<ConnectionProperties> props = null;
		try {
			props = getConnectionPropertiesService().saveAllConnectionProperties(
					ConnectionPropertiesVOConverter.convertVOsToModelList(connectionPropertiesVOList));
		} catch (DbexException e) {
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- saveAllConnectionProperties()");
		}
		return ConnectionPropertiesVOConverter.convertModelsToVOList(props);
	}
	
	
}
