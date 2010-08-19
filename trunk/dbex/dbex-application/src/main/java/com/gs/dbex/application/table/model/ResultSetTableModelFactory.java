/**
 * 
 */
package com.gs.dbex.application.table.model;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author sabuj.das
 *
 */
public class ResultSetTableModelFactory {

	private Connection connection;
	//private ResultSet resultSet;

	public ResultSetTableModelFactory(Connection connection) {
		this.connection = connection;
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

	
	public ResultSetTableModel getResultSetTableModel(String query, int rowFrom, int rowTo) throws SQLException{
		if (connection == null)
			throw new IllegalStateException("Connection already closed.");

		PreparedStatement ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setInt(1, rowFrom);
		ps.setInt(2, rowTo);
		
		return new ResultSetTableModel(ps.executeQuery());
		
		/*Statement statement = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		// Run the query, creating a ResultSet
		int pageSize = rowTo - rowFrom;
		//statement.setFetchSize(pageSize);
		ResultSet resultSet = statement.executeQuery(query);
		if(rowFrom > 0)
			resultSet.absolute(rowFrom);
		resultSet.setFetchSize(pageSize);
		// Create and return a TableModel for the ResultSet
		return new ResultSetTableModel(resultSet);*/
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
}
