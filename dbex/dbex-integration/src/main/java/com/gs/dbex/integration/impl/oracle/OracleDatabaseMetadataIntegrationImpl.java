/**
 * 
 */
package com.gs.dbex.integration.impl.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.integration.impl.DatabaseMetadataIntegrationImpl;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;

/**
 * @author Sabuj.das
 *
 */
public class OracleDatabaseMetadataIntegrationImpl extends
		DatabaseMetadataIntegrationImpl {

	private static final Logger logger = Logger.getLogger(OracleDatabaseMetadataIntegrationImpl.class);
	private SchemaGrabber dbGrabber;
	
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
			if(dbGrabber != null)
				database = dbGrabber.grabDatabaseBySchema(connectionProperties.getConnectionName(), 
						connection, connectionProperties.getDatabaseConfiguration().getSchemaName(), readDepthEnum);
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


	public SchemaGrabber getDbGrabber() {
		return dbGrabber;
	}


	public void setDbGrabber(SchemaGrabber dbGrabber) {
		this.dbGrabber = dbGrabber;
	}


	@Override
	public ResultSet getAllConstraints(Connection connection,
			String schemaName, String tableName) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

}
