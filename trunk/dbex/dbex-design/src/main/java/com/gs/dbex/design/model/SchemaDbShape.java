/**
 * 
 */
package com.gs.dbex.design.model;

import java.io.Serializable;
import java.util.List;

import com.gs.utils.text.StringUtil;


/**
 * @author Sabuj Das
 *
 */
public class SchemaDbShape extends BaseDbShape implements Serializable {

	private String displayName;
	private List<TableDbShape> tableDbShapes;
	
	public SchemaDbShape(String schemaName) {
		setModelName(schemaName);
		if(StringUtil.hasValidContent(schemaName)){
			setDisplayName(schemaName.toUpperCase());
		}
	}

	public List<TableDbShape> getTableDbShapes() {
		return tableDbShapes;
	}

	public void setTableDbShapes(List<TableDbShape> tableDbShapes) {
		this.tableDbShapes = tableDbShapes;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
