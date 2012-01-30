/**
 * 
 */
package com.gs.dbex.integration.impl.oracle;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.integration.impl.DatabaseMetadataIntegrationImpl;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj.das
 *
 */
public class OracleDatabaseMetadataIntegrationImpl extends
		DatabaseMetadataIntegrationImpl {

	private static final Logger logger = Logger.getLogger(OracleDatabaseMetadataIntegrationImpl.class);
	private SchemaGrabber dbGrabber;
	

	public SchemaGrabber getDbGrabber() {
		return dbGrabber;
	}
	public void setDbGrabber(SchemaGrabber dbGrabber) {
		this.dbGrabber = dbGrabber;
	}


	
	public OracleDatabaseMetadataIntegrationImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Set<String> getAvailableSchemaNames(
			ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Set<String> schemaNames = new HashSet<String>();
		try {
			if(dbGrabber != null)
				schemaNames = dbGrabber.getAvailableSchemaNames(connectionProperties);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} 
		return schemaNames;
	}
	
	public Database readDatabase(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		logger.debug("START:: Reading Full database.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Database database = null;
		try {
			if(dbGrabber != null)
				database = dbGrabber.grabDatabaseBySchema(connectionProperties, connectionProperties.getDatabaseConfiguration().getSchemaName(), readDepthEnum);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} 
		logger.debug("END:: Reading Full database.");
		return database;
	}

	
	public Schema readSchema(ConnectionProperties connectionProperties,
			String schemaName, ReadDepthEnum readDepthEnum) throws DbexException {
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Schema schema = null;
		try {
			if(dbGrabber != null)
				schema = dbGrabber.grabSchema(connectionProperties, connectionProperties.getDatabaseConfiguration().getSchemaName(), readDepthEnum);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} 
		return schema;
	}

	
	public Table readTable(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException {
		logger.debug("START:: readTable()");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Table table = null;
		try {
			if(dbGrabber != null)
				table = dbGrabber.grabTable(connectionProperties, schemaName, tableName, readDepthEnum);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} 
		logger.debug("END:: readTable()");
		return table;
	}
	
	@Override
	public Set<Constraint> getAllConstraints(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException {
		
		return null;
	}


	
}
