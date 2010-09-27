/**
 * 
 */
package com.gs.dbex.integration.helper.oracle;

import org.apache.log4j.Logger;

import com.gs.dbex.model.db.Table;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class OracleIntegrationHelper {

	private static final Logger logger = Logger.getLogger(OracleIntegrationHelper.class);
	private static OracleIntegrationHelper instance;

	private OracleIntegrationHelper() {

	}

	public static OracleIntegrationHelper getInstance() {
		if (instance == null)
			instance = new OracleIntegrationHelper();
		return instance;
	}

	public String preparePaginationQuery(Table table) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: preparePaginationQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ( SELECT ")
				.append(table.getColumnNames(','))
				.append(",")
				.append(" ROWNUM AS LIMIT FROM ")
				.append(table.getSchemaName().toUpperCase() + "."
						+ table.getModelName().toUpperCase())
				.append(" ) WHERE LIMIT >= ? AND LIMIT < ?");
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: preparePaginationQuery()");
		}
		return buffer.toString();
	}

	public String prepareTotalRecordsSQL(Table databaseTable) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: prepareTotalRecordsSQL() for table:= " + databaseTable.getModelName());
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(*) FROM " )
			.append(databaseTable.getSchemaName().toUpperCase() + "." + databaseTable.getModelName().toUpperCase());
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: prepareTotalRecordsSQL()");
		}
		return buffer.toString();
	}

	public String prepareFilteredRecordsSQL(Table databaseTable,
			String filterSubQuery) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: prepareFilteredRecordsSQL() for table:= " + databaseTable.getModelName()
					+ " With filterSubQuery:= " + filterSubQuery);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(*) FROM " )
			.append(databaseTable.getSchemaName().toUpperCase() + "." + databaseTable.getModelName().toUpperCase());
		if(StringUtil.hasValidContent(filterSubQuery)){
			buffer.append(" WHERE ").append(filterSubQuery);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: prepareFilteredRecordsSQL()");
		}
		return buffer.toString();
	}

	public String prepareFilteredQuery(Table table,
			String filterSubQuery) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: prepareFilteredQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase());
		if(StringUtil.hasValidContent(filterSubQuery)){
			buffer.append(" WHERE ").append(filterSubQuery);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: prepareFilteredQuery()");
		}
		return buffer.toString();
	}
	
	

}
