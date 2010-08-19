/**
 * 
 */
package com.gs.dbex.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 *
 */
public class Database extends BaseDbModel implements Serializable {

	private List<Schema> schemaList;
	
	public Database() {
		schemaList = new ArrayList<Schema>();
	}

	/**
	 * @return the schemaList
	 */
	public List<Schema> getSchemaList() {
		return schemaList;
	}

	/**
	 * @param schemaList the schemaList to set
	 */
	public void setSchemaList(List<Schema> schemaList) {
		this.schemaList = schemaList;
	}
	
	
}
