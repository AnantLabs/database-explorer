/**
 * 
 */
package com.gs.dbex.model.cfg;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.sql.DataSource;

import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * 
 */
@Entity
@Table(name="DBEX_CONNECTION_PROPERTIES")
public class ConnectionProperties implements Serializable,
		Comparable<ConnectionProperties> {

	/**
	 * serialVersionUID = 2717753646686919478L;
	 */
	private static final long serialVersionUID = 2717753646686919478L;

	private Long connectionPropId;
	private Long userId;
	private String connectionName;
	private String databaseType;
	private String connectionUrl;
	private Integer displayOrder;
	private DatabaseConfiguration databaseConfiguration;

	private transient DataSource dataSource;
	private transient boolean propertySaved = true;

	
	@Id
	@Column(name="CONNECTION_PROP_ID")
	@GeneratedValue
	public Long getConnectionPropId() {
		return connectionPropId;
	}

	public void setConnectionPropId(Long connectionPropId) {
		this.connectionPropId = connectionPropId;
	}
	
	@Column(name="USER_ID", nullable=false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ConnectionProperties() {
		this("UN-NAMED");
	}

	public ConnectionProperties(String connName) {
		this.connectionName = connName;
		databaseConfiguration = new DatabaseConfiguration();
	}

	public int compareTo(ConnectionProperties o) {
		return connectionName.compareTo(o.getConnectionName());
	}

	protected void finalize() throws Throwable {
		super.finalize();
		dataSource = null;
	}

	@Transient
	public boolean isPropertySaved() {
		return propertySaved;
	}

	public void setPropertySaved(boolean propertySaved) {
		this.propertySaved = propertySaved;
	}

	/**
	 * @return the connectionName
	 */
	public String getConnectionName() {
		return connectionName;
	}

	/**
	 * @param connectionName
	 *            the connectionName to set
	 */
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	/**
	 * @return the connectionUrl
	 */
	public String getConnectionUrl() {
		return connectionUrl;
	}

	/**
	 * @param connectionUrl
	 *            the connectionUrl to set
	 */
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	/**
	 * @return the databaseType
	 */
	public String getDatabaseType() {
		return databaseType;
	}

	/**
	 * @param databaseType
	 *            the databaseType to set
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * @return the dataSource
	 */
	@Transient
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void setDatabaseConfiguration(
			DatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	@Override
	public String toString() {
		return (StringUtil.hasValidContent(getConnectionName())) ? getConnectionName()
				: "UN-NAMED";
	}
}
