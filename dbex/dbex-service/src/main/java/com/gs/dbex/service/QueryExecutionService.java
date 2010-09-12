/**
 * 
 */
package com.gs.dbex.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;

/**
 * @author sabuj.das
 * 
 */
public interface QueryExecutionService {

	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException;

	public String preparePaginationQuery(ConnectionProperties connectionProperties, Table table);

	public ResultSet getLimitedResultset(
			ConnectionProperties connectionProperties, Table table,
			int rowFrom, int rowTo) throws DbexException;

	public ResultSet getLimitedResultset(ConnectionProperties connectionProperties, Connection connection, Table table,
			int rowFrom, int rowTo) throws DbexException;

	public int getTotalRecords(ConnectionProperties connectionProperties, Table databaseTable) throws DbexException;

}
