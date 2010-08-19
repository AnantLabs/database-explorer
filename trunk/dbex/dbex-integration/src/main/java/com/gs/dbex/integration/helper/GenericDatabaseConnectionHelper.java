/**
 * 
 */
package com.gs.dbex.integration.helper;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.gs.dbex.core.GenericDataSource;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.cfg.DatabaseConfiguration;

/**
 * @author sabuj.das
 * 
 */
public class GenericDatabaseConnectionHelper extends DatabaseConnectionHelper {

	private static final Logger logger = Logger.getLogger(GenericDatabaseConnectionHelper.class);
	
	public GenericDatabaseConnectionHelper() {
		// TODO Auto-generated constructor stub
	}

	public String formConnectionURL(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(null == connectionProperties)
			return null;
		if(logger.isDebugEnabled()){
			logger.debug("ENTER:: getConnection() with ConnectionProperties [ " + connectionProperties.getConnectionName() + " ].");
		}
		Connection con = null;
		Class.forName(connectionProperties.getDatabaseConfiguration()
				.getDriverClassName());
		connectionProperties.setConnectionUrl(getConnectionUrl(connectionProperties
						.getDatabaseConfiguration()));
		con = DriverManager.getConnection(
				connectionProperties.getConnectionUrl(), connectionProperties
						.getDatabaseConfiguration().getUserName(),
				connectionProperties.getDatabaseConfiguration()
						.getPassword());
		if (con != null)
			logger.info("Connection Successful!");
		return con;
	}

	public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(null == connectionProperties)
			return null;
		if(logger.isDebugEnabled()){
			logger.debug("ENTER:: createDataSource() with ConnectionProperties [ " + connectionProperties.getConnectionName() + " ].");
		}
		DataSource ds = null;
		try {
			ds = new GenericDataSource(connectionProperties);
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		}
		if(ds != null){
			connectionProperties.setDataSource(ds);
			logger.info("DataSource created Succesfully!");
		} else {
			logger.info("DataSource creation Failed!");
		}
		return ds;
	}

	public boolean testConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		return (getConnection(connectionProperties)!=null);
	}

	private String getConnectionUrl(DatabaseConfiguration databaseConfiguration) {
		String url = "jdbc:sqlserver://";
		String selectMethod = "cursor";

		return url + databaseConfiguration.getHostName() + ":"
				+ databaseConfiguration.getPortNumber() + ";databaseName="
				+ databaseConfiguration.getSchemaName() + ";selectMethod="
				+ selectMethod + ";";
	}

}
