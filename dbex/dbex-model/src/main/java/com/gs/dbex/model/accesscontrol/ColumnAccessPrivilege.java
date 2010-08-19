/**
 * 
 */
package com.gs.dbex.model.accesscontrol;

/**
 * @author Sabuj Das
 *
 */
public class ColumnAccessPrivilege {

	private String columnName;
	private String tableName;
	private DatabaseAccessPrivilege databaseAccessPrivilege;
	
	public ColumnAccessPrivilege() {
		// TODO Auto-generated constructor stub
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public DatabaseAccessPrivilege getDatabaseAccessPrivilege() {
		return databaseAccessPrivilege;
	}

	public void setDatabaseAccessPrivilege(
			DatabaseAccessPrivilege databaseAccessPrivilege) {
		this.databaseAccessPrivilege = databaseAccessPrivilege;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
