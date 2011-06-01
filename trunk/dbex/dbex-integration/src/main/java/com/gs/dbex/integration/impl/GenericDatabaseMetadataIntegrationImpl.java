/**
 * 
 */
package com.gs.dbex.integration.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;

/**
 * @author sabuj.das
 *
 */
public class GenericDatabaseMetadataIntegrationImpl extends
		DatabaseMetadataIntegrationImpl {

	private static Logger logger = Logger.getLogger(GenericDatabaseMetadataIntegrationImpl.class);
	
	@Override
	public Set<String> getAvailableSchemaNames(
			ConnectionProperties connectionProperties) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Database readDatabase(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		logger.debug("START:: Reading Full database.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		Database database = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			OracleDbGrabber dbGrabber = new OracleDbGrabber();
			database = dbGrabber.grabDatabaseBySchema(connectionProperties, "", readDepthEnum);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		logger.debug("END:: Reading Full database.");
		return database;
	}

	
	public Schema readSchema(ConnectionProperties connectionProperties,
			String schemaName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Table readTable(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<Constraint> getAllConstraints(ConnectionProperties connectionProperties,
			String schemaName, String tableName) throws DbexException {
		
		return null;
	}

}
