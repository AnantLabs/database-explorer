/**
 * 
 */
package com.gs.dbex.integration;


import com.gs.dbex.common.enums.DatabaseStorageTypeEnum;
import com.gs.dbex.common.enums.DatabaseTypeEnum;

/**
 * @author sabuj.das
 *
 */
public final class IntegrationBeanFactory {

	private static IntegrationBeanFactory instance;
	
	private DatabaseMetadataIntegration oracleDatabaseMetadataIntegration;
	private DatabaseMetadataIntegration mysqlDatabaseMetadataIntegration;
	private DatabaseMetadataIntegration sqlServerDatabaseMetadataIntegration;
	private DatabaseMetadataIntegration catalogMetadataIntegration;
	private DatabaseMetadataIntegration schemaMetadataIntegration;
	private DatabaseMetadataIntegration genericMetadataIntegration;
	private DatabaseConnectionIntegration oracleDatabaseConnectionIntegration;
	private DatabaseConnectionIntegration mysqlDatabaseConnectionIntegration;
	private DatabaseConnectionIntegration genericDatabaseConnectionIntegration;
	private DatabaseConnectionIntegration sqlServerDatabaseConnectionIntegration;
	private XmlReaderIntegration castorXmlReaderIntegration;
	private QueryExecutionIntegration mysqlQueryExecutionIntegration;
	private QueryExecutionIntegration sqlServerQueryExecutionIntegration;
	private QueryExecutionIntegration oracleQueryExecutionIntegration;
	private DependencyIntegration oracleDependencyIntegration;
	
	private IntegrationBeanFactory() {
		
	}
	
	public static IntegrationBeanFactory getBeanFactory(){
		if(instance == null)
			instance = new IntegrationBeanFactory();
		return instance;
	}
	
