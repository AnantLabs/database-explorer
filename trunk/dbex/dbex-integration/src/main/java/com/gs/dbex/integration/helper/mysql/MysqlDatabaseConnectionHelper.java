/**
 * 
 */
package com.gs.dbex.integration.helper.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.text.StringUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author sabuj.das
 *
 */
public class MysqlDatabaseConnectionHelper extends DatabaseConnectionHelper {

private static final Logger logger = Logger.getLogger(MysqlDatabaseConnectionHelper.class);
	
	@Override
	public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- createDataSource()");
		}
		MysqlDataSource ds = new MysqlDataSource();
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
	public String formConnectionURL(ConnectionProperties connectionProperties) {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- formConnectionURL()");
		}
		StringBuffer url = new StringBuffer("jdbc:mysql://");
		if (null != connectionProperties.getDatabaseConfiguration().getHostName()
				&& !connectionProperties.getDatabaseConfiguration().getHostName().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getHostName()).append(":");
		}
		
		url.append(connectionProperties.getDatabaseConfiguration().getPortNumber()).append("/");
		
		if(StringUtil.hasValidContent(connectionProperties.getDatabaseConfiguration().getSchemaName())){
			url.append(connectionProperties.getDatabaseConfiguration().getSchemaName());
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
	
	/*public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setServerName(connectionProperties.getHostName());
		mysqlDataSource.setPortNumber(connectionProperties.getPortNumber());
		mysqlDataSource.setDatabaseName(connectionProperties.getSidServiceName()); 
		mysqlDataSource.setUser(connectionProperties.getUserName());
		mysqlDataSource.setPassword(connectionProperties.getPassword());
		return mysqlDataSource;
	}

	public String formConnectionURL(ConnectionProperties connectionProperties) {
		StringBuffer url = new StringBuffer("jdbc:mysql://");
		
		if (null != connectionProperties.getHostName()
				&& !connectionProperties.getHostName().equals("")) {
			url.append(connectionProperties.getHostName()).append(":");
		}
		url.append(connectionProperties.getPortNumber()).append("/");
		if (null != connectionProperties.getSchemaName()
				&& !connectionProperties.getSchemaName().equals("")) {
			url.append(connectionProperties.getSchemaName());
		}

		return url.toString();
	}

	public Connection getConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if (connectionProperties == null)
			return null;
		Connection connection = null;
		Class.forName(connectionProperties.getDriverClassName());
		String URL = formConnectionURL(connectionProperties);

		connection = DriverManager.getConnection(URL, connectionProperties.getUserName(), connectionProperties.getPassword());
		return connection;
	}

	public boolean testConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if (connectionProperties == null)
			return false;
		boolean connected = false;
		Connection conn = null;
		try {
			conn = getConnection(connectionProperties);
			if (conn != null) {
				connected = true;
			}
		
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return connected;
	}*/
	

}
