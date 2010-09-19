
package com.gs.dbex.model;

import java.util.HashSet;
import java.util.Set;

import com.gs.dbex.model.common.ConnectionBasedReservedWords;
import com.gs.dbex.model.common.DatabaseReservedWords;




/**
 * @author sabuj.das
 *
 */
public class DatabaseReservedWordsUtil {

	private static DatabaseReservedWordsUtil instance;
	
	private DatabaseReservedWords databaseReservedWords;
	
	private DatabaseReservedWordsUtil(){
		if(databaseReservedWords == null){
			databaseReservedWords = new DatabaseReservedWords();
		}
	}
	
	public static DatabaseReservedWordsUtil getInstance(){
		if(instance == null){
			instance = new DatabaseReservedWordsUtil();
		}
		return instance;
	}
	
	public void addSchemaName(String connectionName, String schemaName){
		if(null != schemaName && !"".equals(schemaName))
			databaseReservedWords.getSchemaNames(connectionName).add(schemaName);
	}
	
	public void addTableName(String connectionName, String schemaName, String tableName){
		if((null != schemaName && !"".equals(schemaName)) && (null != tableName && !"".equals(tableName))){
			databaseReservedWords.getSchemaNames(connectionName).add(schemaName);
			databaseReservedWords.getTableNames(connectionName).add(tableName);
			if(null != databaseReservedWords.getSchemaTableNameMap(connectionName).get(schemaName)){
				databaseReservedWords.getSchemaTableNameMap(connectionName).get(schemaName).add(tableName);
			}else{
				Set<String> set = new HashSet<String>();
				set.add(tableName);
				databaseReservedWords.getSchemaTableNameMap(connectionName).put(schemaName, set);
			}
		}
	}
	
	public void addColumnName(String connectionName, String tableName, String columnName){
		if((null != tableName && !"".equals(tableName))
				&& (null != columnName && !"".equals(columnName))){
			databaseReservedWords.getTableNames(connectionName).add(tableName);
			if(null != databaseReservedWords.getTableColumnNameMap(connectionName).get(tableName)){
				databaseReservedWords.getTableColumnNameMap(connectionName).get(tableName).add(columnName);
			}else{
				Set<String> set = new HashSet<String>();
				set.add(columnName);
				databaseReservedWords.getTableColumnNameMap(connectionName).put(tableName, set);
			}
		}
	}
	
	public Set<String> getSchemaNames(String connectionName){
		return databaseReservedWords.getSchemaNames(connectionName);
	}
	
	public Set<String> getTableNames(String connectionName){
		return databaseReservedWords.getTableNames(connectionName);
	}
	
	public Set<String> getTableNames(String connectionName, String schemaName){
		if(null != schemaName && !"".equals(schemaName))
			return databaseReservedWords.getSchemaTableNameMap(connectionName).get(schemaName);
		return null;
	}
	
	public Set<String> getColumnNames(String connectionName, String tableName){
		if(null != tableName && !"".equals(tableName)){
			return databaseReservedWords.getTableColumnNameMap(connectionName).get(tableName);
		}
		return null;
	}
	
	public void addFunctionName(String connectionName, String functionName, boolean system){
		if(null != functionName && !"".equals(functionName)){
			if(system){
				databaseReservedWords.getSystemFunctionNames(connectionName).add(functionName);
			}else{
				databaseReservedWords.getUserFunctionNames(connectionName).add(functionName);
			}
		}
	}
	
	public Set<String> getFunctionNames(String connectionName, boolean system){
		if(system)
			return databaseReservedWords.getSystemFunctionNames(connectionName);
		return databaseReservedWords.getUserFunctionNames(connectionName);
	}
	
	public void addConnectionBasedReservedWords(String connectionName, ConnectionBasedReservedWords connectionBasedReservedWords){
		databaseReservedWords.getConnectionBasedReservedWordsMap().put(connectionName, connectionBasedReservedWords);
	}
	
	public ConnectionBasedReservedWords getConnectionBasedReservedWords(String connectionName){
		return databaseReservedWords.getConnectionBasedReservedWordsMap().get(connectionName);
	}
}
