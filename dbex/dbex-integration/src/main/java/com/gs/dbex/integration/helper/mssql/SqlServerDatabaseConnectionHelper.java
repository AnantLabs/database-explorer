/**
 * 
 */
package com.gs.dbex.integration.helper.mssql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.text.StringUtil;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * @author Sabuj Das
 *
 */
public class SqlServerDatabaseConnectionHelper extends DatabaseConnectionHelper {

	private static final Logger logger = Logger.getLogger(SqlServerDatabaseConnectionHelper.class);
	
	public static final String PROP_USER_NAME = "user";
	public static final String PROP_PASSWORD = "password";
	public static final String PROP_INTEGRATED_SECURITY = "integratedSecurity";
	public static final String PROP_DATABASE_NAME = "databaseName";
	public static final String PROP_APPLICATION_NAME = "applicationName";
	public static final String PROP_INSTANCE_NAME = "instanceName";
	
	public static final char PROPERTY_SEPARATOR = ';';
	
	@Override
	public String formConnectionURL(ConnectionProperties connectionProperties) {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- formConnectionURL()");
		}
		StringBuffer url = new StringBuffer("jdbc:sqlserver://");
		if (null != connectionProperties.getDatabaseConfiguration().getHostName()
				&& !connectionProperties.getDatabaseConfiguration().getHostName().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getHostName()).append(":");
		}
		
		url.append(connectionProperties.getDatabaseConfiguration().getPortNumber()).append(PROPERTY_SEPARATOR);
		
		if(StringUtil.hasValidContent(connectionProperties.getDatabaseConfiguration().getSchemaName())){
			url.append(PROP_DATABASE_NAME).append("=");
			url.append(connectionProperties.getDatabaseConfiguration().getSchemaName()).append(PROPERTY_SEPARATOR);
		}
		if(StringUtil.hasValidContent(connectionProperties.getDatabaseConfiguration().getSchemaName())){
			url.append(PROP_USER_NAME).append("=");
			url.append(connectionProperties.getDatabaseConfiguration().getUserName()).append(PROPERTY_SEPARATOR);
		}
		if(StringUtil.hasValidContent(connectionProperties.getDatabaseConfiguration().getSchemaName())){
			url.append(PROP_PASSWORD).append("=");
			url.append(connectionProperties.getDatabaseConfiguration().getPassword()).append(PROPERTY_SEPARATOR);
		}
		if(StringUtil.hasValidContent(connectionProperties.getDatabaseConfiguration().getSchemaName())){
			url.append(PROP_INTEGRATED_SECURITY).append("=");
			url.append(false);
		}
		logger.info("Connection URL : [ " + url.toString() + " ]");
		if(logger.isDebugEnabled()){
			logger.debug("EXIT ::- formConnectionURL()");
		}
		return url.toString();
	}

	
	@Override
	public Connection getConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- getConnection()");
		}
		if (connectionProperties == null)
			return null;
		Connection connection = null;
		Class.forName(connectionProperties.getDatabaseConfiguration().getDriverClassName());
		String URL = formConnectionURL(connectionProperties);

		connection = DriverManager.getConnection(URL, 
				connectionProperties.getDatabaseConfiguration().getUserName(), 
				connectionProperties.getDatabaseConfiguration().getPassword());
		if(logger.isDebugEnabled()){
			logger.debug("EXIT ::- getConnection()");
		}
		return connection;
	}

	
	@Override
	public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- createDataSource()");
		}
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setServerName(connectionProperties.getDatabaseConfiguration().getHostName());
		ds.setPortNumber(connectionProperties.getDatabaseConfiguration().getPortNumber());
		ds.setDatabaseName(connectionProperties.getDatabaseConfiguration().getSidServiceName()); 
		ds.setUser(connectionProperties.getDatabaseConfiguration().getUserName());
		ds.setPassword(connectionProperties.getDatabaseConfiguration().getPassword());
		if(logger.isDebugEnabled()){
			logger.debug("EXIT ::- createDataSource()");
		}
		return ds;
	}

	
	@Override
	public boolean testConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- testConnection()");
		}
		if (connectionProperties == null)
			return false;
		boolean connected = false;
		Connection conn = null;
		try {
			conn = getConnection(connectionProperties);
			if (conn != null) {
				connected = true;
			}
		} catch (ClassNotFoundException e) {
			connected = false;
			e.printStackTrace();
		} catch (SQLException e) {
			connected = false;
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("EXIT ::- testConnection()");
		}
		return connected;
	}

}
