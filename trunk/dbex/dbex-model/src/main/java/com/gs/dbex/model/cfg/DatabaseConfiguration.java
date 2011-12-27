/**
 * 
 */
package com.gs.dbex.model.cfg;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ForeignKey;

/**
 * @author sabuj
 *
 */
@Entity
@Table(name="DBEX_DATABASE_CONFIGURATION", schema="dbex_configuration")
public class DatabaseConfiguration {

	private Long configurationId;
	private Long connectionPropId;
	private String hostName;
	private Integer portNumber;
	private String driverClassName;
	private String userName;
	private String password;
	private String storageType;
	private String schemaName;
	private boolean savePassword;
	private String sidServiceName;
	
	private Integer versionNumber;
	
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

	@Column(name="CONNECTION_PROP_ID", nullable=false)
	public Long getConnectionPropId() {
		return connectionPropId;
	}

	public void setConnectionPropId(Long connectionPropId) {
		this.connectionPropId = connectionPropId;
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

	@Version
	@Column(name="VERSION_NUMBER")
	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return
	 */
	public DatabaseConfiguration copyAll() {
		DatabaseConfiguration cfg = new DatabaseConfiguration();
		cfg.setConfigurationId(null);
		cfg.setConnectionPropId(connectionPropId);
		cfg.setDriverClassName(driverClassName);
		cfg.setHostName(hostName);
		cfg.setPassword(password);
		cfg.setPortNumber(portNumber);
		cfg.setSavePassword(savePassword);
		cfg.setSchemaName(schemaName);
		cfg.setSidServiceName(sidServiceName);
		cfg.setStorageType(storageType);
		cfg.setUserName(userName);
		cfg.setVersionNumber(0);
		return cfg;
	}

	
	
}
