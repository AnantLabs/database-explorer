/** ***************************************************************************
 *		Oracle GUI	
 *	
 *	File	: Column.java
 *	Type	: com.gs.oracle.model.Column.java
 *	Date	: Jul 29, 2009	1:06:46 PM
 *
 *	Author	: Sabuj Das | sabuj.das@gmail.com
 *
 *	
 *****************************************************************************/
package com.gs.dbex.model.db;

import java.io.Serializable;

import com.gs.dbex.model.BaseDbModel;
import com.gs.dbex.model.common.ColumnHeader;

/**
 * Type Name : com.gs.oracle.model.Column
 * 
 */
public class Column extends BaseDbModel implements Serializable {

	private String tableName;
	private Table parentTable;
	private Integer columnID;
	private Boolean nullable;
	private Integer dataType;
	private String typeName;
	private Integer size;
	private Integer precision;
	private Object defaultValue;
	private Boolean primaryKey;
	private Boolean foreignKey;
	private String privileges;

	public Column() {
		// TODO Auto-generated constructor stub
	}
	
	public Column(Table table) {
		parentTable = table;
	}


	public String getPrivileges() {
		return privileges;
	}


	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}


	public Table getParentTable() {
		return parentTable;
	}

	public void setParentTable(Table parentTable) {
		this.parentTable = parentTable;
	}

	@ColumnHeader(title = "COLUMN_NAME", index = 0)
	public String getModelName() {
		return super.getModelName();
	}

	@ColumnHeader(title = "COLUMN_ID", index = 1)
	public Integer getColumnID() {
		return columnID;
	}

	@ColumnHeader(title = "NULL_ABLE", index = 2)
	public Boolean getNullable() {
		if (null == nullable)
			return Boolean.FALSE;
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	@ColumnHeader(title = "DATA_TYPE_NAME", index = 3)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@ColumnHeader(title = "COLUMN_SCALE", index = 5)
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@ColumnHeader(title = "COLUMN_PRECISION", index = 4)
	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	@ColumnHeader(title = "DEFAULT_VALUE", index = 6)
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	@ColumnHeader(title = "IS_PRIMARY_KEY", index = 7)
	public Boolean getPrimaryKey() {
		if (null == primaryKey)
			return Boolean.FALSE;
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	@ColumnHeader(title = "IS_FOREIGN_KEY", index = 8)
	public Boolean getForeignKey() {
		if (null == foreignKey)
			return Boolean.FALSE;
		return foreignKey;
	}

	public void setForeignKey(Boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public void setColumnID(Integer columnID) {
		this.columnID = columnID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@ColumnHeader(title = "COMMENTS", index = 9)
	public String getComments() {
		return super.getComments();
	}

	@Override
	public String toString() {
		return getModelName() + " [ " + typeName + ", " + ((null != size) ? "(" + size + ") " : "")
				+ ((nullable) ? "NULL" : "NOTNULL") + " ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((columnID == null) ? 0 : columnID.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Column))
			return false;
		Column other = (Column) obj;
		if (columnID == null) {
			if (other.columnID != null)
				return false;
		} else if (!columnID.equals(other.columnID))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

}
