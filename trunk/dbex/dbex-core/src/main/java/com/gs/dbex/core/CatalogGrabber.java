/**
 * 
 */
package com.gs.dbex.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 * 
 */
public interface CatalogGrabber extends DbGrabber {

	public Database grabDatabaseByCatalog(String connectionName, Connection connection,
			String databaseName, ReadDepthEnum readDepth) throws SQLException;

	public List<Schema> grabCatalog(String connectionName, Connection connection) throws SQLException;

	public Schema grabCatalog(String connectionName, Connection connection, String catalogName)
			throws SQLException;

	public Set<String> getAvailableCatalogNames(String connectionName, Connection connection)
			throws SQLException;

	public Table grabTable(String connectionName, Connection connection, String catalogName,
			String tableName, ReadDepthEnum readDepth) throws SQLException;

	public List<Table> grabTables(String connectionName, Connection connection, String schemaName)
			throws SQLException;

	public List<Column> getColumnList(String connectionName, Table table, Connection connection,
			ReadDepthEnum readDepth) throws SQLException;

	public List<Column> getColumnList(String connectionName, String catalogName, String tableName,
			Connection connection, ReadDepthEnum readDepth) throws SQLException;

	public List<PrimaryKey> grabPrimaryKeys(String connectionName, Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException;

	public List<ForeignKey> grabImportedKeys(String connectionName, Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException;

	public List<ForeignKey> grabExportedKeys(String connectionName, Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException;
}
