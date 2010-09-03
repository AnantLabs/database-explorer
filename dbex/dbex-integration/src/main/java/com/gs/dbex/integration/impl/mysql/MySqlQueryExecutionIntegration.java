/**
 * 
 */
package com.gs.dbex.integration.impl.mysql;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.PaginationResult;

/**
 * @author Sabuj Das
 *
 */
public class MySqlQueryExecutionIntegration implements
		QueryExecutionIntegration {

	/* (non-Javadoc)
	 * @see com.gs.dbex.integration.QueryExecutionIntegration#executePaginatedQuery(com.gs.dbex.model.cfg.ConnectionProperties, com.gs.dbex.model.vo.PaginationResult)
	 */
	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

}
