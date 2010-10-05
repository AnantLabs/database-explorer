/**
 * 
 */
package com.gs.dbex.model.cfg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sabuj
 *
 */
@Entity
@Table(name="DBEX_DATABASE_CONFIGURATION")
public class DatabaseConfiguration {

	private Long configurationId;
	private String hostName;
	private Integer portNumber;
	private String driverClassName;
	private String userName;
	private String password;
	private String storageType;
	private String schemaName;
	private boolean savePassword;
	private String sidServiceName;
	
	public DatabaseConfiguration() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name="CFG_ID")
	@GeneratedValue
	public Long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
	}

	public String getSidServiceName() {
		return sidServiceName;
	}

	public void setSidServiceName(String sidServiceName) {
		this.sidServiceName = sidServiceName;
	}

	public boolean isSavePassword() {
		return savePassword;
	}

	public void setSavePassword(boolean savePassword) {
		this.savePassword = savePassword;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	
	
}
