/**
 * 
 */
package com.gs.dbex.integration.impl;

import java.util.Set;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.integration.DatabaseMetadataIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 *
 */
public class CatalogMetadataIntegrationImpl implements
		DatabaseMetadataIntegration {

	private CatalogGrabber dbGrabber;
	
	@Override
	public Set<String> getAvailableSchemaNames(
			ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Database readDatabase(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Schema readSchema(ConnectionProperties connectionProperties,
			String schemaName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Table readTable(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}


	public CatalogGrabber getDbGrabber() {
		return dbGrabber;
	}


	public void setDbGrabber(CatalogGrabber dbGrabber) {
		this.dbGrabber = dbGrabber;
	}


	@Override
	public Set<Constraint> getAllConstraints(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

}
