/**
 * 
 */
package com.gs.dbex.integration;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj Das
 *
 */
public interface DDLGenerationIntegration {

	public String generateDdlForSchema(ConnectionProperties connectionProperties, String schemaName) throws DbexException;
	
	public String generateDdlForTable(ConnectionProperties connectionProperties, String schemaName, String tableName) throws DbexException;
	
	
	
}
