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

import org.exolab.castor.jdo.drivers.MySQLQueryExpression;

import oracle.jdbc.driver.OracleConnection;

import com.gs.dbex.common.enums.ColumnMetaDataEnum;
import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.enums.TableMetaDataEnum;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.core.DbGrabber;
import com.gs.dbex.core.metadata.enums.CatalogMetadataEnum;
import com.gs.dbex.core.metadata.enums.MysqlMetadataConstants;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.text.StringUtil;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
		if(connection == null){
			return "";
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		return databaseMetaData.getSQLKeywords();
	}

	@Override
	public Database grabDatabaseByCatalog(Connection connection,
			String catalogName, ReadDepthEnum readDepth) throws SQLException {
		if(connection == null){
			return null;
		}
		Database db = new Database();
		db.setModelName("");
		if(!StringUtil.hasValidContent(catalogName))
			db.getSchemaList().add(grabCatalog(connection, catalogName));
		return db;
	}

	@Override
	public Schema grabCatalog(Connection connection, String catalogName)
			throws SQLException {
		if(connection == null){
			return null;
		}
		
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_TABLE_NAMES_SQL);
		if(StringUtil.hasValidContent(catalogName))
			statement.setString(1, catalogName);
		else 
			statement.setString(1, "%");
		statement.setString(2, "%");
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				String tableName = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_NAME);
			}
		}
		return null;
	}

	@Override
	public Set<String> getAvailableCatalogNames(Connection connection)
			throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public Table grabTable(Connection connection, String catalogName,
			String tableName, ReadDepthEnum readDepth) {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public List<Column> getColumnList(Table table, Connection connection,
			ReadDepthEnum readDepth) throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public List<Column> getColumnList(String catalogName, String tableName,
			Connection connection, ReadDepthEnum readDepth) throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public List<PrimaryKey> grabPrimaryKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public List<ForeignKey> grabImportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}

	@Override
	public List<ForeignKey> grabExportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(connection == null){
			return null;
		}
		return null;
	}
	
}
