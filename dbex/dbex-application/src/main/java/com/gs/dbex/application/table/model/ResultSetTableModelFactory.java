/**
 * 
 */
package com.gs.dbex.application.table.model;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;

/**
 * @author sabuj.das
 *
 */
public class ResultSetTableModelFactory {

	private QueryExecutionService queryExecutionService;
	private Connection connection;
	//private ResultSet resultSet;

	
	public ResultSetTableModelFactory(Connection connection) {
		this.connection = connection;
		queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public ResultSetTableModel getResultSetTableModel(String query) throws SQLException{
		// If we've called close(), then we can't call this method
		if (connection == null)
			throw new IllegalStateException("Connection already closed.");

		// Create a Statement object that will be used to excecute the query.
		// The arguments specify that the returned ResultSet will be
		// scrollable, read-only, and insensitive to changes in the db.
		Statement statement = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		// Run the query, creating a ResultSet
		
		ResultSet resultSet = statement.executeQuery(query);
		// Create and return a TableModel for the ResultSet
		return new ResultSetTableModel(resultSet);
	}

	
	public ResultSetTableModel getResultSetTableModel(ConnectionProperties connectionProperties, Table table, int rowFrom, int rowTo) throws SQLException{
		if (connectionProperties == null || table == null)
			throw new IllegalStateException("Unable to get required information.");
		try {
			return new ResultSetTableModel(queryExecutionService.getLimitedResultset(connectionProperties, connection, table, rowFrom, rowTo));
		} catch (DbexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResultSetTableModel(null);
	}


	
	public ResultSetTableModel getResultSetTableModel(ResultSet resultSet, int rowCount) throws SQLException{
		return new ResultSetTableModel(resultSet, rowCount);
	}
	
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		if(connection != null){
			connection.close();
			connection = null;
		}
	}

	public TableModel getResultSetTableModel(Table databaseTable,
			int rowNumFrom, int rowNumTo) {
		
		return null;
	}

	public TableModel getResultSetTableModel(ResultSet resultSet) throws SQLException {
		return new ResultSetTableModel(resultSet);
	}
}
