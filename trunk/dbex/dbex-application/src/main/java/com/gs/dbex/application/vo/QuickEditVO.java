/**
 * 
 */
package com.gs.dbex.application.vo;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Green Moon
 *
 */
public class QuickEditVO {

	private String tableName;
	private String schemaName;
	private String currentColumnName, currentColumnValue;
	private String pkColumnName;
	private String pkColumnValue;
	private ConnectionProperties connectionProperties;
	private String rowid, oraRowscn;
	
	public QuickEditVO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @return the currentColumnName
	 */
	public String getCurrentColumnName() {
		return currentColumnName;
	}

	/**
	 * @param currentColumnName the currentColumnName to set
	 */
	public void setCurrentColumnName(String currentColumnName) {
		this.currentColumnName = currentColumnName;
	}

	/**
	 * @return the currentColumnValue
	 */
	public String getCurrentColumnValue() {
		return currentColumnValue;
	}

	/**
	 * @param currentColumnValue the currentColumnValue to set
	 */
	public void setCurrentColumnValue(String currentColumnValue) {
		this.currentColumnValue = currentColumnValue;
	}

	/**
	 * @return the pkColumnName
	 */
	public String getPkColumnName() {
		return pkColumnName;
	}

	/**
	 * @param pkColumnName the pkColumnName to set
	 */
	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}

	/**
	 * @return the pkColumnValue
	 */
	public String getPkColumnValue() {
		return pkColumnValue;
	}

	/**
	 * @param pkColumnValue the pkColumnValue to set
	 */
	public void setPkColumnValue(String pkColumnValue) {
		this.pkColumnValue = pkColumnValue;
	}

	/**
	 * @return the connectionProperties
	 */
	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * @param connectionProperties the connectionProperties to set
	 */
	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * @return the rowid
	 */
	public String getRowid() {
		return rowid;
	}

	/**
	 * @return the oraRowscn
	 */
	public String getOraRowscn() {
		return oraRowscn;
	}

	/**
	 * @param rowid the rowid to set
	 */
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	/**
	 * @param oraRowscn the oraRowscn to set
	 */
	public void setOraRowscn(String oraRowscn) {
		this.oraRowscn = oraRowscn;
	}
	
	
}
