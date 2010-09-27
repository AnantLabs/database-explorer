package com.gs.dbex.integration.impl.oracle;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleConnection;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.Transaction;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.integration.helper.oracle.OracleIntegrationHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.utils.exception.UtilityException;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.jdbc.ResultSetDataTable;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class OracleQueryExecutionIntegration implements
		QueryExecutionIntegration {

	private static final Logger logger = Logger.getLogger(OracleQueryExecutionIntegration.class);
	private OracleIntegrationHelper integrationHelper = OracleIntegrationHelper.getInstance();
	
	public OracleQueryExecutionIntegration() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(paginationResult == null)
			paginationResult = new PaginationResult(0, 30);
		OracleConnection connection = null;
		
		
		return null;
	}

	@Deprecated
	@Override
	public ResultSet getLimitedResultset(Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSetDataTable getLimitedDataTable(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getLimitedDataTable()");
		}
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String query = integrationHelper.preparePaginationQuery(table);
		if(!StringUtil.hasValidContent(query))
			return null;
		Connection connection = null;
		ResultSet resultSet = null;
		ResultSetDataTable dataTable = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			connection.setCatalog(table.getTableSchema());
			PreparedStatement preparedStatement = connection.prepareStatement(
					query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setInt(1, rowFrom);
			preparedStatement.setInt(2, rowTo);
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + query + " ] start:=" 
						+ rowFrom + " to:=" + rowTo);
			}
			resultSet = preparedStatement.executeQuery();
			dataTable = new ResultSetDataTable(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} catch (UtilityException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(resultSet, true);
			JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getLimitedDataTable()");
		}
		return dataTable;
	}

	@Override
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable) throws DbexException {
		int totalRows = 0;
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String countQuery = integrationHelper.prepareTotalRecordsSQL(databaseTable);
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			
			connection.setCatalog(databaseTable.getTableSchema());
			java.sql.Statement statement = connection.prepareStatement(countQuery);
			ResultSet rs = statement.executeQuery(countQuery);
			if(rs != null){
				while(rs.next()){
					totalRows = rs.getInt(1);
				}
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

	@Override
	public Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> createTransaction(
			ConnectionProperties connectionProperties) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null;
		Transaction<Connection, Statement, PreparedStatement, ResultSet> transaction = null;
		try {
			connection = (Connection) connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			connection.setAutoCommit(false);
			transaction = new Transaction<Connection, Statement, PreparedStatement, ResultSet>(connection);
		} catch (SQLException e) {
			logger.error(e);
			JdbcUtil.close(connection);
			throw new DbexException(null, e.getMessage());
		}
		return transaction;
	}

	@Override
	public ResultSetDataTable executeQuery(
			ConnectionProperties connectionProperties,
			String sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(transaction == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null;
		ResultSetDataTable dataTable = null;
		try {
			connection = (Connection) transaction.begin();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			connection.setCatalog(transaction.getCatalogName());
			PreparedStatement ps = transaction.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();
			dataTable = new ResultSetDataTable(rs);
			logger.info("Total " + dataTable.getRowCount() + " found by the query : " + sqlQuery);
			JdbcUtil.close(rs, false);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} catch (UtilityException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		return dataTable;
	}

	@Override
	public int executeNonQuery(
			ConnectionProperties connectionProperties,
			String sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		int rows = 0;
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(transaction == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null;
		try {
			connection = (Connection) transaction.begin();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			connection.setCatalog(transaction.getCatalogName());
			PreparedStatement ps = (PreparedStatement) transaction.prepareStatement(sqlQuery);
			rows = transaction.executeUpdate(sqlQuery);
			logger.info("Total " + rows + " changed : " + sqlQuery);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			//JdbcUtil.close(connection);
		}
		return rows;
	}

	@Override
	public boolean abortTransaction(
			ConnectionProperties connectionProperties,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(transaction == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		try {
			transaction.abort();
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} finally {
			transaction.close();
		}
		return true;
	}

	@Override
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable, String filterSubQuery) throws DbexException {
		int totalRows = 0;
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String countQuery = integrationHelper.prepareFilteredRecordsSQL(databaseTable, filterSubQuery);
		Connection connection = null;
		try {
			connection = (Connection) connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			
			connection.setCatalog(databaseTable.getTableSchema());
			java.sql.Statement statement = connection.prepareStatement(countQuery);
			ResultSet rs = statement.executeQuery(countQuery);
			if(rs != null){
				while(rs.next()){
					totalRows = rs.getInt(1);
				}
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

	@Override
	public ResultSetDataTable getFilteredDataTable(
			ConnectionProperties connectionProperties, Table databaseTable,
			String filterSubQuery) throws DbexException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getFilteredDataTable()");
		}
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		String query = integrationHelper.prepareFilteredQuery(databaseTable, filterSubQuery);
		if(!StringUtil.hasValidContent(query))
			return null;
		Connection connection = null;
		ResultSet resultSet = null;
		ResultSetDataTable dataTable = null;
		try {
			connection = (Connection) connectionProperties.getDataSource().getConnection();
			if(connection == null){
				throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
			}
			connection.setCatalog(databaseTable.getTableSchema());
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(
					query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + query + " ]");
			}
			resultSet = preparedStatement.executeQuery();
			dataTable = new ResultSetDataTable(resultSet);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} catch (UtilityException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(resultSet, true);
			JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getFilteredDataTable()");
		}
		return dataTable;
	}

}
