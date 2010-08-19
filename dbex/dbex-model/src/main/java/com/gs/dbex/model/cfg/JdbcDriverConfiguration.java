/**
 * 
 */
package com.gs.dbex.model.cfg;

/**
 * @author sabuj.das
 *
 */
public class JdbcDriverConfiguration {

	private String databaseName;
	private ConnectionConfiguration connectionConfiguration;
	private JdbcServices jdbcServices;
	private DriverFile driverFile;
	
	public JdbcDriverConfiguration() {
		connectionConfiguration = new ConnectionConfiguration();
		jdbcServices = new JdbcServices();
		driverFile = new DriverFile();
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public ConnectionConfiguration getConnectionConfiguration() {
		return connectionConfiguration;
	}

	public void setConnectionConfiguration(
			ConnectionConfiguration connectionConfiguration) {
		this.connectionConfiguration = connectionConfiguration;
	}

	public JdbcServices getJdbcServices() {
		return jdbcServices;
	}

	public void setJdbcServices(JdbcServices jdbcServices) {
		this.jdbcServices = jdbcServices;
	}

	public DriverFile getDriverFile() {
		return driverFile;
	}

	public void setDriverFile(DriverFile driverFile) {
		this.driverFile = driverFile;
	}
	
	@Override
	public String toString() {
		return getDatabaseName();
	}
}
