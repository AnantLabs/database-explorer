/**
 * 
 */
package com.gs.dbex.core.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.core.metadata.enums.MysqlMetadataConstants;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.text.StringUtil;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author sabuj.das
 *
 */
public class MysqlDbGrabber implements CatalogGrabber {

	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	private static final Logger logger = Logger.getLogger(MysqlDbGrabber.class);
	
	public MysqlDbGrabber() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String grabSqlKeyWords(Connection connection) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabSqlKeyWords()");
		}
		if(connection == null){
			return "";
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		return databaseMetaData.getSQLKeywords();
	}

	@Override
	public Database grabDatabaseByCatalog(Connection connection,
			String catalogName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabDatabaseByCatalog() for Catalog: " + catalogName);
		}
		if(connection == null){
			return null;
		}
		Database database = new Database();
		if(!StringUtil.hasValidContent(catalogName))
			database.getSchemaList().addAll(grabCatalog(connection));
		else
			database.getSchemaList().add(grabCatalog(connection, catalogName));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabDatabaseByCatalog()");
		}
		return database;
	}

	@Override
	public Schema grabCatalog(Connection connection, String catalogName)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog() for catalog: " + catalogName);
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(catalogName))
			return null;
		Schema schema = new Schema();
		schema.setSchemaName(catalogName);
		schema.setModelName(catalogName);
		schema.setTableList(grabTables(connection, catalogName));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schema;
	}

	@Override
	public List<Schema> grabCatalog(Connection connection)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog()");
		}
		if(connection == null){
			return null;
		}
		List<Schema> schemas = new ArrayList<Schema>();
		Set<String> schemaNames = getAvailableCatalogNames(connection);
		if(null != schemaNames && schemaNames.size() > 0){
			for (String schemaName : schemaNames) {
				Schema schema = grabCatalog(connection, schemaName);
				if(null != schema)
					schemas.add(schema);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schemas;
	}
	
	public List<Table> grabTables(Connection connection, String schemaName)throws SQLException  {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTables() for catalog: " + schemaName);
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(schemaName))
			return null;
		List<Table> tables = new ArrayList<Table>();
		List<String> tableNames = new ArrayList<String>();
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_ALL_TABLE_NAMES_BY_SCHEMA_SQL);
		statement.setString(1, schemaName);
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
		}
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				String tableName = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_NAME);
				if(logger.isDebugEnabled()){
					logger.debug("SCHEMA_NAME found: " + tableName);
				}
				if(StringUtil.hasValidContent(tableName))
					tableNames.add(tableName);
			}
		}
		JdbcUtil.close(resultSet, false);
		if(tableNames.size() > 0){
			for (String tableName : tableNames) {
				Table table = grabTable(connection, schemaName, tableName, ReadDepthEnum.DEEP);
				if(null != table)
					tables.add(table);
			}
			
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTables()");
		}
		return tables;
	}

	@Override
	public Set<String> getAvailableCatalogNames(Connection connection)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAvailableCatalogNames()");
		}
		if(connection == null){
			return null;
		}
		Set<String> schemaNames = new HashSet<String>();
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_ALL_SCHEMA_NAMES_SQL);
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
		}
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				String schemaName = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.SCHEMATA.SCHEMA_NAME);
				if(logger.isDebugEnabled()){
					logger.debug("SCHEMA_NAME found: " + schemaName);
				}
				if(StringUtil.hasValidContent(schemaName))
					schemaNames.add(schemaName);
			}
		}
		JdbcUtil.close(resultSet, false);
		if(logger.isDebugEnabled()){
			logger.debug("Total SCHEMA_NAME(s) found: " + schemaNames.size());
		}
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		return schemaNames;
	}

	@Override
	public Table grabTable(Connection connection, String catalogName,
			String tableName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTable()");
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(tableName))
			return null;
		
		Table table = new Table();
		table.setModelName(tableName);
		table.setSchemaName(catalogName);
		
		List<Column> columns = getColumnList(table, connection, ReadDepthEnum.DEEP);
		if(null != columns){
			table.getColumnlist().addAll(columns);
		}
		
		List<PrimaryKey> primaryKeys = grabPrimaryKeys(connection, catalogName, tableName, readDepth);
		if(null != primaryKeys){
			table.getPrimaryKeys().addAll(primaryKeys);
		}
		
		List<ForeignKey> importedKeys = grabImportedKeys(connection, catalogName, tableName, readDepth);
		if(null != importedKeys){
			table.getImportedKeys().addAll(importedKeys);
		}
		
		List<ForeignKey> exportedKeys = grabExportedKeys(connection, catalogName, tableName, readDepth);
		if(null != exportedKeys){
			table.getExportedKeys().addAll(exportedKeys);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTable()");
		}
		return table;
	}

	@Override
	public List<Column> getColumnList(Table table, Connection connection,
			ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getColumnList()");
		}
		if(connection == null){
			return null;
		}
		if(null == table)
			return null;
		List<Column> columns = new ArrayList<Column>();
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_ALL_COLUMNS_FOR_TABLE_QUERY);
		statement.setString(1, table.getSchemaName());
		statement.setString(2, table.getModelName());
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
		}
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				Column column = new Column(table);
				column.setTableName(table.getModelName());
				column.setSchemaName(table.getSchemaName());
				
				String columnName = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_NAME);
				column.setModelName(columnName);
				
				int columnID = resultSet.getInt(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.ORDINAL_POSITION);
				column.setColumnID(columnID);
				
				String comments = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_COMMENT);
				column.setComments(comments);
				
				String typeName = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_TYPE);
				column.setTypeName(typeName);
				
				Object defaultValue = resultSet.getObject(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_DEFAULT);
				column.setDefaultValue(defaultValue);
				
				Boolean nullable = Boolean.FALSE;
				String isNull = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.IS_NULLABLE);
				if("YES".equalsIgnoreCase(isNull))
					nullable = Boolean.TRUE;
				column.setNullable(nullable);
				
				String columnKey = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_KEY);
				if("PRI".equalsIgnoreCase(columnKey))
					column.setPrimaryKey(true);
				else if("MUL".equalsIgnoreCase(columnKey))
					column.setForeignKey(true);
				
				String scaleStr = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_SCALE);
				try{
					column.setSize(Integer.valueOf(scaleStr));
				} catch (Exception e) {
					// do nothing
				}
				
				String preciStr = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_PRECISION);
				try{
					column.setPrecision(Integer.valueOf(preciStr));
				} catch (Exception e) {
					// do nothing
				}
				
				columns.add(column);
			}
		}
		JdbcUtil.close(resultSet, false);
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getColumnList()");
		}
		return columns;
	}

	@Override
	public List<Column> getColumnList(String catalogName, String tableName,
			Connection connection, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		if(connection == null){
			return null;
		}
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		return null;
	}

	@Override
	public List<PrimaryKey> grabPrimaryKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabPrimaryKeys()");
		}
		if(connection == null){
			return null;
		}
		List<PrimaryKey> pkList = new ArrayList<PrimaryKey>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet pkRs = databaseMetaData.getPrimaryKeys(catalogName, "", tableName);
		while(pkRs.next()){
			PrimaryKey pk = new PrimaryKey();
			pk.setColumnName(pkRs.getString(PKMetaDataEnum.COLUMN_NAME.getCode()));
			
			if(ReadDepthEnum.DEEP.equals(readDepth)){
				pk.setTableCat(pkRs.getString(PKMetaDataEnum.TABLE_CAT.getCode()));
				pk.setTableSchem(pkRs.getString(PKMetaDataEnum.TABLE_SCHEM.getCode()));
				pk.setTableName(tableName);
				pk.setModelName(pkRs.getString(PKMetaDataEnum.PK_NAME.getCode()));
				//pk.setDeleted(pkRs.getBoolean(PKMetaDataEnum.))
				pk.setKeySeq(pkRs.getShort(PKMetaDataEnum.KEY_SEQ.getCode()));
				//pk.setComments(pkRs.getString(PKMetaDataEnum.comments))
			}
			
			pkList.add(pk);
		}
		if(pkRs != null){
			pkRs.close();
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabPrimaryKeys()");
		}
		return pkList;
	}

	@Override
	public List<ForeignKey> grabImportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabImportedKeys()");
		}
		if(connection == null){
			return null;
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getImportedKeys(catalogName, "", tableName);
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabImportedKeys()");
		}
		return readFksFromRS(fkRs, true, readDepth);
	}

	@Override
	public List<ForeignKey> grabExportedKeys(Connection connection,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabExportedKeys()");
		}
		if(connection == null){
			return null;
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getExportedKeys(catalogName, "", tableName);
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabExportedKeys()");
		}
		return readFksFromRS(fkRs, false, readDepth);
	}
	
	private List<ForeignKey> readFksFromRS(ResultSet fkRs, Boolean imported, ReadDepthEnum readDepth) throws SQLException{
		List<ForeignKey> fks = new ArrayList<ForeignKey>();
		
		while(fkRs.next()){
			ForeignKey fk = new ForeignKey();
			fk.setPkColumnName(fkRs.getString(ForeignKeyMetaDataEnum.PKCOLUMN_NAME.getCode()));
			fk.setFkColumnName(fkRs.getString(ForeignKeyMetaDataEnum.FKCOLUMN_NAME.getCode()));
			if(ReadDepthEnum.DEEP.equals(readDepth)){
				fk.setPkTableCat(fkRs.getString(ForeignKeyMetaDataEnum.PKTABLE_CAT.getCode()));
				fk.setPkTableSchem(fkRs.getString(ForeignKeyMetaDataEnum.PKTABLE_SCHEM.getCode()));
				fk.setPkTableName(fkRs.getString(ForeignKeyMetaDataEnum.PKTABLE_NAME.getCode()));
				fk.setFkTableCat(fkRs.getString(ForeignKeyMetaDataEnum.FKTABLE_CAT.getCode()));
				fk.setFkTableSchem(fkRs.getString(ForeignKeyMetaDataEnum.FKTABLE_SCHEM.getCode()));
				fk.setFkTableName(fkRs.getString(ForeignKeyMetaDataEnum.FKTABLE_NAME.getCode()));
				fk.setKeySeq(fkRs.getShort(ForeignKeyMetaDataEnum.KEY_SEQ.getCode()));
				fk.setUpdateRule(fkRs.getShort(ForeignKeyMetaDataEnum.UPDATE_RULE.getCode()));
				fk.setDeleteRule(fkRs.getShort(ForeignKeyMetaDataEnum.DELETE_RULE.getCode()));
				fk.setPkName(fkRs.getString(ForeignKeyMetaDataEnum.PK_NAME.getCode()));
				fk.setFkName(fkRs.getString(ForeignKeyMetaDataEnum.FK_NAME.getCode()));
				fk.setDeferrability(fkRs.getShort(ForeignKeyMetaDataEnum.DEFERRABILITY.getCode()));
			}
			fk.setImportedKey(imported);
			fks.add(fk);
		}
		if(fkRs != null){
			fkRs.close();
		}
		return fks;
	}

}
