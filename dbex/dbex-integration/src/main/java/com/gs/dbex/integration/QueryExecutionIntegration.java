/**
 * 
 */
package com.gs.dbex.integration;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
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
	
}
