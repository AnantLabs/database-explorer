/**
 * 
 */
package com.gs.dbex.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class GenericDataSource implements DataSource {

	private String driverClassName;
	private ConnectionProperties connectionProperties;
	
	public GenericDataSource(ConnectionProperties connectionProperties) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.driverClassName = connectionProperties.getDatabaseConfiguration().getDriverClassName();
		this.connectionProperties = connectionProperties;
		DriverManager.registerDriver((Driver) Class.forName(driverClassName).newInstance());
	}
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(getClass().getCanonicalName());
	}


	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	
	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionProperties.getConnectionUrl());
	}

	
	public Connection getConnection(String username, String password)
			throws SQLException {
		return DriverManager.getConnection(connectionProperties.getConnectionUrl(), username, password);
	}


	public String getDriverClassName() {
		return driverClassName;
	}


	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}


	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}


	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

}
