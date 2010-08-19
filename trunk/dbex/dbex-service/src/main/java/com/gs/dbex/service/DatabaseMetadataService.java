/**
 * 
 */
package com.gs.dbex.service;

import java.util.Set;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 *
 */
public interface DatabaseMetadataService {

	String BEAN_NAME = "databaseMetadataService";
	
	public Set<Schema> getSchemaDetails(ConnectionProperties connectionProperties, String schemaName);
	
	public Database getDatabaseDetails(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepthEnum) throws DbexException;
	
	public Database getDatabaseDetails(ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException;
	
	public Table getTableDetails(ConnectionProperties connectionProperties, String schemaName, String tableName);
	
	public Database getColumnDetails(ConnectionProperties connectionProperties);
	
	public Database getAllTableDetails(ConnectionProperties connectionProperties);
	
	
	
}
