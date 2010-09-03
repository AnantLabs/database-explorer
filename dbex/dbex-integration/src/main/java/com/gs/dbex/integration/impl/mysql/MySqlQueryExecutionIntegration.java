/**
 * 
 */
package com.gs.dbex.integration.impl.mysql;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.QueryExecutionIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.utils.jdbc.JdbcUtil;
import com.mysql.jdbc.Connection;

/**
 * @author Sabuj Das
 *
 */
public class MySqlQueryExecutionIntegration implements
		QueryExecutionIntegration {

	private static Logger logger = Logger.getLogger(MySqlQueryExecutionIntegration.class);
	
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

}
