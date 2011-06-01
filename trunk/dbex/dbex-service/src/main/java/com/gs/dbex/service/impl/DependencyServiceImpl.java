package com.gs.dbex.service.impl;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.DependencyIntegration;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.dependency.TableDependency;
import com.gs.dbex.service.DependencyService;

public class DependencyServiceImpl implements DependencyService {

	private static final Logger logger = Logger.getLogger(DependencyServiceImpl.class);
	
	private DependencyIntegration getIntegrationPoint(ConnectionProperties connectionProperties) throws DbexException{
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DependencyIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDependencyIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration;
	}
	
	@Override
	public TableDependency generateTableDependency(ConnectionProperties connectionProperties, String tableName) throws DbexException {
		DependencyIntegration dependencyIntegration = getIntegrationPoint(connectionProperties);
		if(null != dependencyIntegration){
			return dependencyIntegration.generateTableDependency(connectionProperties, tableName);
		}
		return null;
	}

}
