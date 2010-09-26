
package com.gs.dbex.model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
	
	public String getAllKeyWords(String connectionName, char separator){
		
		StringBuffer kwBuffer = new StringBuffer();
		Set<String> schemaNames = getSchemaNames(connectionName);
		if(null != schemaNames){
			Iterator<String> iterator = schemaNames.iterator();
			while(iterator.hasNext()){
				String sn = iterator.next();
				kwBuffer.append(sn);
				Set<String> tableNames = getTableNames(connectionName, sn);
				if(null != tableNames && tableNames.size() > 0 ){
					kwBuffer.append(separator);
					Iterator<String> Titerator = tableNames.iterator();
					while(Titerator.hasNext()){
						String tn = Titerator.next();
						kwBuffer.append(sn);
						Set<String> columnNames = getColumnNames(connectionName, tn);
						if(null != columnNames && columnNames.size() > 0 ){
							kwBuffer.append(separator);
							Iterator<String> Citerator = columnNames.iterator();
							while(Citerator.hasNext()){
								String cn = Citerator.next();
								kwBuffer.append(sn);
								if(Citerator.hasNext()){
									kwBuffer.append(separator);
								}
							}
						}
						if(Titerator.hasNext()){
							kwBuffer.append(separator);
						}
					}
				}
				if(iterator.hasNext()){
					kwBuffer.append(separator);
				}
			}
		}
		
		return kwBuffer.toString();
	}
	
	public Hashtable<String, String> getAllKeyWords(String connectionName){
		Hashtable<String, String> keywords = new Hashtable<String, String>();
		
		Set<String> schemaNames = getSchemaNames(connectionName);
		if(null != schemaNames){
			Iterator<String> iterator = schemaNames.iterator();
			while(iterator.hasNext()){
				String sn = iterator.next();
				keywords.put(sn, sn);
				Set<String> tableNames = getTableNames(connectionName, sn);
				if(null != tableNames && tableNames.size() > 0 ){
					Iterator<String> Titerator = tableNames.iterator();
					while(Titerator.hasNext()){
						String tn = Titerator.next();
						keywords.put(tn, tn);
						Set<String> columnNames = getColumnNames(connectionName, tn);
						if(null != columnNames && columnNames.size() > 0 ){
							Iterator<String> Citerator = columnNames.iterator();
							while(Citerator.hasNext()){
								String cn = Citerator.next();
								keywords.put(cn, cn);
							}
						}
					}
				}
			}
		}
		
		return keywords;
	}
}
