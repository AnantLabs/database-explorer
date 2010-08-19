/**
 * 
 */
package com.gs.dbex.integration.helper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 * 
 */
public abstract class DatabaseConnectionHelper {

	public abstract String formConnectionURL(
			ConnectionProperties connectionProperties);

	public abstract Connection getConnection(
			ConnectionProperties connectionProperties) throws ClassNotFoundException, SQLException;

	public abstract DataSource createDataSource(
			ConnectionProperties connectionProperties) throws ClassNotFoundException, SQLException;
	
	public abstract boolean testConnection(
			ConnectionProperties connectionProperties) throws ClassNotFoundException, SQLException;
}
