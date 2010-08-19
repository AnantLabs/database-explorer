/**
 * 
 */
package com.gs.dbex.integration.helper.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class OracleDatabaseConnectionHelper extends DatabaseConnectionHelper {

	private static final Logger logger = Logger.getLogger(OracleDatabaseConnectionHelper.class);
	
	@Override
	public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER ::- createDataSource()");
		}
		OracleDataSource ds = new OracleDataSource();
		ds.setDriverType("thin");
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
		StringBuffer url = new StringBuffer("jdbc:oracle:thin:");
		if (null != connectionProperties.getDatabaseConfiguration().getUserName()
				&& !connectionProperties.getDatabaseConfiguration().getUserName().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getUserName()).append("/");
		}
		if (null != connectionProperties.getDatabaseConfiguration().getPassword()
				&& !connectionProperties.getDatabaseConfiguration().getPassword().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getPassword()).append("@");
		}
		if (null != connectionProperties.getDatabaseConfiguration().getHostName()
				&& !connectionProperties.getDatabaseConfiguration().getHostName().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getHostName()).append(":");
		}
		url.append(connectionProperties.getDatabaseConfiguration().getPortNumber()).append("/");
		if (null != connectionProperties.getDatabaseConfiguration().getSidServiceName()
				&& !connectionProperties.getDatabaseConfiguration().getSidServiceName().equals("")) {
			url.append(connectionProperties.getDatabaseConfiguration().getSidServiceName());
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
		Class.forName(oracle.jdbc.driver.OracleDriver.class.getCanonicalName());
		String URL = formConnectionURL(connectionProperties);

		connection = DriverManager.getConnection(URL);
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
	
	


	/*public DataSource createDataSource(	ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		OracleDataSource ds = new OracleDataSource();
		ds.setDriverType("thin");
		ds.setServerName(connectionProperties.getHostName());
		ds.setPortNumber(connectionProperties.getPortNumber());
		ds.setDatabaseName(connectionProperties.getSidServiceName()); 
		ds.setUser(connectionProperties.getUserName());
		ds.setPassword(connectionProperties.getPassword());
		
		return ds;
	}

	public String formConnectionURL(ConnectionProperties connectionProperties) {
		StringBuffer url = new StringBuffer("jdbc:oracle:thin:");
		if (null != connectionProperties.getUserName()
				&& !connectionProperties.getUserName().equals("")) {
			url.append(connectionProperties.getUserName()).append("/");
		}
		if (null != connectionProperties.getPassword()
				&& !connectionProperties.getPassword().equals("")) {
			url.append(connectionProperties.getPassword()).append("@");
		}
		if (null != connectionProperties.getHostName()
				&& !connectionProperties.getHostName().equals("")) {
			url.append(connectionProperties.getHostName()).append(":");
		}
		url.append(connectionProperties.getPortNumber()).append("/");
		if (null != connectionProperties.getSidServiceName()
				&& !connectionProperties.getSidServiceName().equals("")) {
			url.append(connectionProperties.getSidServiceName());
		}

		return url.toString();
	}

	
	public Connection getConnection(ConnectionProperties connectionProperties) throws ClassNotFoundException, SQLException {
		if (connectionProperties == null)
			return null;
		Connection connection = null;
		Class.forName(oracle.jdbc.driver.OracleDriver.class.getCanonicalName());
		String URL = formConnectionURL(connectionProperties);

		connection = DriverManager.getConnection(URL);
		return connection;
	}
	
	public boolean testConnection(
			ConnectionProperties connectionProperties) throws ClassNotFoundException, SQLException {
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
		return connected;
	}*/
	

}
