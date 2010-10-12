package com.gs.dbex.core.oracle;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.jdbc.driver.OracleConnection;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ColumnMetaDataEnum;
import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.enums.TableMetaDataEnum;
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.core.metadata.enums.MysqlMetadataConstants;
import com.gs.dbex.core.metadata.enums.OracleMetadataConstants;
import com.gs.dbex.core.mysql.MysqlMetaQueryConstants;
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
 * @author Sabuj Das
 *
 */
public class OracleDbGrabber implements SchemaGrabber{
	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	private static final Logger logger = Logger.getLogger(OracleDbGrabber.class);
	
	public OracleDbGrabber() {
		// TODO Auto-generated constructor stub
	}
	
	public String grabSqlKeyWords(String connectionName, Connection connection) throws SQLException{
		if(connection == null){
			return "";
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		return databaseMetaData.getSQLKeywords();
	}
	
	/**
	 * Read the complete database information. The amount of information depends
	 * on the readDepth (DEEP/ SHALLOW).
	 * 
	 * @param connection
	 * @param schemaName
	 * @param readDepth
	 * @return
	 * @throws SQLException
	 */
	public Database grabDatabaseBySchema(String connectionName, Connection connection, String schemaName, ReadDepthEnum readDepth) throws SQLException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabDatabaseBySchema() for schemaName: " + schemaName);
		}
		if(connection == null){
			return null;
		}
		Database database = new Database();
		if(!StringUtil.hasValidContent(schemaName))
			database.getSchemaList().addAll(grabSchema(connectionName, connection));
		else
			database.getSchemaList().add(grabSchema(connectionName, connection, schemaName.toUpperCase()));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabDatabaseBySchema()");
		}
		return database;
	}
	
	public List<Schema> grabSchema(String connectionName, Connection connection)
			throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabCatalog()");
		}
		if(connection == null){
			return null;
		}
		List<Schema> schemas = new ArrayList<Schema>();
		Set<String> schemaNames = getAvailableSchemaNames(connectionName, connection);
		if(null != schemaNames && schemaNames.size() > 0){
			for (String schemaName : schemaNames) {
				RESERVED_WORDS_UTIL.addSchemaName(connectionName, schemaName);
				Schema schema = grabSchema(connectionName, connection, schemaName);
				if(null != schema)
					schemas.add(schema);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabCatalog()");
		}
		return schemas;
	}

	public Schema grabSchema(String connectionName, Connection connection, String schemaName) throws SQLException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabSchema() for schema: " + schemaName);
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(schemaName))
			return null;
		RESERVED_WORDS_UTIL.addSchemaName(connectionName, schemaName);
		Schema schema = new Schema();
		schema.setSchemaName(schemaName);
		schema.setModelName(schemaName);
		schema.setTableList(grabTables(connectionName, connection, schemaName));
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabSchema()");
		}
		return schema;
	}
	
	public List<Table> grabTables(String connectionName, Connection connection, String schemaName)throws SQLException  {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabTables() for schemaName: " + schemaName);
		}
		if(connection == null){
			return null;
		}
		if(!StringUtil.hasValidContent(schemaName))
			return null;
		List<Table> tables = new ArrayList<Table>();
		PreparedStatement statement = connection.prepareStatement(OracleMetaQueryConstants.ALL_TABLES_SQL);
		statement.setString(1, schemaName.toUpperCase());
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + OracleMetaQueryConstants.ALL_TABLES_SQL + " ]");
		}
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				String tableName = resultSet.getString(OracleMetadataConstants.ALL_TABLES_SQL_META_DATA.TABLE_NAME);
				if(logger.isDebugEnabled()){
					logger.debug("TABLE_NAME found: " + tableName);
				}
				String tableCatalog = resultSet.getString(OracleMetadataConstants.ALL_TABLES_SQL_META_DATA.OWNER);
				String tableSchema = resultSet.getString(OracleMetadataConstants.ALL_TABLES_SQL_META_DATA.OWNER);
				String dropped = resultSet.getString(OracleMetadataConstants.ALL_TABLES_SQL_META_DATA.DROPPED);
				
				if(StringUtil.hasValidContent(tableName)){
					Table table = grabTable(connectionName, connection, schemaName, tableName, ReadDepthEnum.DEEP);
					table.setTableCatalog(tableCatalog);
					table.setTableSchema(tableSchema);
					table.setModelName(tableName);
					tables.add(table);
					RESERVED_WORDS_UTIL.addTableName(connectionName, schemaName, tableName);
				}
			}
		}
		JdbcUtil.close(resultSet, false);
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTables()");
		}
		return tables;
	}
	
	public ResultSet grabColumnDetails(String connectionName, String schemaName, String tableName, Connection connection) throws SQLException{
		DatabaseMetaData metaData = connection.getMetaData();
		return metaData.getColumns("", schemaName, tableName, "%");
	}
	
	public int grabColumnCount(String connectionName, String schemaName, String tableName, Connection connection) throws SQLException{
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getColumns("", schemaName, tableName, "%");
		int count = 0;
		while(rs.next()){
			count ++;
		}
		return count;
	}
	
	/**
	 * 
	 * @param connection
	 * @param schemaName
	 * @param tableName
	 * @param readDepth
	 * @return
	 */
	public Table grabTable(String connectionName, Connection connection, String schemaName, String tableName, ReadDepthEnum readDepth) throws SQLException {
		
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
		table.setSchemaName(schemaName);
		
		List<PrimaryKey> primaryKeys = grabPrimaryKeys(connectionName, connection, schemaName, tableName, readDepth);
		if(null != primaryKeys){
			table.getPrimaryKeys().addAll(primaryKeys);
		}
		
		List<Column> columns = getColumnList(connectionName, table, connection, ReadDepthEnum.DEEP);
		if(null != columns){
			table.getColumnlist().addAll(columns);
		}
		
		List<ForeignKey> importedKeys = grabImportedKeys(connectionName, connection, table, readDepth);
		if(null != importedKeys){
			table.getImportedKeys().addAll(importedKeys);
		}
		
		List<ForeignKey> exportedKeys = grabExportedKeys(connectionName, connection, table, readDepth);
		if(null != exportedKeys){
			table.getExportedKeys().addAll(exportedKeys);
		}
		
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: grabTable()");
		}
		return table;
	}
	
	public List<Column> getColumnList(String connectionName, Table table, Connection connection, ReadDepthEnum readDepth) throws SQLException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getColumnList()");
		}
		if(connection == null){
			return null;
		}
		if(null == table)
			return null;
		List<Column> columns = new ArrayList<Column>();
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(OracleMetaQueryConstants.ALL_COLUMNS_SQL);
		statement.setString(1, table.getSchemaName().toUpperCase());
		statement.setString(2, table.getModelName().toUpperCase());
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + OracleMetaQueryConstants.ALL_COLUMNS_SQL + " ]");
		}
		Column pkCol = table.getPrimaryKeyColumn();
		
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				Column column = new Column(table);
				column.setTableName(table.getModelName());
				column.setSchemaName(table.getSchemaName());
				
				String columnName = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.COLUMN_NAME);
				column.setModelName(columnName);
				column.setPrimaryKey(table.isPrimaryKeyColumn(columnName));
				RESERVED_WORDS_UTIL.addColumnName(connectionName, table.getModelName(), columnName);
				
				int columnID = resultSet.getInt(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.COLUMN_ID);
				column.setColumnID(columnID);
				
				String comments = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.COMMENTS);
				column.setComments(comments);
				
				String typeName = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.DATA_TYPE);
				column.setTypeName(typeName);
				try{
					InputStream defaultValue; // Holds the LONG data
			        StringBuffer dataBuffer = new StringBuffer();
			        int chunk;
			        defaultValue = resultSet.getAsciiStream(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.DATA_DEFAULT);
			        if(null != defaultValue){
						while ((chunk = defaultValue.read()) != -1) {
							dataBuffer.append((char) chunk);
						}
						column.setDefaultValue(dataBuffer.toString());
			        }
				}catch(Exception e){
					logger.error("Cannot read DATA_DEFAULT for column: " + columnName);
					logger.error(e.getMessage());
				}
				Boolean nullable = Boolean.FALSE;
				String isNull = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.NULLABLE);
				if("Y".equalsIgnoreCase(isNull))
					nullable = Boolean.TRUE;
				column.setNullable(nullable);
				
				
				String scaleStr = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.DATA_LENGTH);
				try{
					column.setSize(Integer.valueOf(scaleStr));
				} catch (Exception e) {
					// do nothing
				}
				
				String preciStr = resultSet.getString(OracleMetadataConstants.ALL_COLUMNS_SQL_META_DATA.DATA_PRECISION);
				try{
					column.setPrecision(Integer.valueOf(preciStr));
				} catch (Exception e) {
					// do nothing
				}
				
				/*String privilages = resultSet.getString(MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.PRIVILEGES);
				column.setPrivileges(privilages);*/
				
				columns.add(column);
			}
		}
		JdbcUtil.close(resultSet, false);
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getColumnList()");
		}
		return columns;
	}
	
	@Deprecated
	public List<Column> getColumnList(String connectionName, String schemaName, String tableName, Connection connection, ReadDepthEnum readDepth) throws SQLException{
		if(connection instanceof OracleConnection)
			((OracleConnection)connection).setRemarksReporting(true);
		List<Column> list = new ArrayList<Column>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		
		List<PrimaryKey> pkList = grabPrimaryKeys(connectionName, connection, schemaName, tableName, readDepth);
		Set<String> pkColSet = new HashSet<String>();
		for (PrimaryKey pk : pkList) {
			pkColSet.add(pk.getColumnName());
		}
		
		Set<String> fkColSet = new HashSet<String>();
		List<ForeignKey> importedKeys = grabImportedKeys(connectionName, connection, schemaName, tableName, readDepth);
		for (ForeignKey fk : importedKeys) {
			fkColSet.add(fk.getFkColumnName());
		}
		
		ResultSet colRs = databaseMetaData.getColumns("", schemaName, tableName, "%");
		ResultSetMetaData rsm = colRs.getMetaData();
		int cc = rsm.getColumnCount();
		while(colRs.next()){
			Column c = new Column(null);
			//set schema name
			c.setSchemaName(schemaName);
			//set table name
			c.setTableName(tableName);
			// set column name
			c.setModelName(colRs.getString(ColumnMetaDataEnum.COLUMN_NAME.getCode()));
			// set PK
			if(pkColSet.contains(c.getModelName())){
				c.setPrimaryKey(true);
			}
			// set FK
			if(fkColSet.contains(c.getModelName())){
				c.setForeignKey(true);
			}
			// set type name
			c.setTypeName(colRs.getString(ColumnMetaDataEnum.TYPE_NAME.getCode()));
			// set nullable
			String nulAble = colRs.getString(ColumnMetaDataEnum.IS_NULLABLE.getCode());
			if(ColumnMetaDataEnum.IS_NULLABLE_YES.getCode().equalsIgnoreCase(nulAble)){
				c.setNullable(true);
			}else{
				c.setNullable(false);
			}
			// set size
			c.setSize(colRs.getInt(ColumnMetaDataEnum.COLUMN_SIZE.getCode()));
			if(ReadDepthEnum.DEEP.equals(readDepth)){
				// set sql type
				c.setDataType(colRs.getInt(ColumnMetaDataEnum.SQL_DATA_TYPE.getCode()));
				// set column id
				c.setColumnID(colRs.getInt(ColumnMetaDataEnum.ORDINAL_POSITION.getCode()));
				// Precision
				c.setPrecision(colRs.getInt(ColumnMetaDataEnum.DECIMAL_DIGITS.getCode()));
				// set default value
				//c.setDefaultValue(colRs.getString(ColumnMetaDataEnum.COLUMN_DEF.getCode()));
				// comment
				c.setComments(colRs.getString(ColumnMetaDataEnum.REMARKS.getCode()));
			}
			list.add(c);
		}
		if(colRs != null){
			colRs.close();
		}
		return list;
	}
	
	/**
	 * 
	 * @param connection
	 * @param schemaName
	 * @param tableName
	 * @param readDepth
	 * @return
	 * @throws SQLException
	 */
	public List<PrimaryKey> grabPrimaryKeys(String connectionName, Connection connection, String schemaName, 
			String tableName, ReadDepthEnum readDepth) throws SQLException{
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabPrimaryKeys()");
		}
		if(connection == null){
			return null;
		}
		List<PrimaryKey> pkList = new ArrayList<PrimaryKey>();
		ResultSet resultSet = null;
		try{
			PreparedStatement statement = connection.prepareStatement(OracleMetaQueryConstants.GET_CONSTRAINT_COLUMNS_SQL);
			statement.setString(1, schemaName.toUpperCase());
			statement.setString(2, tableName.toUpperCase());
			statement.setString(3, "P");
			if(logger.isDebugEnabled()){
				logger.debug("Executing SQL: [ " + OracleMetaQueryConstants.GET_CONSTRAINT_COLUMNS_SQL + " ]");
			}
			resultSet = statement.executeQuery();
			if(null != resultSet){
				while(resultSet.next()){
					PrimaryKey pk = new PrimaryKey();
					String columnName = resultSet.getString(OracleMetadataConstants.GET_CONSTRAINT_COLUMNS_SQL_META_DATA.COLUMN_NAME);
					pk.setColumnName(columnName);
					pk.setTableSchem(schemaName);
					pk.setTableName(tableName);
					pkList.add(pk);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			JdbcUtil.close(resultSet, true);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: grabPrimaryKeys()");
		}
		return pkList;
	}
	
	public List<ForeignKey> grabImportedKeys(String connectionName, Connection connection, Table table, ReadDepthEnum readDepth) throws SQLException{
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getImportedKeys("", table.getSchemaName(), table.getModelName());
		return readFksFromRS(table, fkRs, true, readDepth);
	}
	
	public List<ForeignKey> grabExportedKeys(String connectionName, Connection connection, Table table, ReadDepthEnum readDepth) throws SQLException{
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getExportedKeys("", table.getSchemaName(), table.getModelName());
		return readFksFromRS(table, fkRs, false, readDepth);
	}
	
	private List<ForeignKey> readFksFromRS(Table table, ResultSet fkRs, Boolean imported, ReadDepthEnum readDepth) throws SQLException{
		List<ForeignKey> fks = new ArrayList<ForeignKey>();
		 
		while(fkRs.next()){
			ForeignKey fk = new ForeignKey();
			fk.setPkColumnName(fkRs.getString(ForeignKeyMetaDataEnum.PKCOLUMN_NAME.getCode()));
			fk.setFkColumnName(fkRs.getString(ForeignKeyMetaDataEnum.FKCOLUMN_NAME.getCode()));
			if(imported){
				table.markForeignKey(fk.getFkColumnName());
			}
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

	public Set<String> getAvailableSchemaNames(String connectionName, 
			Connection connection) throws SQLException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAvailableCatalogNames()");
		}
		Set<String> schemaNames = new HashSet<String>();
		PreparedStatement statement = connection.prepareStatement(OracleMetaQueryConstants.AVAILABLE_SCHEMA_SQL);
		if(logger.isDebugEnabled()){
			logger.debug("Executing SQL: [ " + OracleMetaQueryConstants.AVAILABLE_SCHEMA_SQL + " ]");
		}
		ResultSet resultSet = statement.executeQuery();
		if(null != resultSet){
			while(resultSet.next()){
				String schemaName = resultSet.getString(OracleMetadataConstants.AVAILABLE_SCHEMA_SQL_META_DATA.OWNER);
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
	public List<ForeignKey> grabImportedKeys(String connectionName,
			Connection connection, String schemaName, String tableName,
			ReadDepthEnum readDepth) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ForeignKey> grabExportedKeys(String connectionName,
			Connection connection, String schemaName, String tableName,
			ReadDepthEnum readDepth) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
