/**
 * 
 */
package com.gs.dbex.service;

/**
 * @author sabuj.das
 *
 */
public final class DbexServiceBeanFactory {

	private static DbexServiceBeanFactory beanFactory;
	
	private DbexServiceBeanFactory() {
		// TODO Auto-generated constructor stub
	}

	public static DbexServiceBeanFactory getBeanFactory() {
		if(beanFactory == null)
			beanFactory = new DbexServiceBeanFactory();
		return beanFactory;
	}
	
	
	private DatabaseConnectionService databaseConnectionService;
	private DatabaseMetadataService databaseMetadataService;
	private QueryExecutionService queryExecutionService;

	
	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}

	public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}

	public DatabaseConnectionService getDatabaseConnectionService() {
		return databaseConnectionService;
	}

	public void setDatabaseConnectionService(
			DatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}

	public DatabaseMetadataService getDatabaseMetadataService() {
		return databaseMetadataService;
	}

	public void setDatabaseMetadataService(
			DatabaseMetadataService databaseMetadataService) {
		this.databaseMetadataService = databaseMetadataService;
	}
	
	
}
