/**
 * 
 */
package com.gs.dbex.model.dependency;

import java.io.Serializable;
import java.util.List;

import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 *
 */
public class TableDependency implements Serializable{

	private Table currentTable;
	
	private List<ImportedTableRelation> importedRelations;
	private List<ExportedTableRelation> exportedRelations;
	
	public TableDependency() {
		// TODO Auto-generated constructor stub
	}

	public Table getCurrentTable() {
		return currentTable;
	}

	public void setCurrentTable(Table currentTable) {
		this.currentTable = currentTable;
	}

	public List<ImportedTableRelation> getImportedRelations() {
		return importedRelations;
	}

	public List<ExportedTableRelation> getExportedRelations() {
		return exportedRelations;
	}

	public void setImportedRelations(List<ImportedTableRelation> importedRelations) {
		this.importedRelations = importedRelations;
	}

	public void setExportedRelations(List<ExportedTableRelation> exportedRelations) {
		this.exportedRelations = exportedRelations;
	}


	
}