	public DatabaseMetadataIntegration getDatabaseMetadataIntegration(DatabaseTypeEnum databaseTypeEnum, DatabaseStorageTypeEnum storageTypeEnum){
		if(null == databaseTypeEnum || null == storageTypeEnum)
			return null;
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleDatabaseMetadataIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return getMysqlDatabaseMetadataIntegration();
		} else if(DatabaseTypeEnum.MSSQL_2005.equals(databaseTypeEnum)){
			return getSqlServerDatabaseMetadataIntegration();
		} else if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
			if(DatabaseStorageTypeEnum.CATALOG_STORAGE.equals(storageTypeEnum)){
				return getCatalogMetadataIntegration();
			} else if(DatabaseStorageTypeEnum.SCHEMA_STORAGE.equals(storageTypeEnum)){
				return getSchemaMetadataIntegration();
			}
		}
		return getGenericMetadataIntegration();
	}
	
	public DatabaseConnectionIntegration getDatabaseConnectionIntegration(DatabaseTypeEnum databaseTypeEnum){
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleDatabaseConnectionIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return getMysqlDatabaseConnectionIntegration();
		} else if(DatabaseTypeEnum.MSSQL_2005.equals(databaseTypeEnum)){
			return getSqlServerDatabaseConnectionIntegration();
		} else if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
			return getGenericDatabaseConnectionIntegration();
		}
		return null;
	}
	
	public QueryExecutionIntegration getQueryExecutionIntegration(DatabaseTypeEnum databaseTypeEnum){
		if(null == databaseTypeEnum)
			return null;
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleQueryExecutionIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return getMysqlQueryExecutionIntegration();
		}else if(DatabaseTypeEnum.MSSQL_2005.equals(databaseTypeEnum)){
			return getSqlServerQueryExecutionIntegration();
		} else if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
			
		}
		return null;
	}

	public DependencyIntegration getDependencyIntegration(DatabaseTypeEnum databaseTypeEnum) {
		if(null == databaseTypeEnum)
			return null;
		if(DatabaseTypeEnum.ORACLE.equals(databaseTypeEnum)){
			return getOracleDependencyIntegration();
		} else if(DatabaseTypeEnum.MYSQL.equals(databaseTypeEnum)){
			return null;
		}else if(DatabaseTypeEnum.MSSQL_2005.equals(databaseTypeEnum)){
			return null;
		} else if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
			
		}
		return null;
	}
	
	
	@Deprecated
	public DependencyIntegration getOracleDependencyIntegration() {
		return oracleDependencyIntegration;
	}

	@Deprecated
	public void setOracleDependencyIntegration(
			DependencyIntegration oracleDependencyIntegration) {
		this.oracleDependencyIntegration = oracleDependencyIntegration;
	}

	@Deprecated
	public QueryExecutionIntegration getOracleQueryExecutionIntegration() {
		return oracleQueryExecutionIntegration;
	}

	@Deprecated
	public void setOracleQueryExecutionIntegration(
			QueryExecutionIntegration oracleQueryExecutionIntegration) {
		this.oracleQueryExecutionIntegration = oracleQueryExecutionIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getOracleDatabaseMetadataIntegration() {
		return oracleDatabaseMetadataIntegration;
	}

	@Deprecated
	public void setOracleDatabaseMetadataIntegration(
			DatabaseMetadataIntegration oracleDatabaseMetadataIntegration) {
		this.oracleDatabaseMetadataIntegration = oracleDatabaseMetadataIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getMysqlDatabaseMetadataIntegration() {
		return mysqlDatabaseMetadataIntegration;
	}

	@Deprecated
	public void setMysqlDatabaseMetadataIntegration(
			DatabaseMetadataIntegration mysqlDatabaseMetadataIntegration) {
		this.mysqlDatabaseMetadataIntegration = mysqlDatabaseMetadataIntegration;
	}

	@Deprecated
	public DatabaseConnectionIntegration getOracleDatabaseConnectionIntegration() {
		return oracleDatabaseConnectionIntegration;
	}

	@Deprecated
	public void setOracleDatabaseConnectionIntegration(
			DatabaseConnectionIntegration oracleDatabaseConnectionIntegration) {
		this.oracleDatabaseConnectionIntegration = oracleDatabaseConnectionIntegration;
	}

	@Deprecated
	public DatabaseConnectionIntegration getMysqlDatabaseConnectionIntegration() {
		return mysqlDatabaseConnectionIntegration;
	}

	@Deprecated
	public void setMysqlDatabaseConnectionIntegration(
			DatabaseConnectionIntegration mysqlDatabaseConnectionIntegration) {
		this.mysqlDatabaseConnectionIntegration = mysqlDatabaseConnectionIntegration;
	}

	@Deprecated
	public XmlReaderIntegration getCastorXmlReaderIntegration() {
		return castorXmlReaderIntegration;
	}

	@Deprecated
	public void setCastorXmlReaderIntegration(
			XmlReaderIntegration castorXmlReaderIntegration) {
		this.castorXmlReaderIntegration = castorXmlReaderIntegration;
	}

	@Deprecated
	public DatabaseConnectionIntegration getGenericDatabaseConnectionIntegration() {
		return genericDatabaseConnectionIntegration;
	}

	@Deprecated
	public void setGenericDatabaseConnectionIntegration(
			DatabaseConnectionIntegration genericDatabaseConnectionIntegration) {
		this.genericDatabaseConnectionIntegration = genericDatabaseConnectionIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getCatalogMetadataIntegration() {
		return catalogMetadataIntegration;
	}

	@Deprecated
	public void setCatalogMetadataIntegration(
			DatabaseMetadataIntegration catalogMetadataIntegration) {
		this.catalogMetadataIntegration = catalogMetadataIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getSchemaMetadataIntegration() {
		return schemaMetadataIntegration;
	}

	@Deprecated
	public void setSchemaMetadataIntegration(
			DatabaseMetadataIntegration schemaMetadataIntegration) {
		this.schemaMetadataIntegration = schemaMetadataIntegration;
	}

	@Deprecated
	public QueryExecutionIntegration getMysqlQueryExecutionIntegration() {
		return mysqlQueryExecutionIntegration;
	}

	@Deprecated
	public void setMysqlQueryExecutionIntegration(
			QueryExecutionIntegration mysqlQueryExecutionIntegration) {
		this.mysqlQueryExecutionIntegration = mysqlQueryExecutionIntegration;
	}

	@Deprecated
	public DatabaseConnectionIntegration getSqlServerDatabaseConnectionIntegration() {
		return sqlServerDatabaseConnectionIntegration;
	}

	@Deprecated
	public void setSqlServerDatabaseConnectionIntegration(
			DatabaseConnectionIntegration sqlServerDatabaseConnectionIntegration) {
		this.sqlServerDatabaseConnectionIntegration = sqlServerDatabaseConnectionIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getSqlServerDatabaseMetadataIntegration() {
		return sqlServerDatabaseMetadataIntegration;
	}

	@Deprecated
	public void setSqlServerDatabaseMetadataIntegration(
			DatabaseMetadataIntegration sqlServerDatabaseMetadataIntegration) {
		this.sqlServerDatabaseMetadataIntegration = sqlServerDatabaseMetadataIntegration;
	}

	@Deprecated
	public QueryExecutionIntegration getSqlServerQueryExecutionIntegration() {
		return sqlServerQueryExecutionIntegration;
	}

	@Deprecated
	public void setSqlServerQueryExecutionIntegration(
			QueryExecutionIntegration sqlServerQueryExecutionIntegration) {
		this.sqlServerQueryExecutionIntegration = sqlServerQueryExecutionIntegration;
	}

	@Deprecated
	public DatabaseMetadataIntegration getGenericMetadataIntegration() {
		return genericMetadataIntegration;
	}

	@Deprecated
	public void setGenericMetadataIntegration(
			DatabaseMetadataIntegration genericMetadataIntegration) {
		this.genericMetadataIntegration = genericMetadataIntegration;
	}

	
	
}
