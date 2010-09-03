/**
 * 
 */
package com.gs.dbex.service.impl;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.dbex.service.QueryExecutionService;

/**
 * @author sabuj.das
 *
 */
public class QueryExecutionServiceImpl implements QueryExecutionService {

	@Override
	public PaginationResult executePaginatedQuery(
			ConnectionProperties connectionProperties,
			PaginationResult paginationResult) throws DbexException {
		if(null == connectionProperties){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		if(null == paginationResult)
			paginationResult = new PaginationResult(0, 30);
		
		
		
		return paginationResult;
	}

}
