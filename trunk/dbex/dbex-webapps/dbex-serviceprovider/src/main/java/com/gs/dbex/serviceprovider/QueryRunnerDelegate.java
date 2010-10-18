package com.gs.dbex.serviceprovider;

import org.apache.log4j.Logger;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.converter.ConnectionPropertiesVOConverter;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.exception.UtilityException;
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
}
