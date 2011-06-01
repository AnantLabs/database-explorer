/**
 * 
 */
package com.gs.dbex.service;

import java.util.List;
import java.util.Set;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.ResultSetDataTable;

/**
 * @author sabuj.das
 *
 */
public interface DatabaseMetadataService {

	String BEAN_NAME = "databaseMetadataService";
	
	public Schema getSchemaDetails(ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException ;
	
	public Database getDatabaseDetails(ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException;
	
	public Table getTableDetails(ConnectionProperties connectionProperties, String schemaName, String tableName) throws DbexException;
	
	public Column getColumnDetails(ConnectionProperties connectionProperties, String tableName, String columnName) throws DbexException;
	
	public List<Column> getAllColumnDetails(ConnectionProperties connectionProperties, String tableName) throws DbexException;
	
	public List<Table> getAllTableDetails(ConnectionProperties connectionProperties) throws DbexException;

	public ResultSetDataTable getAllConstraints(ConnectionProperties connectionProperties, String schemaName, String tableName) throws DbexException;
	
	public ResultSetDataTable getAllConstraints(ConnectionProperties connectionProperties, Table table) throws DbexException;
	
	public Set<String> getAvailableSchemaNames(ConnectionProperties connectionProperties) throws DbexException;
	
}
