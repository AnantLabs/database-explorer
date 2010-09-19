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
import com.gs.dbex.common.enums.TableMetaDataEnum;
import com.gs.dbex.core.CatalogGrabber;
import com.gs.dbex.core.metadata.enums.CatalogMetadataEnum;
import com.gs.dbex.core.metadata.enums.SqlServerMetadataConstants;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
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
	
	@Override
	public String grabSqlKeyWords(String connectionName, Connection connection) throws SQLException {
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
	public Database grabDatabaseByCatalog(String connectionName, Connection connection,
			String catalogName, ReadDepthEnum readDepth) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabDatabaseByCatalog() for Catalog: " + catalogName);
		}
		if(connection == null){
			return null;
		}
		Database database = new Database();
		RESERVED_WORDS_UTIL.addSchemaName(connectionName, catalogName);
		if(!StringUtil.hasValidContent(catalogName))
			database.getSchemaList().addAll(grabCatalog(connectionName, connection));
		else
			database.getSchemaList().add(grabCatalog(connectionName, connection, catalogName));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabDatabaseByCatalog()");
		}
		return database;
	}

	@Override
	public Schema grabCatalog(String connectionName, Connection connection, String catalogName)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog() for catalog: " + catalogName);
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(catalogName))
			return null;
		RESERVED_WORDS_UTIL.addSchemaName(connectionName, catalogName);
		Schema schema = new Schema();
		schema.setSchemaName(catalogName);
		schema.setModelName(catalogName);
		schema.setTableList(grabTables(connectionName, connection, catalogName));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schema;
	}

	@Override
	public List<Schema> grabCatalog(String connectionName, Connection connection)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog()");
		}
		if(connection == null){
			return null;
		}
		List<Schema> schemas = new ArrayList<Schema>();
		Set<String> schemaNames = getAvailableCatalogNames(connectionName, connection);
		
		if(null != schemaNames && schemaNames.size() > 0){
			for (String schemaName : schemaNames) {
				RESERVED_WORDS_UTIL.addSchemaName(connectionName, schemaName);
				Schema schema = grabCatalog(connectionName, connection, schemaName);
				if(null != schema)
					schemas.add(schema);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schemas;
	}
	
	public List<Table> grabTables(String connectionName, Connection connection, String schemaName)throws SQLException  {
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
		connection.setCatalog(schemaName);
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(SqlServerMetaQueryConstants.GET_ALL_TABLE_NAMES_BY_SCHEMA_SQL);
		statement.setString(1, schemaName);
		if(logger.isDebugEnabled()){
			//logger.debug("Executing SQL: [ " + statement.getPreparedSql() + " ]");
		}
		ResultSet resultSet = statement.executeQuery();//metaData.getTables(schemaName, "dbo", "%", new String[] {"TABLE"});
		if(null != resultSet){
			while(resultSet.next()){
				String tableName = resultSet.getString(SqlServerMetadataConstants.INFORMATION_SCHEMA.TABLES.TABLE_NAME);//TableMetaDataEnum.TABLE_NAME.getCode());
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
				Table table = grabTable(connectionName, connection, schemaName, tableName, ReadDepthEnum.DEEP);
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
	public Set<String> getAvailableCatalogNames(String connectionName, Connection connection)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAvailableCatalogNames()");
		}
		if(connection == null){
			return null;
		}
		Set<String> schemaNames = new HashSet<String>();
		DatabaseMetaData metaData = connection.getMetaData();
		
		ResultSet resultSet = metaData.getCatalogs();
		if(null != resultSet){
			while(resultSet.next()){
				String schemaName = resultSet.getString(CatalogMetadataEnum.TABLE_CAT.getCode());
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
			logger.debug("Exit:: getAvailableCatalogNames()");
		}
		return schemaNames;
	}

	@Override
	public Table grabTable(String connectionName, Connection connection, String catalogName,
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
		
		List<Column> columns = getColumnList(connectionName, table, connection, ReadDepthEnum.DEEP);
		if(null != columns){
			table.getColumnlist().addAll(columns);
		}
		
		List<PrimaryKey> primaryKeys = grabPrimaryKeys(connectionName, connection, catalogName, tableName, readDepth);
		if(null != primaryKeys){
			table.getPrimaryKeys().addAll(primaryKeys);
		}
		
		List<ForeignKey> importedKeys = grabImportedKeys(connectionName, connection, catalogName, tableName, readDepth);
		if(null != importedKeys){
			table.getImportedKeys().addAll(importedKeys);
		}
		
		List<ForeignKey> exportedKeys = grabExportedKeys(connectionName, connection, catalogName, tableName, readDepth);
		if(null != exportedKeys){
			table.getExportedKeys().addAll(exportedKeys);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTable()");
		}
		return table;
	}

	@Override
	public List<Column> getColumnList(String connectionName, Table table, Connection connection,
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
	}

	@Override
	public List<Column> getColumnList(String connectionName, String catalogName, String tableName,
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
	public List<PrimaryKey> grabPrimaryKeys(String connectionName, Connection connection,
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
	public List<ForeignKey> grabImportedKeys(String connectionName, Connection connection,
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
	public List<ForeignKey> grabExportedKeys(String connectionName, Connection connection,
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
