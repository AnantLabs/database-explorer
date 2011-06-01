/**
 * 
 */
package com.gs.dbex.integration.impl;

import java.util.Set;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.DatabaseMetadataIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;

/**
 * @author sabuj.das
 *
 */
public abstract class DatabaseMetadataIntegrationImpl implements
		DatabaseMetadataIntegration {

	public Set<Constraint> getAllConstraints(
			ConnectionProperties connectionProperties, String schemaName,
			String tableName) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
