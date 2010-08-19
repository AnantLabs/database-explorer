/**
 * 
 */
package com.gs.dbex.service.impl;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.ConnectionUrlPopulationService;

/**
 * @author sabuj.das
 *
 */
public class ConnectionUrlPopulationServiceImpl implements
		ConnectionUrlPopulationService {

	private static final Logger logger = Logger.getLogger(ConnectionUrlPopulationServiceImpl.class);
	
	@Override
	public String populateConnectionURL(
			ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("Close database connection");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_POPULATE_URL);
		}
		
		
		/*DatabaseConnectionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseConnectionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}*/
		return null;
	}

	
	@Override
	public ConnectionProperties populateConnectionProperties(String url) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

}
