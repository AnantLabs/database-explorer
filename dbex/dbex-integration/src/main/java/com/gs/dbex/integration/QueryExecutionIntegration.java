/**
 * 
 */
package com.gs.dbex.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.Transaction;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.utils.jdbc.ResultSetDataTable;

/**
 * @author Sabuj Das
 * 
 */
public interface QueryExecutionIntegration {

	/**
	 * 
	 * @param connectionProperties
	 * @param paginationResult
	 * @return
	 * @throws DbexException
	 */
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException;

	/**
	 * 
	 * @param connection
	 * @param table
	 * @param rowFrom
	 * @param rowTo
	 * @return
	 * @throws DbexException 
	 */
	public ResultSet getLimitedResultset(
			Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException;
	
	/**
	 * 
	 * @param connection
	 * @param table
	 * @param rowFrom
	 * @param rowTo
	 * @return
	 * @throws DbexException 
	 */
	public ResultSetDataTable getLimitedDataTable(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException;

	/**
	 * 
	 * @param connectionProperties
	 * @param databaseTable
	 * @return
	 * @throws DbexException
	 */
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @return <code>Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet></code>
	 * @throws DbexException
	 */
	public Transaction<
		? extends Connection, 
		? extends Statement, 
		? extends PreparedStatement, 
		? extends ResultSet> createTransaction(ConnectionProperties connectionProperties) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @param sqlQuery
	 * @param transaction <code>Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet></code>
	 * @return
	 * @throws DbexException
	 */
	public ResultSetDataTable executeQuery(
		ConnectionProperties connectionProperties,
		String sqlQuery, 
		Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet> transaction) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @param sqlQuery
	 * @param transaction <code>Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet></code>
	 * @return
	 * @throws DbexException
	 */
	public int executeNonQuery(
		ConnectionProperties connectionProperties,
		String sqlQuery, 
		Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet> transaction) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @param transaction <code>Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet></code>
	 * @return
	 * @throws DbexException
	 */
	public boolean abortTransaction(
		ConnectionProperties connectionProperties,
		Transaction<
			? extends Connection, 
			? extends Statement, 
			? extends PreparedStatement, 
			? extends ResultSet> transaction) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @param databaseTable
	 * @param filterSubQuery
	 * @return
	 * @throws DbexException
	 */
	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable, String filterSubQuery) throws DbexException;
	
	/**
	 * 
	 * @param connectionProperties
	 * @param databaseTable
	 * @param filterSubQuery
	 * @return
	 * @throws DbexException
	 */
	public ResultSetDataTable getFilteredDataTable(
			ConnectionProperties connectionProperties, Table databaseTable,
			String filterSubQuery) throws DbexException;
}
