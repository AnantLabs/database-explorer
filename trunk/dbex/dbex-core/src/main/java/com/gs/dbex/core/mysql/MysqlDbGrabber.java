/**
 * 
 */
package com.gs.dbex.core.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.jdbc.driver.OracleConnection;

import com.gs.dbex.common.enums.ColumnMetaDataEnum;
import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.enums.TableMetaDataEnum;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.core.DbGrabber;
import com.gs.dbex.core.metadata.enums.CatalogMetadataEnum;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
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
public class MysqlDbGrabber implements CatalogGrabber {

	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	public MysqlDbGrabber() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String grabSqlKeyWords(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Database grabDatabaseByCatalog(Connection connection,
			String databaseName, ReadDepthEnum readDepth) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schema grabCatalog(Connection connection, String catalogName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAvailableCatalogNames(Connection connection)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table grabTable(Connection connection, String catalogName,
			String tableName, ReadDepthEnum readDepth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> getColumnList(Table table, Connection connection,
			ReadDepthEnum readDepth) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> getColumnList(String catalogName, String tableName,
			Connection connection, ReadDepthEnum readDepth) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PrimaryKey> grabPrimaryKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ForeignKey> grabImportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ForeignKey> grabExportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
