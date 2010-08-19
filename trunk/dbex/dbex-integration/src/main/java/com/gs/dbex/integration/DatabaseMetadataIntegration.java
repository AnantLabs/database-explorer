/**
 * 
 */
package com.gs.dbex.integration;

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
	 */
	public Schema readSchema(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepthEnum);
	
	/**
	 * Read the complete table.
	 * @param connectionProperties
	 * @param readDepthEnum
	 * @return
	 */
	public Table readTable(ConnectionProperties connectionProperties, String schemaName, String tableName, ReadDepthEnum readDepthEnum);
	
}
