
package com.gs.dbex.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;


/**
 * @author sabuj.das
 *
 */
public class Table extends BaseDbModel implements Serializable {

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
}
