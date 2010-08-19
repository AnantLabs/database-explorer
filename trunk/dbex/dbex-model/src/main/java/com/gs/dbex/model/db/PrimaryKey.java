/**
 * 
 */
package com.gs.dbex.model.db;

import java.io.Serializable;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 *
 */
public class PrimaryKey extends BaseDbModel implements Serializable {

	private String tableCat ;
	private String tableSchem ;
	private String tableName ;
	private String columnName ;
	private Short keySeq;
	
	public String getTableCat() {
		return tableCat;
	}
	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}
	public String getTableSchem() {
		return tableSchem;
	}
	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Short getKeySeq() {
		return keySeq;
	}
	public void setKeySeq(Short keySeq) {
		this.keySeq = keySeq;
	}
	
	
}
