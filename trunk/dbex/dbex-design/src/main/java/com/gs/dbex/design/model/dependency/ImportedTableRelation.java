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
public class ImportedTableRelation extends TableRelation implements
		Serializable {

	private ForeignKey importedKey;
	private Table importedTable;
	
	public ImportedTableRelation() {
		// TODO Auto-generated constructor stub
	}

	public ForeignKey getImportedKey() {
		return importedKey;
	}

	public Table getImportedTable() {
		return importedTable;
	}

	public void setImportedKey(ForeignKey importedKey) {
		this.importedKey = importedKey;
	}

	public void setImportedTable(Table importedTable) {
		this.importedTable = importedTable;
	}
	
	
}
