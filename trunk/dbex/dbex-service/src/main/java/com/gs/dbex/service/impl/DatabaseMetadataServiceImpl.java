package com.gs.dbex.service.impl;

import java.util.Set;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.DatabaseConnectionIntegration;
import com.gs.dbex.integration.DatabaseMetadataIntegration;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;

public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

	public Set<Schema> getSchemaDetails(
			ConnectionProperties connectionProperties) {
		System.out.println("DatabaseMetadataServiceImpl");
		return null;
	}

	public Database getDatabaseDetails(
			ConnectionProperties connectionProperties, String schemaName,
			ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.readDatabase(connectionProperties, ReadDepthEnum.SHALLOW);
	}

	public Database getDatabaseDetails(
			ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.readDatabase(connectionProperties, ReadDepthEnum.SHALLOW);
	}

	
	public Database getAllTableDetails(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	public Database getColumnDetails(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	public Database getDatabaseDetails(
			ConnectionProperties connectionProperties, String schemaName) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.readDatabase(connectionProperties, ReadDepthEnum.SHALLOW);
	}

	public Set<Schema> getSchemaDetails(
			ConnectionProperties connectionProperties, String schemaName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Table getTableDetails(ConnectionProperties connectionProperties,
			String schemaName, String tableName) {
		// TODO Auto-generated method stub
		return null;
	}


}
