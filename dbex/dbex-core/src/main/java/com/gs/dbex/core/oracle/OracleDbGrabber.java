package com.gs.dbex.core.oracle;

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
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 *
 */
public class OracleDbGrabber implements SchemaGrabber{
	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	public OracleDbGrabber() {
		// TODO Auto-generated constructor stub
	}
	
	public String grabSqlKeyWords(Connection connection) throws SQLException{
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
	 * @param databaseName
	 * @param readDepth
	 * @return
	 * @throws SQLException
	 */
	public Database grabDatabaseBySchema(Connection connection, String databaseName, ReadDepthEnum readDepth) throws SQLException{
		if(connection == null){
			return null;
		}
		Database db = new Database();
		db.setModelName(databaseName);
		List<Schema> schemaList = new ArrayList<Schema>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		if(databaseMetaData != null){
			ResultSet rs = databaseMetaData.getSchemas();
			while(rs.next()){
				String cat = rs.getString("TABLE_SCHEM");
				if(null != databaseName && !"".equals(databaseName))
					if(!databaseName.equalsIgnoreCase(cat)){
						continue;
					}
				RESERVED_WORDS_UTIL.addSchemaName(cat);
				Schema s = new Schema();
				s.setModelName(cat);
				ResultSet ret = databaseMetaData.getTables("", s.getModelName(), "%", new String[] {"TABLE"});
				while(ret.next()){
					String tn = ret.getString(TableMetaDataEnum.TABLE_NAME.getCode());
					
					Table t = grabTable(connection, s.getModelName(), tn, readDepth);
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
		return db;
	}

	public Schema grabSchema(Connection connection, String schemaName) throws SQLException{
		if(connection == null)
			return null;
		Schema schema = new Schema();
		
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
	}
	
	public ResultSet grabColumnDetails(String schemaName, String tableName, Connection connection) throws SQLException{
		DatabaseMetaData metaData = connection.getMetaData();
		return metaData.getColumns("", schemaName, tableName, "%");
	}
	
	public int grabColumnCount(String schemaName, String tableName, Connection connection) throws SQLException{
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
	public Table grabTable(Connection connection, String schemaName, String tableName, ReadDepthEnum readDepth){
		if(connection instanceof OracleConnection)
			((OracleConnection)connection).setRemarksReporting(true);
		Table table = new Table();
		table.setModelName(tableName);
		try{
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet ret = meta.getTables("", schemaName, tableName, new String[] {"TABLE"});
			while(ret.next()){
				String tn = ret.getString(TableMetaDataEnum.TABLE_NAME.getCode());
				table.setModelName(tn);
				table.setSchemaName(schemaName);
				if(ReadDepthEnum.DEEP.equals(readDepth) || ReadDepthEnum.MEDIUM.equals(readDepth)){
					table.setPrimaryKeys(grabPrimaryKeys(connection, schemaName, tableName, readDepth));
					table.setImportedKeys(grabImportedKeys(connection, schemaName, tableName, readDepth));
					table.setExportedKeys(grabExportedKeys(connection, schemaName, tableName, readDepth));
					try{
						table.setColumnlist(getColumnList(table, connection, readDepth));
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
					RESERVED_WORDS_UTIL.addTableName(schemaName, tn);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return table;
	}
	
	public List<Column> getColumnList(Table table, Connection connection, ReadDepthEnum readDepth) throws SQLException{
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
			Column c = new Column();
			// set the schema name
			c.setSchemaName(table.getSchemaName());
			//set table name
			c.setTableName(table.getModelName());
			// set column name
			c.setModelName(colRs.getString(ColumnMetaDataEnum.COLUMN_NAME.getCode()));
			RESERVED_WORDS_UTIL.addColumnName(table.getModelName(), c.getModelName());
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
	
	public List<Column> getColumnList(String schemaName, String tableName, Connection connection, ReadDepthEnum readDepth) throws SQLException{
		if(connection instanceof OracleConnection)
			((OracleConnection)connection).setRemarksReporting(true);
		List<Column> list = new ArrayList<Column>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		
		List<PrimaryKey> pkList = grabPrimaryKeys(connection, schemaName, tableName, readDepth);
		Set<String> pkColSet = new HashSet<String>();
		for (PrimaryKey pk : pkList) {
			pkColSet.add(pk.getColumnName());
		}
		
		Set<String> fkColSet = new HashSet<String>();
		List<ForeignKey> importedKeys = grabImportedKeys(connection, schemaName, tableName, readDepth);
		for (ForeignKey fk : importedKeys) {
			fkColSet.add(fk.getFkColumnName());
		}
		
		ResultSet colRs = databaseMetaData.getColumns("", schemaName, tableName, "%");
		ResultSetMetaData rsm = colRs.getMetaData();
		int cc = rsm.getColumnCount();
		while(colRs.next()){
			Column c = new Column();
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
	public List<PrimaryKey> grabPrimaryKeys(Connection connection, String schemaName, 
			String tableName, ReadDepthEnum readDepth) throws SQLException{
		List<PrimaryKey> pkList = new ArrayList<PrimaryKey>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet pkRs = databaseMetaData.getPrimaryKeys("", schemaName, tableName);
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
		return pkList;
	}
	
	public List<ForeignKey> grabImportedKeys(Connection connection, String schemaName, String tableName, ReadDepthEnum readDepth) throws SQLException{
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getImportedKeys("", schemaName, tableName);
		return readFksFromRS(fkRs, true, readDepth);
	}
	
	public List<ForeignKey> grabExportedKeys(Connection connection, String schemaName, String tableName, ReadDepthEnum readDepth) throws SQLException{
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet fkRs = databaseMetaData.getExportedKeys("", schemaName, tableName);
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

	public Set<String> getAvailableSchemaNames(
			Connection connection) throws SQLException {
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
	}
	
	
	
}
