/**
 * 
 */
package com.gs.dbex.integration;

import java.sql.Connection;
import java.sql.ResultSet;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;

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
}
