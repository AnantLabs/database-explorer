package com.gs.dbex.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public Transaction(Connection connection) {
		this.connection = connection;
	}

	public final Connection begin() throws SQLException {
		return this.connection;
	}

	public final void commit() throws SQLException {
		if (this.resultSet != null) {
			this.resultSet.close();
		}

		if (this.statement != null) {
			this.statement.close();
		}

		if (!(this.connection.getAutoCommit()))
			this.connection.commit();
	}

	public final void abort() {
		try {
			this.connection.rollback();
		} catch (Exception e) {
		}
	}

	public final void close() {
		if (this.resultSet != null) {
			try {
				this.resultSet.close();
			} catch (SQLException e) {
			} finally {
				this.resultSet = null;
			}
		}

		if (this.statement != null) {
			try {
				this.statement.close();
			} catch (SQLException e) {
			} finally {
				this.statement = null;
			}
		}

		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.close();
			} catch (SQLException e) {
			} finally {
				this.preparedStatement = null;
			}
		}

		if (this.connection == null)
			return;
		try {
			this.connection.close();
		} catch (SQLException e) {
		} finally {
			this.connection = null;
		}
	}

	public final ResultSet executeQuery(String query) throws SQLException {
		this.statement = this.connection.createStatement();
		this.resultSet = this.statement.executeQuery(query);
		return this.resultSet;
	}

	public final PreparedStatement prepareStatement(String query)
			throws SQLException {
		this.preparedStatement = this.connection.prepareStatement(query);
		return this.preparedStatement;
	}

	public final void execute() throws SQLException {
		if (this.preparedStatement != null)
			this.preparedStatement.execute();
	}
}