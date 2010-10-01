package com.gs.dbex.model.vo.cfg;

import java.io.Serializable;

import com.gs.dbex.model.cfg.DatabaseConfiguration;

/**
 * @author Sabuj Das
 *
 */
public class ConnectionPropertiesVO implements Serializable {

	private String connectionName;
	private String databaseType;
	private String connectionUrl;
	private Integer displayOrder;
	private DatabaseConfigurationVO databaseConfiguration;

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

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public DatabaseConfigurationVO getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void setDatabaseConfiguration(
			DatabaseConfigurationVO databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

}
