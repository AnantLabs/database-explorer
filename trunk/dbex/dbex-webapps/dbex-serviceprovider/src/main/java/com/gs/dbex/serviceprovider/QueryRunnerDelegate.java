package com.gs.dbex.serviceprovider;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.jdbc.ResultSetDataTable;
import com.gs.utils.text.StringUtil;

/**
 * @author Sabuj.das
 *
 */
public class QueryRunnerDelegate {

	private static final Logger logger = Logger.getLogger(QueryRunnerDelegate.class);
	private static final DbexServiceProviderContext serviceProviderContext = DbexServiceProviderContext.getInstance();
	
	private QueryExecutionService queryExecutionService;
	
	public QueryRunnerDelegate() {
		// TODO Auto-generated constructor stub
	}
	
	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}
	public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}


	public ResultSetDataTable executeSingleQuery(String sql, String connectionName) throws DbexException{
		ResultSetDataTable dataTable = null;
		if(StringUtil.hasValidContent(connectionName)){
			ConnectionProperties connectionProperties = serviceProviderContext.connectedConnectionPropertiesMap.get(connectionName);
			if(null != connectionProperties){
				SqlQuery sqlQuery = new SqlQuery(sql);
				dataTable = getQueryExecutionService().executeQuery(connectionProperties, sqlQuery);
			}
		}
		return dataTable;
	}
	
	public PaginationResult getPaginatedTableData(PaginationResult paginationResult, Table table, String connectionName) throws DbexException{
		PaginationResult result = new PaginationResult();
		result.setCurrentTable(table);
		result.setRowsPerPage(paginationResult.getRowsPerPage());
		result.setCurrentPage(paginationResult.getCurrentPage());
		if(StringUtil.hasValidContent(connectionName)){
			ConnectionProperties connectionProperties = serviceProviderContext.connectedConnectionPropertiesMap.get(connectionName);
			if(null != connectionProperties){
				result.setRowAttributes(getTotalRecords(connectionProperties, table));
				int rowNumFrom = result.getStartRow();
		    	int rowNumTo = result.getEndRow();
				ResultSetDataTable dataTable = getQueryExecutionService().getLimitedDataTable(connectionProperties, table, rowNumFrom, rowNumTo);
				result.setDataTable(dataTable);
			}
		}
		return result;
	}
	
	public PaginationResult getFilteredPaginatedTableData(Table table, String connectionName, String filterSubQuery) throws DbexException{
		PaginationResult result = new PaginationResult();
		result.setCurrentTable(table);
		if(StringUtil.hasValidContent(connectionName)){
			ConnectionProperties connectionProperties = serviceProviderContext.connectedConnectionPropertiesMap.get(connectionName);
			if(null != connectionProperties){
				int totalRecords = getTotalRecords(connectionProperties, table, filterSubQuery);
				result.setRowAttributes(totalRecords);
				result.setRowsPerPage(totalRecords);
				result.setCurrentPage(1);
				ResultSetDataTable dataTable = getQueryExecutionService().getFilteredDataTable(connectionProperties, table, filterSubQuery);
				result.setDataTable(dataTable);
			}
		}
		return result;
	}
	
	private int getTotalRecords(ConnectionProperties connectionProperties, Table table) throws DbexException{
    	int totalRows = 0;
    	QueryExecutionService queryExecutionService = getQueryExecutionService();
    	if(null != queryExecutionService){
			totalRows = getQueryExecutionService().getTotalRecords(connectionProperties, table);
    	}
		return totalRows;
    }
	
	private int getTotalRecords(ConnectionProperties connectionProperties, Table table, String filterSubQuery) throws DbexException{
		int totalRows = 0;
    	QueryExecutionService queryExecutionService = getQueryExecutionService();
    	if(null != queryExecutionService){
			totalRows = getQueryExecutionService().getTotalRecords(connectionProperties, table, filterSubQuery);
    	}
		return totalRows;
    }
	
}
