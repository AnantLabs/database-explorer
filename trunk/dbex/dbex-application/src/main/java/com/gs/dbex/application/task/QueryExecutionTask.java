package com.gs.dbex.application.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.gs.dbex.core.Transaction;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.jdbc.ResultSetDataTable;

public class QueryExecutionTask extends
		SwingWorker<ResultSetDataTable, SqlQuery> {

	public static final String TASK_STATUS_DONE = "TASK_STATUS_DONE";
	public static final String TASK_STATUS_START = "TASK_STATUS_START";
	public static final String TASK_STATUS_ABORT = "TASK_STATUS_ABORT";
	
	public static final String PROPERTY_PROGRESS = "PROPERTY_PROGRESS";
	public static final String PROPERTY_MESSAGE = "PROPERTY_MESSAGE";
	
	private SqlQuery sqlQuery;
	private ConnectionProperties connectionProperties;
	private QueryExecutionService queryExecutionService;
	private Transaction<
		? extends Connection, 
		? extends Statement, 
		? extends PreparedStatement, 
		? extends ResultSet> currentTransaction;
	private ResultSetDataTable resultSetDataTable;

	public QueryExecutionTask(ConnectionProperties connectionProperties, SqlQuery sqlQuery) {
		this.connectionProperties = connectionProperties;
		this.sqlQuery = sqlQuery;
		this.queryExecutionService = DbexServiceBeanFactory.getBeanFactory()
				.getQueryExecutionService();
	}

	public SqlQuery getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(SqlQuery sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}

	public void setQueryExecutionService(
			QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}
	
	public ResultSetDataTable getResultSetDataTable() {
		return resultSetDataTable;
	}

	@Override
	protected void process(List<SqlQuery> chunks) {

	}

	@Override
	protected ResultSetDataTable doInBackground() throws Exception {
		firePropertyChange(PROPERTY_PROGRESS, null, TASK_STATUS_START);
		Thread.sleep(50000);
		currentTransaction = getQueryExecutionService().createTransaction(connectionProperties);
		if (null != currentTransaction) {
			resultSetDataTable = getQueryExecutionService().executeQuery(connectionProperties, sqlQuery, currentTransaction);
		} else {
			firePropertyChange(TASK_STATUS_ABORT, null, "Cannot create transaction.");
		}
		return resultSetDataTable;
	}

	@Override
	protected void done() {
		if (null != currentTransaction) {
			try {
				currentTransaction.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			currentTransaction.close();
		}
		firePropertyChange(TASK_STATUS_DONE, null, null);
	}

	public void stop() {
		if (null != currentTransaction) {
			try {
				currentTransaction.abort();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		firePropertyChange(TASK_STATUS_ABORT, null, null);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
