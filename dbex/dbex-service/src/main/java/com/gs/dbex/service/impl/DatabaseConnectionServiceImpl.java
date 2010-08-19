/**
 * 
 */
package com.gs.dbex.service.impl;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.DatabaseConnectionIntegration;
import com.gs.dbex.integration.DatabaseMetadataIntegration;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.DatabaseConnectionService;

/**
 * @author sabuj.das
 *
 */
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

	private static Logger logger = Logger.getLogger(DatabaseConnectionServiceImpl.class);
	

	public Boolean closeConnection(ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("Close database connection");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseConnectionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseConnectionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.closeConnection(connectionProperties);
	}


	public Boolean connectToDatabase(ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("Connecting to database");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseConnectionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseConnectionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		
		return integration.connectToDatabase(connectionProperties);
	}

}
