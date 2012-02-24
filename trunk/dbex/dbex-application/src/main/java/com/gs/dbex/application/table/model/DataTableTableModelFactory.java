package com.gs.dbex.application.table.model;

import javax.swing.table.TableModel;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;

/**
 * @author Sabuj Das
 *
 */
public class DataTableTableModelFactory {

	private QueryExecutionService queryExecutionService;

	public DataTableTableModelFactory() {
		queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
	}

	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}

	public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}
	
	public DataTableTableModel getResultSetTableModel(ConnectionProperties connectionProperties, 
			Table table, int rowFrom, int rowTo) {
		if (connectionProperties == null || table == null)
			throw new IllegalStateException("Unable to get required information.");
		try {
			return new DataTableTableModel(
					queryExecutionService.getLimitedDataTable(connectionProperties, table, rowFrom, rowTo));
		} catch (DbexException e) {
			
		}
		return new DataTableTableModel(null);
	}

	public DataTableTableModel getResultSetTableModel(
			ConnectionProperties connectionProperties, Table databaseTable,
			String filterSubQuery) {
		if (connectionProperties == null || databaseTable == null)
			throw new IllegalStateException("Unable to get required information.");
		try {
			return new DataTableTableModel(
					queryExecutionService.getFilteredDataTable(connectionProperties, databaseTable, filterSubQuery));
		} catch (DbexException e) {
			
		}
		return new DataTableTableModel(null);
	}

	/**
	 * @param connectionProperties
	 * @param queryString
	 * @param rowNumFrom
	 * @param rowNumTo
	 * @return
	 */
	public TableModel getResultSetTableModel(
			ConnectionProperties connectionProperties, String queryString,
			int rowNumFrom, int rowNumTo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param connectionProperties
	 * @param queryString
	 * @param filterSubQuery
	 * @return
	 */
	public DataTableTableModel getResultSetTableModel(
			ConnectionProperties connectionProperties, String queryString,
			String filterSubQuery) {
		// TODO Auto-generated method stub
		return null;
	}
}
