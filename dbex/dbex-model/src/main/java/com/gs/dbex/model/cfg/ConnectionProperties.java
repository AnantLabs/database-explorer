/**
 * 
 */
package com.gs.dbex.model.cfg;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
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

	@Id
	private String connectionName;
	private String databaseType;
	private String connectionUrl;
	private Integer displayOrder;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private DatabaseConfiguration databaseConfiguration;

	private transient DataSource dataSource;
	private transient boolean propertySaved = true;

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
