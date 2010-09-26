
package com.gs.dbex.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;
import com.gs.utils.text.StringUtil;


/**
 * @author sabuj.das
 *
 */
public class Table extends BaseDbModel implements Serializable {

	private String tableCatalog;
	private String tableSchema;
	private Integer autoIncrementValue;
	
	
	private List<PrimaryKey> primaryKeys;
	private List<Column> columnlist;
	private List<ForeignKey> importedKeys;
	private List<ForeignKey> exportedKeys;
	
	
	public Table() {
		primaryKeys = new ArrayList<PrimaryKey>();
		columnlist = new ArrayList<Column>();
		importedKeys = new ArrayList<ForeignKey>();
		exportedKeys = new ArrayList<ForeignKey>();
	}
	/**
	 * Returns the primary key column.
	 * @return
	 */
	public Column getPrimaryKeyColumn(){
		for (Column column : columnlist) {
			if(column.getPrimaryKey()){
				return column;
			}
		}
		return null;
	}
	
	/**
	 * Returns true if the table has any primary key.
	 * @return
	 */
	public boolean hasPrimaryKey(){
		for (Column column : columnlist) {
			if(column.getPrimaryKey()){
				return true;
			}
		}
		return false;
	}

	public Integer getAutoIncrementValue() {
		return autoIncrementValue;
	}
	public void setAutoIncrementValue(Integer autoIncrementValue) {
		this.autoIncrementValue = autoIncrementValue;
	}
	public List<Column> getColumnlist() {
		return columnlist;
	}

	public void setColumnlist(List<Column> columnlist) {
		this.columnlist = columnlist;
	}
	
	@Override
	public String toString() {
		return super.getModelName();
	}

	public String getTableCatalog() {
		return tableCatalog;
	}
	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public List<PrimaryKey> getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	public List<ForeignKey> getImportedKeys() {
		return importedKeys;
	}
	public void setImportedKeys(List<ForeignKey> importedKeys) {
		this.importedKeys = importedKeys;
	}
	public List<ForeignKey> getExportedKeys() {
		return exportedKeys;
	}
	public void setExportedKeys(List<ForeignKey> exportedKeys) {
		this.exportedKeys = exportedKeys;
	}

	public String getColumnNames(char separator){
		StringBuffer buffer = new StringBuffer();
		List<Column> cList = getColumnlist();
		if(cList != null){
			for (int i = 0; i < cList.size(); i++) {
				Column c = cList.get(i);
				buffer.append(c.getModelName());
				if(i != cList.size()-1){
					buffer.append(" ").append(separator);
				}
			}
		}
		
		return buffer.toString();
	}
	
	public boolean hasExportedTableForColumn(Column column){
		if(null != column && null != getExportedKeys() && getExportedKeys().size() > 0){
			for (ForeignKey expKey : getExportedKeys()) {
				if(null != expKey){
					if(column.getModelName().equalsIgnoreCase(expKey.getModelName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasImportedTableForColumn(Column column){
		if(null != column && null != getImportedKeys() && getImportedKeys().size() > 0){
			for (ForeignKey impKey : getImportedKeys()) {
				if(null != impKey){
					if(column.getModelName().equalsIgnoreCase(impKey.getModelName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isPrimaryKeyColumn(String columnName){
		if(!StringUtil.hasValidContent(columnName)){
			return false;
		}
		if(null != getPrimaryKeys() && getPrimaryKeys().size() > 0){
			for(PrimaryKey pk : getPrimaryKeys()){
				if(columnName.equalsIgnoreCase(pk.getColumnName()))
					return true;
			}
		}
		return false;
	}
	
	public void markForeignKey(String ...columnNames){
		if(null == columnNames || columnNames.length <= 0){
			return ;
		}
		List<String> columnNameList = Arrays.asList(columnNames);
		if(null != getColumnlist() && getColumnlist().size() > 0){
			for(Column column : getColumnlist()){
				if(columnNameList.contains(column.getModelName())){
					column.setForeignKey(true);
				}
			}
		}
	}
}
