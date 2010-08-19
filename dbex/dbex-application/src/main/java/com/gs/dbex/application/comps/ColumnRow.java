package com.gs.dbex.application.comps;

import java.io.Serializable;

/**
 * 
 */

/**
 * @author Green Moon
 *
 */
public class ColumnRow implements Serializable{

	/**
	 * serialVersionUID = 2010166660354689949L
	 */
	private static final long serialVersionUID = 2010166660354689949L;

	private String columnName;
	private Class columnClass;
	private Object columnValue;
	
	
	public ColumnRow() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}


	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	/**
	 * @return the columnClass
	 */
	public Class getColumnClass() {
		return columnClass;
	}


	/**
	 * @param columnClass the columnClass to set
	 */
	public void setColumnClass(Class columnClass) {
		this.columnClass = columnClass;
	}


	/**
	 * @return the columnValue
	 */
	public Object getColumnValue() {
		return columnValue;
	}


	/**
	 * @param columnValue the columnValue to set
	 */
	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	
}
