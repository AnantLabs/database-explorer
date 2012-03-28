/**
 * 
 */
package com.gs.dbex.model.vo;

import java.io.Serializable;

/**
 * @author Sabuj Das
 *
 */
public class XmlColumnVo implements Serializable{

	private String columnName;
	private Object columnValue;
	
	public XmlColumnVo() {
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}
	
	
}
