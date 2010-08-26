/**
 * 
 */
package com.gs.dbex.design.model.dependency;

import java.io.Serializable;

import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 *
 */
public class ExportedTableRelation extends TableRelation implements
		Serializable {

	private ForeignKey exportedKey;
	private Table exportedTable;
	
	public ExportedTableRelation() {
		// TODO Auto-generated constructor stub
	}

	public ForeignKey getExportedKey() {
		return exportedKey;
	}

	public Table getExportedTable() {
		return exportedTable;
	}

	public void setExportedKey(ForeignKey exportedKey) {
		this.exportedKey = exportedKey;
	}

	public void setExportedTable(Table exportedTable) {
		this.exportedTable = exportedTable;
	}
	
	
}
