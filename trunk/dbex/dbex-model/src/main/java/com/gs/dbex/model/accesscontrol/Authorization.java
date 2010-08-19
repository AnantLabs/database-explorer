/**
 * 
 */
package com.gs.dbex.model.accesscontrol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sabuj Das
 *
 */
public class Authorization {

	private Map<String, List<TableAccessPrivilege>> tablePrivilegeListMap; 
	private Map<String, List<ColumnAccessPrivilege>> columnPrivilegeListMap;
	
	public Authorization() {
		tablePrivilegeListMap = new HashMap<String, List<TableAccessPrivilege>>();
		columnPrivilegeListMap = new HashMap<String, List<ColumnAccessPrivilege>>();
	}

	public Map<String, List<TableAccessPrivilege>> getTablePrivilegeListMap() {
		return tablePrivilegeListMap;
	}

	public void setTablePrivilegeListMap(
			Map<String, List<TableAccessPrivilege>> tablePrivilegeListMap) {
		this.tablePrivilegeListMap = tablePrivilegeListMap;
	}

	public Map<String, List<ColumnAccessPrivilege>> getColumnPrivilegeListMap() {
		return columnPrivilegeListMap;
	}

	public void setColumnPrivilegeListMap(
			Map<String, List<ColumnAccessPrivilege>> columnPrivilegeListMap) {
		this.columnPrivilegeListMap = columnPrivilegeListMap;
	}
	
	
}
