/**
 * 
 */
package com.gs.dbex.core.mssql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.core.metadata.enums.CatalogMetadataEnum;
import com.gs.dbex.core.metadata.enums.SqlServerMetadataConstants;
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

/**
 * @author Sabuj.das
 * 
 */
public class SqlServerDbGrabber implements CatalogGrabber {

	private static final Logger logger = Logger.getLogger(SqlServerDbGrabber.class);
	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	public SqlServerDbGrabber() {
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
		database.setModelName(connectionProperties.getConnectionName());
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
		} finally {
			JdbcUtil.close(connection);
		}
		
	}
	
	public List<Table> grabTables(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepth)throws SQLException  {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTables() for catalog: " + schemaName);
		}
		if(!StringUtil.hasValidContent(schemaName))
			return null;
		if(null == connectionProperties){
			return null;
		}
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			if(connection == null){
				return null;
			}
			
			List<Table> tables = new ArrayList<Table>();
			List<String> tableNames = new ArrayList<String>();
			connection.setCatalog(schemaName);
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SqlServerMetaQueryConstants.GET_ALL_TABLE_NAMES_BY_SCHEMA_SQL);
			statement.setString(1, schemaName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(null != resultSet){
				while(resultSet.next()){
					String tableName = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_NAME);
					if(logger.isDebugEnabled()){
						logger.debug("TABLE_NAME found: " + tableName);
					}
					String tableCatalog = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_CATALOG);
					String tableSchema = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_SCHEMA);
					String tableType = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_TYPE);
					//String tableComment = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_COMMENT);
					if(StringUtil.hasValidContent(tableName)){
						Table table = grabTable(connectionProperties, schemaName, tableName, readDepth);
						table.setTableCatalog(tableCatalog);
						table.setTableSchema(tableSchema);
						table.setModelName(tableName);
						table.setModelType(tableType);
						table.setComments("");
						tables.add(table);
						RESERVED_WORDS_UTIL.addTableName(connectionProperties.getConnectionName(), schemaName, tableName);
					}
				}
			}
			JdbcUtil.close(resultSet, false);
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit:: grabTables()");
			}
			return tables;
		} finally {
			JdbcUtil.close(connection);
		}
	}

	@Override
	public Set<String> getAvailableCatalogNames(ConnectionProperties connectionProperties) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAvailableCatalogNames()");
		}
		if(connectionProperties == null){
			return null;
		}
		Connection connection = null;
		Set<String> schemaNames = new HashSet<String>();
		
		
		try{
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			if(metaData != null){
				ResultSet rs = metaData.getCatalogs();
				while(rs.next()){
					String cat = rs.getString(CatalogMetadataEnum.TABLE_CAT.getCode());
					schemaNames.add(cat);
				}
				if(rs != null){
					rs.close();
				}
			}
			
			return schemaNames;
		} finally {
			JdbcUtil.close(connection);
		}
	}

	@Override
	public Table grabTable(ConnectionProperties connectionProperties, String catalogName,
			String tableName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTable()");
		}
		if(null == connectionProperties)
			return null;
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			if(connection == null){
				return null;
			}
			if(!StringUtil.hasValidContent(tableName))
				return null;
			
			Table table = new Table();
			table.setModelName(tableName);
			table.setSchemaName(catalogName);
			
			List<Column> columns = getColumnList(connectionProperties, table, ReadDepthEnum.DEEP);
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
			if(logger.isDebugEnabled()){
				logger.debug("Exit:: grabTable()");
			}
			return table;
		} finally {
			JdbcUtil.close(connection);
		}
	}

	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, Table table,
			ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getColumnList()");
		}
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTable()");
		}
		if(null == connectionProperties)
			return null;
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			if(connection == null){
				return null;
			}
			if(null == table)
				return null;
			List<Column> columns = new ArrayList<Column>();
			connection.setCatalog(table.getSchemaName());
			PreparedStatement statement = connection.prepareStatement(SqlServerMetaQueryConstants.GET_ALL_COLUMNS_FOR_TABLE_QUERY);
			statement.setString(1, table.getSchemaName());
			statement.setString(2, table.getModelName());
			if(logger.isDebugEnabled()){
				//logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
			}
			DatabaseMetaData metaData = connection.getMetaData();
			
			ResultSet resultSet = statement.executeQuery();
			if(null != resultSet){
				while(resultSet.next()){
					Column column = new Column(table);
					column.setTableName(table.getModelName());
					column.setSchemaName(table.getSchemaName());
					
					String columnName = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_NAME);
					column.setModelName(columnName);
					RESERVED_WORDS_UTIL.addColumnName(connectionProperties.getConnectionName(), table.getModelName(), columnName);
					
					int columnID = resultSet.getInt(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.ORDINAL_POSITION);
					column.setColumnID(columnID);
					
					String typeName = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.DATA_TYPE);
					Object charMaxLengthObj = resultSet.getObject(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.CHARACTER_MAXIMUM_LENGTH);
					Object dateTimePrecisionObj = resultSet.getObject(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.DATETIME_PRECISION);
					Object precisionObj = resultSet.getObject(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_PRECISION);
					Object scaleObj = resultSet.getObject(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_SCALE);
					
					if(null != charMaxLengthObj){
						typeName += "(" + charMaxLengthObj.toString() + ")";
					} else if(null != dateTimePrecisionObj){
						typeName += "(" + dateTimePrecisionObj.toString() + ")";
					} else if(null != precisionObj || null != scaleObj){
						column.setPrecision(Integer.valueOf(precisionObj.toString()));
						if(scaleObj == null)
							typeName += "(" + precisionObj.toString() + ")";
						else{
							typeName += "(" + precisionObj.toString() + ", " + scaleObj.toString() + ")";
							column.setSize(Integer.valueOf(scaleObj.toString()));
						}
					}  
					
					column.setTypeName(typeName);
					
					Object defaultValue = resultSet.getObject(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_DEFAULT);
					column.setDefaultValue(defaultValue);
					
					Boolean nullable = Boolean.FALSE;
					String isNull = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.IS_NULLABLE);
					if("YES".equalsIgnoreCase(isNull))
						nullable = Boolean.TRUE;
					column.setNullable(nullable);
					
					String scaleStr = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_SCALE);
					try{
						column.setSize(Integer.valueOf(scaleStr));
					} catch (Exception e) {
						// do nothing
					}
					
					String preciStr = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.COLUMNS.NUMERIC_PRECISION);
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
		} finally {
			JdbcUtil.close(connection);
		}
	}

	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, String catalogName, String tableName,
			ReadDepthEnum readDepth) throws SQLException {
		
		return null;
	}

	@Override
	public List<PrimaryKey> grabPrimaryKeys(ConnectionProperties connectionProperties,
			String catalogName, String tableName, ReadDepthEnum readDepth)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabPrimaryKeys()");
		}
		if(null == connectionProperties)
			return null;
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
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
		} finally {
			JdbcUtil.close(connection);
		}
	}

	public List<ForeignKey> grabImportedKeys(ConnectionProperties connectionProperties, String catalogName, String tableName, ReadDepthEnum readDepth) throws SQLException{
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
