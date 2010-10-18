/**
 * 
 */
package com.gs.dbex.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.Transaction;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.jdbc.ResultSetDataTable;

/**
 * @author sabuj.das
 *
 */
public class QueryExecutionServiceImpl implements QueryExecutionService {

	private static final Logger logger = Logger.getLogger(QueryExecutionServiceImpl.class);
	
	
	private QueryExecutionIntegration getIntegrationPoint(ConnectionProperties connectionProperties) throws DbexException{
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getQueryExecutionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration;
	}
	
	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		if(null == connectionProperties){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(null == paginationResult)
			paginationResult = new PaginationResult(0, 30);
		
		
		
		return paginationResult;
	}

	

	@Override
	public ResultSet getLimitedResultset(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return null;//integration.getLimitedResultset(connectionProperties.getDataSource().getConnection(), table, rowFrom, rowTo);
	}

	@Override
	public ResultSet getLimitedResultset(ConnectionProperties connectionProperties, Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException {
		if(connection == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getLimitedResultset(connection, table, rowFrom, rowTo);
	}
	
	@Override
	public ResultSetDataTable getLimitedDataTable(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getLimitedDataTable(connectionProperties, table, rowFrom, rowTo);
	}
	
	@Override
	public ResultSetDataTable getFilteredDataTable(
			ConnectionProperties connectionProperties, Table databaseTable,
			String filterSubQuery) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getFilteredDataTable(connectionProperties, databaseTable, filterSubQuery);
	}

	@Override
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getTotalRecords(connectionProperties, databaseTable);
	}
	
	@Override
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable, String filterSubQuery) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getTotalRecords(connectionProperties, databaseTable, filterSubQuery);
	}

	@Override
	public Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> createTransaction(
			ConnectionProperties connectionProperties) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.createTransaction(connectionProperties);
	}

	@Override
	public ResultSetDataTable executeQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.executeQuery(connectionProperties, sqlQuery.getQuery(), transaction);
	}
	
	@Override
	public int executeNonQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.executeNonQuery(connectionProperties, sqlQuery.getQuery(), transaction);
	}

	@Override
	public boolean abortTransaction(
			ConnectionProperties connectionProperties,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.abortTransaction(connectionProperties, transaction);
	}
	
	public ResultSetDataTable executeQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = getIntegrationPoint(connectionProperties);
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.executeQuery(connectionProperties, sqlQuery.getQuery());
	}
}
