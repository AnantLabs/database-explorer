/**
 * 
 */
package com.gs.dbex.integration.helper.mysql;

import org.apache.log4j.Logger;

import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 * 
 */
public final class MySqlIntegrationHelper {

	private static final Logger logger = Logger.getLogger(MySqlIntegrationHelper.class);
	private static MySqlIntegrationHelper instance;

	private MySqlIntegrationHelper() {

	}

	public static MySqlIntegrationHelper getInstance() {
		if (instance == null)
			instance = new MySqlIntegrationHelper();
		return instance;
	}

	public String preparePaginationQuery(Table table) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: preparePaginationQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ")
				.append(table.getColumnNames(','))
				.append(" FROM ")
				.append(table.getSchemaName().toUpperCase() + "." + table.getModelName().toUpperCase())
				.append(" LIMIT ? , ? ");
		if(logger.isDebugEnabled()){
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
}
