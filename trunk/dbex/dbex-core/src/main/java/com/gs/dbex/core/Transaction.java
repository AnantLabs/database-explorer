package com.gs.dbex.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction<C extends Connection, S extends Statement, P extends PreparedStatement, R extends ResultSet> {
	private C connection;
	private S statement;
	private P preparedStatement;
	private R resultSet;

	public Transaction(C connection) {
		this.connection = connection;
	}

	public final C begin() throws SQLException {
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

	public final void abort() throws SQLException {
		this.connection.rollback();
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

	@SuppressWarnings("unchecked")
	public final ResultSet executeQuery(String query) throws SQLException {
		this.statement = (S) this.connection.createStatement();
		this.resultSet = (R) this.statement.executeQuery(query);
		return this.resultSet;
	}

	@SuppressWarnings("unchecked")
	public final PreparedStatement prepareStatement(String query)
			throws SQLException {
		this.preparedStatement = (P) this.connection.prepareStatement(query);
		return this.preparedStatement;
	}

	public final void execute() throws SQLException {
		if (this.preparedStatement != null)
			this.preparedStatement.execute();
	}

	public final int executeUpdate(String query) throws SQLException {
		if (this.preparedStatement != null)
			return this.preparedStatement.executeUpdate();
		return 0;
	}
}