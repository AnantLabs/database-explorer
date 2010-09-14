package com.gs.dbex.integration.impl.mssql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.integration.helper.mysql.SqlServerIntegrationHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.utils.exception.UtilityException;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.jdbc.ResultSetDataTable;
import com.gs.utils.text.StringUtil;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
/**
 * @author Sabuj Das
 *
 */
public class SqlServerQueryExecutionIntegration implements
		QueryExecutionIntegration {

	private static Logger logger = Logger.getLogger(SqlServerQueryExecutionIntegration.class);
	private SqlServerIntegrationHelper integrationHelper = SqlServerIntegrationHelper.getInstance();
	
	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(paginationResult == null)
			paginationResult = new PaginationResult(0, 30);
		SQLServerConnection connection = null;
		try {
			connection = (SQLServerConnection) connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		
		return null;
	}

	public ResultSet getLimitedResultset(
			Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getLimitedResultset()");
		}
		if(connection == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		
		String query = integrationHelper.preparePaginationQuery(table, rowFrom, rowTo);
		if(!StringUtil.hasValidContent(query))
			return null;
		ResultSet resultSet = null;
		try {
			connection.setCatalog(table.getSchemaName());
			SQLServerPreparedStatement preparedStatement = (SQLServerPreparedStatement) connection.prepareStatement(
					query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + query + " ] start:=" + rowFrom + " to:=" + rowTo);
			}
			resultSet = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			//JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getLimitedResultset()");
		}
		return resultSet;
	}
	
	@Override
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable) throws DbexException {
		int totalRows = 0;
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String countQuery = integrationHelper.prepareTotalRecordsSQL(databaseTable);
		SQLServerConnection connection = null;
		try {
			connection = (SQLServerConnection) connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			
			connection.setCatalog(databaseTable.getSchemaName());
			PreparedStatement statement = connection.prepareStatement(countQuery);
			ResultSet rs = statement.executeQuery();
			if(rs != null){
				while(rs.next()){
					totalRows = rs.getInt(1);
				}
				rs.close();
			}
			logger.info("Total " + totalRows + " found by the query : " + countQuery);
			JdbcUtil.close(rs, false);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		return totalRows;
	}
}
