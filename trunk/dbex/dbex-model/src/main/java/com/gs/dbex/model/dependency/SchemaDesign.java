/**
 * 
 */
package com.gs.dbex.model.dependency;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sabuj.das
 *
 */
public class SchemaDesign implements Serializable {

	private List<TableDependency> tableDependencies;
	
	public SchemaDesign() {
		// TODO Auto-generated constructor stub
	}

	public List<TableDependency> getTableDependencies() {
		return tableDependencies;
	}

	public void setTableDependencies(List<TableDependency> tableDependencies) {
		this.tableDependencies = tableDependencies;
	}
	
	
	
}
