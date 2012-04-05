/**
 * 
 */
package com.gs.dbex.core.schema.grabber;

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

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ColumnMetaDataEnum;
import com.gs.dbex.common.enums.ForeignKeyMetaDataEnum;
import com.gs.dbex.common.enums.PKMetaDataEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.enums.TableMetaDataEnum;
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;

/**
 * @author sabuj.das
 *
 */
public class SchemaDBGrabberImpl implements SchemaGrabber {
	private static final Logger logger = Logger.getLogger(SchemaDBGrabberImpl.class);
	/**
	 * From database meta data, the keywords are returned as a ,-separated string.
	 * 
	 * @param connectionProperties
	 */
	@Override
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
	
	/**
	 * Read the complete database information. The amount of information depends
	 * on the readDepth (DEEP/ SHALLOW).
	 * 
	 * @param connection
	 * @param databaseName
	 * @param readDepth
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Database grabDatabaseBySchema(ConnectionProperties connectionProperties, String databaseName, ReadDepthEnum readDepth) throws SQLException{
		if(connectionProperties == null){
			return null;
		}
		String connectionName = connectionProperties.getConnectionName();
		Database db = new Database();
		db.setModelName(databaseName);
		Connection connection = null;
		try{
			List<Schema> schemaList = new ArrayList<Schema>();
			
			connection = connectionProperties.getDataSource().getConnection();
			
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if(databaseMetaData != null){
				ResultSet rs = databaseMetaData.getSchemas();
				while(rs.next()){
					String cat = rs.getString("TABLE_SCHEM");
					if(null != databaseName && !"".equals(databaseName))
						if(!databaseName.equalsIgnoreCase(cat)){
							continue;
						}
					RESERVED_WORDS_UTIL.addSchemaName(connectionName, cat);
					Schema s = new Schema();
					s.setModelName(cat);
					ResultSet ret = databaseMetaData.getTables("", s.getModelName(), "%", new String[] {"TABLE"});
					while(ret.next()){
						String tn = ret.getString(TableMetaDataEnum.TABLE_NAME.getCode());
						
						Table t = grabTable(connectionProperties, s.getModelName(), tn, readDepth);
						if(tn.startsWith("BIN$"))
							t.setDeleted(true);
						s.getTableList().add(t);
					}
					if(ret != null){
						ret.close();
					}
					
					schemaList.add(s);
					
				}
				if(rs != null){
					rs.close();
				}
				
			}
			databaseMetaData = null;
			db.setSchemaList(schemaList);
		} finally {
			JdbcUtil.close(connection);
		}
		
		return db;
	}

	@Override
	public Schema grabSchema(ConnectionProperties connectionProperties, String schemaName, ReadDepthEnum readDepth) throws SQLException{
		if(connectionProperties == null)
			return null;
		Schema schema = new Schema();
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if(databaseMetaData != null){
				ResultSet rs = databaseMetaData.getCatalogs();
				while(rs.next()){
					String cat = rs.getString("TABLE_CAT");
					if(cat.equalsIgnoreCase(schemaName)){
						schema.setModelName(schemaName);
						break;
					}
				}
			}
			return schema;
		} finally {
			JdbcUtil.close(connection);
		}
	}
	

	public int grabColumnCount(ConnectionProperties connectionProperties, 
			String schemaName, String tableName) throws SQLException{
		if(null == connectionProperties){
			return 0;
		}
		
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getColumns("", schemaName, tableName, "%");
			int count = 0;
			while(rs.next()){
				count ++;
			}
			return count;
		}
		finally {
			JdbcUtil.close(connection);
		}
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
	@Override
	public Table grabTable(ConnectionProperties connectionProperties, String schemaName, 
			String tableName, ReadDepthEnum readDepth) throws SQLException{
		if(null == connectionProperties){
			return null;
		}
		
		Connection connection = null;
		Table table = new Table();
		table.setModelName(tableName);
		try{
			connection = connectionProperties.getDataSource().getConnection();
			
			if(connection instanceof OracleConnection)
				((OracleConnection)connection).setRemarksReporting(true);
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet ret = meta.getTables("", schemaName, tableName, new String[] {"TABLE"});
			while(ret.next()){
				String tn = ret.getString(TableMetaDataEnum.TABLE_NAME.getCode());
				table.setModelName(tn);
				table.setSchemaName(schemaName);
				if(ReadDepthEnum.DEEP.equals(readDepth) || ReadDepthEnum.MEDIUM.equals(readDepth)){
					table.setPrimaryKeys(grabPrimaryKeys(connectionProperties, schemaName, tableName, readDepth));
					table.setImportedKeys(grabImportedKeys(connectionProperties, table, readDepth));
					table.setExportedKeys(grabExportedKeys(connectionProperties, table, readDepth));
					try{
						table.setColumnlist(getColumnList(connectionProperties, table, readDepth));
					}catch(Exception e){
						System.err.println("Table : " + table.getModelName() );
						e.printStackTrace();
					}
					if(ReadDepthEnum.DEEP.equals(readDepth)){
						table.setComments(ret.getString(TableMetaDataEnum.REMARKS.getCode()));
					}
				}
				
				if(tn.startsWith("BIN$"))
					table.setDeleted(true);
				else
					RESERVED_WORDS_UTIL.addTableName(connectionProperties.getConnectionName(), schemaName, tn);
				
			}
		} finally{
			JdbcUtil.close(connection);
		}
		return table;
	}
	
	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, Table table, ReadDepthEnum readDepth) throws SQLException{
		if(null == connectionProperties){
			return null;
		}
		
		Connection connection = null;
		
		try{
			connection = connectionProperties.getDataSource().getConnection();
			
			if(connection instanceof OracleConnection)
				((OracleConnection)connection).setRemarksReporting(true);
			List<Column> list = new ArrayList<Column>();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			List<PrimaryKey> pkList = table.getPrimaryKeys();
			Set<String> pkColSet = new HashSet<String>();
			for (PrimaryKey pk : pkList) {
				pkColSet.add(pk.getColumnName());
			}
			
			Set<String> fkColSet = new HashSet<String>();
			List<ForeignKey> importedKeys = table.getImportedKeys();
			for (ForeignKey fk : importedKeys) {
				fkColSet.add(fk.getFkColumnName());
			}
			ResultSet colRs = databaseMetaData.getColumns("", table.getSchemaName(), table.getModelName(), "%");
			ResultSetMetaData rsm = colRs.getMetaData();
			int cc = rsm.getColumnCount();
			while(colRs.next()){
				Column c = new Column(table);
				// set the schema name
				c.setSchemaName(table.getSchemaName());
				//set table name
				c.setTableName(table.getModelName());
				// set column name
				c.setModelName(colRs.getString(ColumnMetaDataEnum.COLUMN_NAME.getCode()));
				RESERVED_WORDS_UTIL.addColumnName(connectionProperties.getConnectionName(), table.getModelName(), c.getModelName());
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
			
		} finally {
			JdbcUtil.close(connection);
		}
	}
	
	@Override
	public List<Column> getColumnList(ConnectionProperties connectionProperties, String tableName, ReadDepthEnum readDepth) throws SQLException{
		throw new UnsupportedOperationException("Method not implemented");
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
	public List<ForeignKey> grabImportedKeys(ConnectionProperties connectionProperties, Table table,
			ReadDepthEnum readDepth)
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
			ResultSet fkRs = databaseMetaData.getImportedKeys(table.getSchemaName(), "", table.getModelName());
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit:: grabImportedKeys()");
			}
			return readFksFromRS(fkRs, true, readDepth);
		} finally {
			JdbcUtil.close(connection);
		}
		
		
	}
	
	public List<Schema> grabSchema(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepth) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter:: grabCatalog()");
		}
		if (connectionProperties == null) {
			return null;
		}
		List<Schema> schemas = new ArrayList<Schema>();
		Set<String> schemaNames = getAvailableSchemaNames(connectionProperties);
		if (null != schemaNames && schemaNames.size() > 0) {
			for (String schemaName : schemaNames) {
				RESERVED_WORDS_UTIL.addSchemaName(
						connectionProperties.getConnectionName(), schemaName);
				Schema schema = grabSchema(connectionProperties, schemaName,
						readDepth);
				if (null != schema)
					schemas.add(schema);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Exit:: grabCatalog()");
		}
		return schemas;
	}
	
	@Override
	public List<ForeignKey> grabExportedKeys(ConnectionProperties connectionProperties,
			Table table, ReadDepthEnum readDepth)
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
			ResultSet fkRs = databaseMetaData.getExportedKeys(table.getSchemaName(), "", table.getModelName());
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

	public Set<String> getAvailableSchemaNames(ConnectionProperties connectionProperties) throws SQLException {
		if(null == connectionProperties){
			return null;
		}
		Connection connection = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			Set<String> schemaNames = new HashSet<String>();
			if(null == connection)
				return schemaNames;
			
			DatabaseMetaData metaData = connection.getMetaData();
			if(metaData != null){
				ResultSet rs = metaData.getSchemas();
				while(rs.next()){
					String cat = rs.getString("TABLE_SCHEM");
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


}
