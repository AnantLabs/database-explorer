/**
 * 
 */
package com.gs.dbex.design.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sabuj Das
 *
 */
public class RelationTableShape implements Serializable{

	private TableDbShape currentTableDbShape;
	
	private List<TableDbShape> importedTableDbShapes;
	private List<TableDbShape> exportedTableDbShapes;
	
	public RelationTableShape() {
		// TODO Auto-generated constructor stub
	}

	public TableDbShape getCurrentTableDbShape() {
		return currentTableDbShape;
	}

	public void setCurrentTableDbShape(TableDbShape currentTableDbShape) {
		this.currentTableDbShape = currentTableDbShape;
	}

	public List<TableDbShape> getImportedTableDbShapes() {
		return importedTableDbShapes;
	}

	public void setImportedTableDbShapes(List<TableDbShape> importedTableDbShapes) {
		this.importedTableDbShapes = importedTableDbShapes;
	}

	public List<TableDbShape> getExportedTableDbShapes() {
		return exportedTableDbShapes;
	}

	public void setExportedTableDbShapes(List<TableDbShape> exportedTableDbShapes) {
		this.exportedTableDbShapes = exportedTableDbShapes;
	}
	
	
	
}
