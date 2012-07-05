/**
 * 
 */
package com.gs.dbex.core.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.gs.dbex.model.cfg.ConnectionProperties;
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

	private static final Logger logger = Logger.getLogger(MysqlDbGrabber.class);
	
	public MysqlDbGrabber() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * From database meta data, the keywords are returned as a ,-separated string.
	 * 
	 * @param connectionProperties
	 */
	public String grabSqlKeyWords(ConnectionProperties connectionProperties) throws SQLException{
		if(connectionProperties == null){
			return "";
		}
		
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			return databaseMetaData.getSQLKeywords();
		} finally{
			JdbcUtil.close(connection);
		}
	}

	@Override
	public Database grabDatabaseByCatalog(ConnectionProperties connectionProperties,
			String catalogName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabDatabaseByCatalog() for Catalog: " + catalogName);
		}
		if(connectionProperties == null){
			return null;
		}
		Database database = new Database();
		
		if(!StringUtil.hasValidContent(catalogName))
			database.getSchemaList().addAll(grabCatalog(connectionProperties, readDepth));
		else
			database.getSchemaList().add(grabCatalog(connectionProperties, catalogName, readDepth));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabDatabaseByCatalog()");
		}
		return database;
	}

	@Override
	public Schema grabCatalog(ConnectionProperties connectionProperties, String catalogName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog() for catalog: " + catalogName);
		}
		if(connectionProperties == null){
			return null;
		}
		
		if(!StringUtil.hasValidContent(catalogName))
			return null;
		RESERVED_WORDS_UTIL.addSchemaName(connectionProperties.getConnectionName(), catalogName);
		Schema schema = new Schema();
		schema.setSchemaName(catalogName);
		schema.setModelName(catalogName);
		schema.setTableList(grabTables(connectionProperties, catalogName, readDepth));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schema;
	}

	@Override
	public List<Schema> grabCatalog(ConnectionProperties connectionProperties, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			
		} finally {
			JdbcUtil.close(connection);
		}
		List<Schema> schemas = new ArrayList<Schema>();
		Set<String> schemaNames = getAvailableCatalogNames(connectionProperties);
		if(null != schemaNames && schemaNames.size() > 0){
			for (String schemaName : schemaNames) {
				RESERVED_WORDS_UTIL.addSchemaName(connectionProperties.getConnectionName(), schemaName);
				Schema schema = grabCatalog(connectionProperties, schemaName, readDepth);
				if(null != schema)
					schemas.add(schema);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schemas;
	}
	
	public List<Table> grabTables(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepth)throws SQLException  {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTables() for catalog: " + schemaName);
		}
		if(connectionProperties == null){
			return null;
		}
		if(!StringUtil.hasValidContent(schemaName))
			return null;
		List<Table> tables = new ArrayList<Table>();
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			
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
						logger.debug("TABLE_NAME found: " + tableName);
					}
					String tableCatalog = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_CATALOG);
					String tableSchema = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_SCHEMA);
					Timestamp createTime = resultSet.getTimestamp(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.CREATE_TIME);
					Timestamp updateTime = resultSet.getTimestamp(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.UPDATE_TIME);
					String autoInrStr = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.AUTO_INCREMENT);
					Integer autoIncrementValue = null;
					try{
						autoIncrementValue = Integer.parseInt(autoInrStr);
					}catch (Exception e){
						// ignore
					}
					String tableType = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_TYPE);
					String tableComment = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_COMMENT);
					if(StringUtil.hasValidContent(tableName)){
						Table table = grabTable(connectionProperties, schemaName, tableName, readDepth);
						table.setTableCatalog(tableCatalog);
						table.setTableSchema(tableSchema);
						table.setModelName(tableName);
						table.setModelType(tableType);
						table.setAutoIncrementValue(autoIncrementValue);
						table.setUpdateTime(updateTime);
						table.setCreateTime(createTime);
						table.setComments(tableComment);
						tables.add(table);
						RESERVED_WORDS_UTIL.addTableName(connectionProperties.getConnectionName(), schemaName, tableName);
					}
				}
			}
			JdbcUtil.close(resultSet, false);
		} finally {
			JdbcUtil.close(connection);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTables()");
		}
		return tables;
	}

	@Override
	public Set<String> getAvailableCatalogNames(ConnectionProperties connectionProperties)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAvailableCatalogNames()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		Set<String> schemaNames = new HashSet<String>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_ALL_SCHEMA_NAMES_SQL);
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
			}
			resultSet = statement.executeQuery();
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
		} finally{
			JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Total SCHEMA_NAME(s) found: " + schemaNames.size());
		}
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		return schemaNames;
	}

	@Override
	public Table grabTable(ConnectionProperties connectionProperties, String catalogName,
			String tableName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTable()");
		}
		if(connectionProperties == null){
			return null;
		}
		if(!StringUtil.hasValidContent(tableName))
			return null;
		
		Table table = new Table();
		table.setModelName(tableName);
		table.setSchemaName(catalogName);
		table.setTableCatalog(catalogName);
		
		if(ReadDepthEnum.DEEP.equals(readDepth)){
			List<Column> columns = getColumnList(connectionProperties, table, readDepth);
			if(null != columns){
				table.getColumnlist().addAll(columns);
			}
			
			List<PrimaryKey> primaryKeys = grabPrimaryKeys(connectionProperties, catalogName, tableName, readDepth);
			if(null != primaryKeys){
				table.getPrimaryKeys().addAll(primaryKeys);
			}
			
			List<ForeignKey> importedKeys = grabImportedKeys(connectionProperties, catalogName, tableName, readDepth);
			if(null != importedKeys){
				table.getImportedKeys().addAll(importedKeys);
			}
			
			List<ForeignKey> exportedKeys = grabExportedKeys(connectionProperties, catalogName, tableName, readDepth);
			if(null != exportedKeys){
				table.getExportedKeys().addAll(exportedKeys);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTable()");
		}
		
		return table;
	}

	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, Table table, 
			ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getColumnList()");
		}
		if(connectionProperties == null){
			return null;
		}
		if(null == table){
			return null;
		}
		Connection connection = null;
		List<Column> columns = new ArrayList<Column>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(MysqlMetaQueryConstants.GET_ALL_COLUMNS_FOR_TABLE_QUERY);
			statement.setString(1, table.getTableCatalog());
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
					RESERVED_WORDS_UTIL.addColumnName(connectionProperties.getConnectionName(), table.getModelName(), columnName);
					
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
					
					String privilages = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.PRIVILEGES);
					column.setPrivileges(privilages);
					
					columns.add(column);
				}
			}
			JdbcUtil.close(resultSet, false);
		} finally {
			JdbcUtil.close(connection);
		}
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getColumnList()");
		}
		return columns;
	}

	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, String catalogName, String tableName,
			ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			
		} finally {
			JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("");
		}
		return null;
	}

	@Override
	public List<PrimaryKey> grabPrimaryKeys(ConnectionProperties connectionProperties,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabPrimaryKeys()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		List<PrimaryKey> pkList = new ArrayList<PrimaryKey>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
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
			JdbcUtil.close(pkRs, true);
		} finally {
			JdbcUtil.close(connection);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabPrimaryKeys()");
		}
		return pkList;
	}

	@Override
	public List<ForeignKey> grabImportedKeys(ConnectionProperties connectionProperties,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabImportedKeys()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet fkRs = databaseMetaData.getImportedKeys(catalogName, "", tableName);
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit:: grabImportedKeys()");
			}
			return readFksFromRS(fkRs, true, readDepth);
		} finally {
			JdbcUtil.close(connection);
		}
		
		
	}

	@Override
	public List<ForeignKey> grabExportedKeys(ConnectionProperties connectionProperties,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabExportedKeys()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet fkRs = databaseMetaData.getExportedKeys(catalogName, "", tableName);
			if(logger.isDebugEnabled()){
				logger.debug("Exit:: grabExportedKeys()");
			}
			return readFksFromRS(fkRs, false, readDepth);
		} finally {
			JdbcUtil.close(connection);
		}
		
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
