
package com.gs.dbex.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;


/**
 * @author sabuj.das
 *
 */
public class Schema extends BaseDbModel implements Serializable {

	private List<Table> tableList;
	
	public Schema() {
		tableList = new ArrayList<Table>();
	}
	

	public List<Table> getTableList() {
		return tableList;
	}

	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}
	
	
}
