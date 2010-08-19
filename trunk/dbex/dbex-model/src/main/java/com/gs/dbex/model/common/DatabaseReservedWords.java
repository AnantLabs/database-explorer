/**
 * 
 */
package com.gs.dbex.model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sabuj.das
 *
 */
public class DatabaseReservedWords {

	private Set<String> schemaNames;
	private Map<String, Set<String>> schemaTableNameMap;
	private Set<String> tableNames;
	private Map<String, Set<String>> tableColumnNameMap;
	
	private Set<String> systemFunctionNames;
	private Set<String> userFunctionNames;
	
	private Set<String> systemProcedureNames;
	private Set<String> userProcedureNames;
	
	public DatabaseReservedWords() {
		schemaNames = new HashSet<String>(); 
		schemaTableNameMap = new HashMap<String, Set<String>>();
		tableNames = new HashSet<String>();
		tableColumnNameMap = new HashMap<String, Set<String>>();
		userProcedureNames = new HashSet<String>();
		userFunctionNames = new HashSet<String>();
		systemProcedureNames = new HashSet<String>();
		systemFunctionNames = new HashSet<String>();
	}

	public Set<String> getSchemaNames() {
		return schemaNames;
	}

	public void setSchemaNames(Set<String> schemaNames) {
		this.schemaNames = schemaNames;
	}

	public Map<String, Set<String>> getSchemaTableNameMap() {
		return schemaTableNameMap;
	}

	public void setSchemaTableNameMap(Map<String, Set<String>> schemaTableNameMap) {
		this.schemaTableNameMap = schemaTableNameMap;
	}

	public Set<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(Set<String> tableNames) {
		this.tableNames = tableNames;
	}

	public Map<String, Set<String>> getTableColumnNameMap() {
		return tableColumnNameMap;
	}

	public void setTableColumnNameMap(Map<String, Set<String>> tableColumnNameMap) {
		this.tableColumnNameMap = tableColumnNameMap;
	}

	public Set<String> getSystemFunctionNames() {
		return systemFunctionNames;
	}

	public void setSystemFunctionNames(Set<String> systemFunctionNames) {
		this.systemFunctionNames = systemFunctionNames;
	}

	public Set<String> getUserFunctionNames() {
		return userFunctionNames;
	}

	public void setUserFunctionNames(Set<String> userFunctionNames) {
		this.userFunctionNames = userFunctionNames;
	}

	public Set<String> getSystemProcedureNames() {
		return systemProcedureNames;
	}

	public void setSystemProcedureNames(Set<String> systemProcedureNames) {
		this.systemProcedureNames = systemProcedureNames;
	}

	public Set<String> getUserProcedureNames() {
		return userProcedureNames;
	}

	public void setUserProcedureNames(Set<String> userProcedureNames) {
		this.userProcedureNames = userProcedureNames;
	}

	
}
