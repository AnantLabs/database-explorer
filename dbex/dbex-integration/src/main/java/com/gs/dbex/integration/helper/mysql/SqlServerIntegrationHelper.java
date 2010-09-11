package com.gs.dbex.integration.helper.mysql;

import org.apache.log4j.Logger;

import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 * 
 */
public final class SqlServerIntegrationHelper {

	private static final Logger logger = Logger
			.getLogger(SqlServerIntegrationHelper.class);
	private static SqlServerIntegrationHelper instance;

	private SqlServerIntegrationHelper() {

	}

	public static SqlServerIntegrationHelper getInstance() {
		if (instance == null)
			instance = new SqlServerIntegrationHelper();
		return instance;
	}

	public String preparePaginationQuery(Table table) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: preparePaginationQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT TOP ? * ")
				.append("FROM ")
				.append(table.getSchemaName().toUpperCase() + "."
						+ table.getModelName().toUpperCase())
				.append(") AS X ")
				.append("EXCEPT ")
				.append("SELECT * FROM ( ")
				.append("SELECT TOP ? * ")
				.append("FROM ")
				.append(table.getSchemaName().toUpperCase() + "."
						+ table.getModelName().toUpperCase()).append(") AS Y ");
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: preparePaginationQuery()");
		}
		return buffer.toString();
	}
}
