/**
 * 
 */
package com.gs.dbex.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.dbex.service.QueryExecutionService;

/**
 * @author sabuj.das
 *
 */
public class QueryExecutionServiceImpl implements QueryExecutionService {

	private static final Logger logger = Logger.getLogger(QueryExecutionServiceImpl.class);
	
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
	public String preparePaginationQuery(ConnectionProperties connectionProperties, Table table) {
		StringBuffer buffer = new StringBuffer();
		if(DatabaseTypeEnum.ORACLE.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ( SELECT ")
				.append(table.getColumnNames(',')).append(",").append(" ROWNUM AS LIMIT FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
				.append(" ) WHERE LIMIT >= :FROM AND LIMIT < :TO");
			return buffer.toString();
		}
		if(DatabaseTypeEnum.MYSQL.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
				.append(" LIMIT :FROM :TO ");
			return buffer.toString();
		}
		if(DatabaseTypeEnum.MSSQL_2005.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			buffer.append("SELECT TOP :TO * ")
					.append("FROM ")
					.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
					.append(") AS X ")
					.append("EXCEPT ")
					.append("SELECT * FROM ( ")
					.append("SELECT TOP :FROM * ")
					.append("FROM ")
					.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
					.append(") AS Y ");
			return buffer.toString();
		}
		buffer = new StringBuffer();
		buffer.append("SELECT ")
			.append(table.getColumnNames(','))
			.append(" FROM ")
			.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase());
		return buffer.toString();
	}

	@Override
	public ResultSet getLimitedResultset(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		QueryExecutionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getQueryExecutionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
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
		QueryExecutionIntegration integration = IntegrationBeanFactory.getBeanFactory()
			.getQueryExecutionIntegration(DatabaseTypeEnum.getDatabaseTypeEnum(connectionProperties.getDatabaseType()));
		if(integration == null){
			logger.debug("Integration point not found.");
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		return integration.getLimitedResultset(connection, table, rowFrom, rowTo);
	}
}
