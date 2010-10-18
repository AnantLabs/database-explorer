package com.gs.dbex.serviceprovider;

import org.apache.log4j.Logger;

import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.converter.ConnectionPropertiesVOConverter;
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


	public ResultSetDataTable executeSingleQuery(String sql, ConnectionPropertiesVO connectionPropertiesVO){
		/*if(null != connectionPropertiesVO && StringUtil.hasValidContent(sql)){
			ConnectionProperties connectionProperties = ConnectionPropertiesVOConverter.convertVoToModel(connectionPropertiesVO);
			if(null != connectionProperties){
				
			}
		}*/
		
		try {
			return new ResultSetDataTable(null);
		} catch (UtilityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
