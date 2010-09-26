package com.gs.dbex.integration.helper.mysql;

import org.apache.log4j.Logger;

import com.gs.dbex.model.db.Table;
import com.gs.utils.text.StringUtil;

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
		buffer.append("SELECT * FROM ( SELECT TOP 75 * ")
				.append("FROM ")
				.append("dbo.[" + table.getModelName().toUpperCase())
				.append("]) AS X ")
				.append("EXCEPT ")
				.append("SELECT * FROM ( ")
				.append("SELECT TOP 30 * ")
				.append("FROM ")
				.append("dbo.[" + table.getModelName().toUpperCase())
				.append("]) AS Y ");
		//String s = "SELECT * FROM (SELECT TOP ? * FROM (SELECT TOP ? * from dbo.[" + table.getModelName().toUpperCase() + "] X) AS inner_tbl) AS outer_tbl";
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: preparePaginationQuery()");
		}
		//return "SELECT * FROM ( SELECT TOP 30 * FROM  dbo.[" + table.getModelName().toUpperCase()+") AS X ";
		return buffer.toString();
		//return s;
	}

	public String prepareTotalRecordsSQL(Table databaseTable) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: prepareTotalRecordsSQL() for table:= " + databaseTable.getModelName());
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(*) FROM " )
			.append("dbo.[" + databaseTable.getModelName().toUpperCase())
			.append("]");
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: prepareTotalRecordsSQL()");
		}
		return buffer.toString();
	}

	public String preparePaginationQuery(Table table, int rowFrom, int rowTo) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: preparePaginationQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM ( SELECT TOP ").append(rowTo).append(" * ").append("FROM ")
				.append("dbo.[" + table.getModelName().toUpperCase()).append("]) AS X ")
			.append("EXCEPT ")
				.append("SELECT * FROM ( SELECT TOP ").append(rowFrom).append(" * ").append("FROM ")
				.append("dbo.[" + table.getModelName().toUpperCase()).append("]) AS Y ");
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: preparePaginationQuery()");
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
			.append("dbo.[" + databaseTable.getModelName().toUpperCase())
			.append("]");
		if(StringUtil.hasValidContent(filterSubQuery)){
			buffer.append(" WHERE ").append(filterSubQuery);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL: [ " + buffer.toString() + " ]");
			logger.debug("Exit:: prepareFilteredRecordsSQL()");
		}
		return buffer.toString();
	}

	public String prepareFilteredQuery(Table table, String filterSubQuery) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: prepareFilteredQuery()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ")
				.append("* ")
				.append(" FROM ")
				.append("dbo.[" + table.getModelName().toUpperCase())
			.append("]");
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
