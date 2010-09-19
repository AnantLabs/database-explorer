/**
 * 
 */
package com.gs.dbex.model.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sabuj.das
 *
 */
public class DatabaseReservedWords {

	private Map<String, ConnectionBasedReservedWords> connectionBasedReservedWordsMap;
	
	public DatabaseReservedWords() {
		connectionBasedReservedWordsMap = new HashMap<String, ConnectionBasedReservedWords>();
	}

	public Map<String, ConnectionBasedReservedWords> getConnectionBasedReservedWordsMap() {
		return connectionBasedReservedWordsMap;
	}

	public void setConnectionBasedReservedWordsMap(
			Map<String, ConnectionBasedReservedWords> connectionBasedReservedWordsMap) {
		this.connectionBasedReservedWordsMap = connectionBasedReservedWordsMap;
	}
	
	public void addConnectionBasedReservedWords(String connectionName, ConnectionBasedReservedWords connectionBasedReservedWords){
		connectionBasedReservedWordsMap.put(connectionName, connectionBasedReservedWords);
	}
	
	public ConnectionBasedReservedWords getConnectionBasedReservedWords(String connectionName){
		return connectionBasedReservedWordsMap.get(connectionName);
	}

	public Set<String> getSchemaNames(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getSchemaNames();
	}

	public Set<String> getTableNames(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getTableNames();
	}

	public Map<String, Set<String>> getSchemaTableNameMap(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getSchemaTableNameMap();
	}

	public Map<String, Set<String>> getTableColumnNameMap(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getTableColumnNameMap();
	}

	public Set<String> getSystemFunctionNames(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getSystemFunctionNames();
	}

	public Set<String> getUserFunctionNames(String connectionName) {
		if(null == getConnectionBasedReservedWords(connectionName)){
			ConnectionBasedReservedWords reservedWords = new ConnectionBasedReservedWords();
			addConnectionBasedReservedWords(connectionName, reservedWords);
		}
		return getConnectionBasedReservedWords(connectionName).getUserFunctionNames();
	}
}
