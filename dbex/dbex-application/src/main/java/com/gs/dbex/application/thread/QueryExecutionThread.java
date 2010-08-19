/**
 * 
 */
package com.gs.dbex.application.thread;

import java.awt.EventQueue;

import org.omg.CORBA.portable.ApplicationException;

import com.gs.dbex.common.SqlQuery;
import com.gs.dbex.common.enums.QueryTypeEnum;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;

/**
 * @author sabuj
 * 
 */
public class QueryExecutionThread<V>
		implements Runnable {

	
	private ConnectionProperties connectionProperties;
	private SqlQuery sqlQuery;
	
	private QueryExecutionService queryExecutionService;

	private V returnedValue;


	public QueryExecutionThread(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public SqlQuery getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(SqlQuery sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public void run() {
		
		if(QueryTypeEnum.SELECT.equals(sqlQuery.getQueryType())){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					/*try {
						
					} catch (SQLException ex) {
						
					} catch (Exception ex) {
						
					}*/
				}
			});
		}
		else {
			queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
			/*try {
				int row = queryExecutionService.executeNonQuery(sqlQuery);
				
			} catch (ApplicationException ex) {
				
			} catch (Exception ex) {
				
			}*/
		}
		
		
	}


	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}

	public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}
	
	

}
