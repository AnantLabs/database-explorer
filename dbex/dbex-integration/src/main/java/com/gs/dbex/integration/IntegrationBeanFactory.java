/**
 * 
 */
package com.gs.dbex.integration;

import com.gs.dbex.common.enums.DatabaseTypeEnum;

/**
 * @author sabuj.das
 *
 */
public final class IntegrationBeanFactory {

	private static IntegrationBeanFactory instance;
	
	private DatabaseMetadataIntegration oracleDatabaseMetadataIntegration;
	private DatabaseMetadataIntegration mysqlDatabaseMetadataIntegration;
	private DatabaseConnectionIntegration oracleDatabaseConnectionIntegration;
	private DatabaseConnectionIntegration mysqlDatabaseConnectionIntegration;
	private DatabaseConnectionIntegration genericDatabaseConnectionIntegration;
	private XmlReaderIntegration castorXmlReaderIntegration;
	
	private IntegrationBeanFactory() {
		
	}
	
	public static IntegrationBeanFactory getBeanFactory(){
		if(instance == null)
			instance = new IntegrationBeanFactory();
		return instance;
	}
	
	public DatabaseMetadataIntegration getDatabaseMetadataIntegration(DatabaseTypeEnum databaseTypeEnum){
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleDatabaseMetadataIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return getMysqlDatabaseMetadataIntegration();
		}
		return null;
	}
	
	public DatabaseConnectionIntegration getDatabaseConnectionIntegration(DatabaseTypeEnum databaseTypeEnum){
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleDatabaseConnectionIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return getMysqlDatabaseConnectionIntegration();
		} else if(DatabaseTypeEnum.MSSQL_2005.equals(databaseTypeEnum)){
			return null;
		} else if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
			return getGenericDatabaseConnectionIntegration();
		}
		return null;
	}

	public DatabaseMetadataIntegration getOracleDatabaseMetadataIntegration() {
		return oracleDatabaseMetadataIntegration;
	}

	public void setOracleDatabaseMetadataIntegration(
			DatabaseMetadataIntegration oracleDatabaseMetadataIntegration) {
		this.oracleDatabaseMetadataIntegration = oracleDatabaseMetadataIntegration;
	}

	public DatabaseMetadataIntegration getMysqlDatabaseMetadataIntegration() {
		return mysqlDatabaseMetadataIntegration;
	}

	public void setMysqlDatabaseMetadataIntegration(
			DatabaseMetadataIntegration mysqlDatabaseMetadataIntegration) {
		this.mysqlDatabaseMetadataIntegration = mysqlDatabaseMetadataIntegration;
	}

	public DatabaseConnectionIntegration getOracleDatabaseConnectionIntegration() {
		return oracleDatabaseConnectionIntegration;
	}

	public void setOracleDatabaseConnectionIntegration(
			DatabaseConnectionIntegration oracleDatabaseConnectionIntegration) {
		this.oracleDatabaseConnectionIntegration = oracleDatabaseConnectionIntegration;
	}

	public DatabaseConnectionIntegration getMysqlDatabaseConnectionIntegration() {
		return mysqlDatabaseConnectionIntegration;
	}

	public void setMysqlDatabaseConnectionIntegration(
			DatabaseConnectionIntegration mysqlDatabaseConnectionIntegration) {
		this.mysqlDatabaseConnectionIntegration = mysqlDatabaseConnectionIntegration;
	}

	public XmlReaderIntegration getCastorXmlReaderIntegration() {
		return castorXmlReaderIntegration;
	}

	public void setCastorXmlReaderIntegration(
			XmlReaderIntegration castorXmlReaderIntegration) {
		this.castorXmlReaderIntegration = castorXmlReaderIntegration;
	}

	public DatabaseConnectionIntegration getGenericDatabaseConnectionIntegration() {
		return genericDatabaseConnectionIntegration;
	}

	public void setGenericDatabaseConnectionIntegration(
			DatabaseConnectionIntegration genericDatabaseConnectionIntegration) {
		this.genericDatabaseConnectionIntegration = genericDatabaseConnectionIntegration;
	}

	
	
}
