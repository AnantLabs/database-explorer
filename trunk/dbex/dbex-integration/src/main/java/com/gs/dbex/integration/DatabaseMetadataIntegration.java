/**
 * 
 */
package com.gs.dbex.integration;

import java.util.List;
import java.util.Set;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 *
 */
public interface DatabaseMetadataIntegration {

	/**
	 * Read the complete database.
	 * @param connectionProperties
	 * @param readDepthEnum
	 * @return
	 * @throws DbexException 
	 */
	public Database readDatabase(ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException;
	
	/**
	 * Read the complete schema.
	 * @param connectionProperties
	 * @param readDepthEnum
	 * @return
	 * @throws DbexException 
	 */
	public Schema readSchema(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepthEnum) throws DbexException;
	
	/**
	 * Read the complete table.
	 * @param connectionProperties
	 * @param readDepthEnum
	 * @return
	 * @throws DbexException 
	 */
	public Table readTable(ConnectionProperties connectionProperties, String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException;

	/**
	 * 
	 * @param connection
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws DbexException
	 */
	public Set<Constraint> getAllConstraints(ConnectionProperties connectionProperties, String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException;
	
	
	/**
	 * 
	 * @param connectionProperties
	 * @return
	 * @throws DbexException
	 */
	public Set<String> getAvailableSchemaNames(ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException;
	
	//public Set<Trigger> getAllTriggers(ConnectionProperties connectionProperties) throws DbexException;
	
	/**
	 * Get all the system defined functions for the underlying database.
	 * @param connectionProperties
	 * @return
	 * @throws DbexException
	 */
	public List<String> getSystemFunctions(ConnectionProperties connectionProperties) throws DbexException;
	
	/**
	 * Get all the numeric functions for the underlying database.
	 * @param connectionProperties
	 * @return
	 * @throws DbexException
	 */
	public List<String> getNumericFunctions(ConnectionProperties connectionProperties) throws DbexException;
	
	/**
	 * Get all the String functions for the underlying database.
	 * @param connectionProperties
	 * @return
	 * @throws DbexException
	 */
	public List<String> getStringFunctions(ConnectionProperties connectionProperties) throws DbexException;
	
	/**
	 * Get all the date functions for the underlying database.
	 * @param connectionProperties
	 * @return
	 * @throws DbexException
	 */
	public List<String> getTimeDateFunctions(ConnectionProperties connectionProperties) throws DbexException;
	
}
