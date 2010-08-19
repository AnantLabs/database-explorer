/**
 * 
 */
package com.gs.dbex.model.cfg;

/**
 * @author sabuj.das
 *
 */
public class ConnectionConfiguration {

	private String connectionName;
	private String databaseType;
	private ConnectionUrl connectionUrl;
	private DatabaseConfiguration databaseConfiguration;
	
	public ConnectionConfiguration() {
		connectionUrl = new ConnectionUrl();
		databaseConfiguration = new DatabaseConfiguration();
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public ConnectionUrl getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(ConnectionUrl connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}
	
	
}
