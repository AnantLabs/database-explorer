/**
 * 
 */
package com.gs.dbex.integration.impl.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.integration.helper.mysql.MySqlIntegrationHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.text.StringUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author Sabuj Das
 *
 */
public class MySqlQueryExecutionIntegration implements
		QueryExecutionIntegration {

	private static Logger logger = Logger.getLogger(MySqlQueryExecutionIntegration.class);
	private MySqlIntegrationHelper integrationHelper = MySqlIntegrationHelper.getInstance();
	
	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(paginationResult == null)
			paginationResult = new PaginationResult(0, 30);
		Connection connection = null;
		try {
			connection = (Connection) connectionProperties.getDataSource().getConnection();
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
			java.sql.Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getLimitedResultset()");
		}
		if(connection == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String query = integrationHelper.preparePaginationQuery(table);
		if(!StringUtil.hasValidContent(query))
			return null;
		ResultSet resultSet = null;
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(
					query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setInt(1, rowFrom);
			preparedStatement.setInt(2, rowTo - rowFrom);
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + preparedStatement.getPreparedSql() + " ] start:=" + rowFrom + " to:=" + rowTo);
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
}
