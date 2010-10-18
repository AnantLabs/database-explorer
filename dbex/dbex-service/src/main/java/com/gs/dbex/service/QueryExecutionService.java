/**
 * 
 */
package com.gs.dbex.service;

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
 * @author sabuj.das
 * 
 */
public interface QueryExecutionService {

	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException;

	public ResultSet getLimitedResultset(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException;

	public ResultSetDataTable getLimitedDataTable(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException;

	public ResultSet getLimitedResultset(
			ConnectionProperties connectionProperties, Connection connection,
			Table table, int rowFrom, int rowTo) throws DbexException;

	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable) throws DbexException;

	public Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> createTransaction(
			ConnectionProperties connectionProperties) throws DbexException;

	public ResultSetDataTable executeQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException;

	public int executeNonQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException;

	public boolean abortTransaction(
			ConnectionProperties connectionProperties,
			Transaction<? extends Connection, ? extends Statement, ? extends PreparedStatement, ? extends ResultSet> transaction)
			throws DbexException;

	public ResultSetDataTable getFilteredDataTable(
			ConnectionProperties connectionProperties, Table databaseTable,
			String filterSubQuery) throws DbexException;

	public int getTotalRecords(ConnectionProperties connectionProperties,
			Table databaseTable, String filterSubQuery) throws DbexException;
	
	public ResultSetDataTable executeQuery(
			ConnectionProperties connectionProperties,
			SqlQuery sqlQuery)
			throws DbexException ;
}
