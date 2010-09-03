/**
 * 
 */
package com.gs.dbex.service;

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

}
