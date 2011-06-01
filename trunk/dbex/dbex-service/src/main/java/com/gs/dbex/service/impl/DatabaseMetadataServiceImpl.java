package com.gs.dbex.service.impl;

import java.util.List;
import java.util.Set;

import com.gs.dbex.common.enums.DatabaseStorageTypeEnum;
import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.DatabaseMetadataIntegration;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.utils.jdbc.ResultSetDataTable;

public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

	
	public Set<String> getAvailableSchemaNames(
			ConnectionProperties connectionProperties) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getAvailableSchemaNames(connectionProperties);
	}
	
	public Database getDatabaseDetails(
			ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.readDatabase(connectionProperties, ReadDepthEnum.SHALLOW);
	}

	@Override
	public Schema getSchemaDetails(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		// TODO invoke appropriate integration method
		return null;
	}

	@Override
	public Table getTableDetails(ConnectionProperties connectionProperties, String schemaName,
			String tableName) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		
		return integration.readTable(connectionProperties, schemaName, tableName, ReadDepthEnum.DEEP);
	}

	@Override
	public Column getColumnDetails(ConnectionProperties connectionProperties,
			String tableName, String columnName)  throws DbexException{
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		// TODO invoke appropriate integration method
		return null;
	}

	@Override
	public List<Column> getAllColumnDetails(
			ConnectionProperties connectionProperties, String tableName) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		// TODO invoke appropriate integration method
		return null;
	}

	@Override
	public List<Table> getAllTableDetails(
			ConnectionProperties connectionProperties) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		// TODO invoke appropriate integration method
		return null;
	}

	@Override
	public ResultSetDataTable getAllConstraints(ConnectionProperties connectionProperties, Table table) throws DbexException {
		if(table != null)
			return getAllConstraints(connectionProperties, table.getSchemaName(), table.getModelName());
		return null;
	}

	@Override
	public ResultSetDataTable getAllConstraints(
			ConnectionProperties connectionProperties, String schemaName,
			String tableName) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		DatabaseMetadataIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getDatabaseMetadataIntegration(
					DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()),
					DatabaseStorageTypeEnum.getDatabaseStorageTypeEnum(connectionProperties.getDatabaseConfiguration().getStorageType()));
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		
		return null;
	}
}
