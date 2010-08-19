/**
 * 
 */
package com.gs.dbex.integration.helper.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.gs.dbex.integration.helper.DatabaseConnectionHelper;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author sabuj.das
 *
 */
public class MysqlDatabaseConnectionHelper extends DatabaseConnectionHelper {

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
	@Override
	public DataSource createDataSource(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String formConnectionURL(ConnectionProperties connectionProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testConnection(ConnectionProperties connectionProperties)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
