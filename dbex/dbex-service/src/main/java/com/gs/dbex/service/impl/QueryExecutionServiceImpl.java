/**
 * 
 */
package com.gs.dbex.service.impl;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
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

	@Override
	public String preparePaginationQuery(ConnectionProperties connectionProperties, Table table) {
		StringBuffer buffer = new StringBuffer();
		if(DatabaseTypeEnum.ORACLE.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ( SELECT ")
				.append(table.getColumnNames(',')).append(",").append(" ROWNUM AS LIMIT FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
				.append(" ) WHERE LIMIT >= :FROM AND LIMIT < :TO");
			return buffer.toString();
		}
		if(DatabaseTypeEnum.MYSQL.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
				.append(" LIMIT :FROM :TO ");
			return buffer.toString();
		}
		if(DatabaseTypeEnum.MSSQL_2005.getCode().equals(connectionProperties.getDatabaseType())){
			buffer = new StringBuffer();
			/* 
			 	.append("SELECT * FROM ( ")
				    .append("SELECT TOP ? * ")
				    .append("FROM T ")
				.append(") AS X ")
				.append("EXCEPT ")
				.append("SELECT * FROM ( ")
				    .append("SELECT TOP ? * ")
				    .append("FROM T ")
				.append(") AS Y ");
			 
			 */
			buffer.append("SELECT TOP :TO * ")
					.append("FROM ")
					.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
					.append(") AS X ")
					.append("EXCEPT ")
					.append("SELECT * FROM ( ")
					.append("SELECT TOP :FROM * ")
					.append("FROM T ")
					.append(") AS Y ");
			return buffer.toString();
		}
		buffer = new StringBuffer();
		buffer.append("SELECT ")
			.append(table.getColumnNames(','))
			.append(" FROM ")
			.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase());
		return buffer.toString();
	}


}
