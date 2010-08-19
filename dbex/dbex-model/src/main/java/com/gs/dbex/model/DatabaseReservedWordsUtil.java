
package com.gs.dbex.model;

import java.util.HashSet;
import java.util.Set;

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
	
	public void addSchemaName(String schemaName){
		if(null != schemaName && !"".equals(schemaName))
			databaseReservedWords.getSchemaNames().add(schemaName);
	}
	
	public void addTableName(String schemaName, String tableName){
		if((null != schemaName && !"".equals(schemaName)) && (null != tableName && !"".equals(tableName))){
			databaseReservedWords.getSchemaNames().add(schemaName);
			databaseReservedWords.getTableNames().add(tableName);
			if(null != databaseReservedWords.getSchemaTableNameMap().get(schemaName)){
				databaseReservedWords.getSchemaTableNameMap().get(schemaName).add(tableName);
			}else{
				Set<String> set = new HashSet<String>();
				set.add(tableName);
				databaseReservedWords.getSchemaTableNameMap().put(schemaName, set);
			}
		}
	}
	
	public void addColumnName(String tableName, String columnName){
		if((null != tableName && !"".equals(tableName))
				&& (null != columnName && !"".equals(columnName))){
			databaseReservedWords.getTableNames().add(tableName);
			if(null != databaseReservedWords.getTableColumnNameMap().get(tableName)){
				databaseReservedWords.getTableColumnNameMap().get(tableName).add(columnName);
			}else{
				Set<String> set = new HashSet<String>();
				set.add(columnName);
				databaseReservedWords.getTableColumnNameMap().put(tableName, set);
			}
		}
	}
	
	public Set<String> getSchemaNames(){
		return databaseReservedWords.getSchemaNames();
	}
	
	public Set<String> getTableNames(){
		return databaseReservedWords.getTableNames();
	}
	
	public Set<String> getTableNames(String schemaName){
		if(null != schemaName && !"".equals(schemaName))
			return databaseReservedWords.getSchemaTableNameMap().get(schemaName);
		return null;
	}
	
	public Set<String> getColumnNames(String tableName){
		if(null != tableName && !"".equals(tableName)){
			return databaseReservedWords.getTableColumnNameMap().get(tableName);
		}
		return null;
	}
	
	public void addFunctionName(String functionName, boolean system){
		if(null != functionName && !"".equals(functionName)){
			if(system){
				databaseReservedWords.getSystemFunctionNames().add(functionName);
			}else{
				databaseReservedWords.getUserFunctionNames().add(functionName);
			}
		}
	}
	
	public Set<String> getFunctionNames(boolean system){
		if(system)
			return databaseReservedWords.getSystemFunctionNames();
		return databaseReservedWords.getUserFunctionNames();
	}
}
