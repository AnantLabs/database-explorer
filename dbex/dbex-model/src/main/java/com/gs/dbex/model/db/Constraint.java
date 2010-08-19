/**
 * 
 */
package com.gs.dbex.model.db;

import java.io.Serializable;

import com.gs.dbex.model.BaseDbModel;
import com.gs.dbex.model.common.ColumnHeader;

/**
 * @author sabuj.das
 *
 */
public class Constraint extends BaseDbModel implements Serializable{

	private String constraintType;
	private String constraintColumn;
	private String referenceOwner;
	private String referencedTable;
	private String referenceColumnName;
	private String referenceConstraintName;
	
	public Constraint() {
		// TODO Auto-generated constructor stub
	}

	@ColumnHeader(title="NAME", index=0)
	public String getModelName() {
		return super.getModelName();
	}
	
	@ColumnHeader(title="COLUMN", index=1)
	public String getConstraintColumn() {
		return constraintColumn;
	}
	
	@ColumnHeader(title="TYPE", index=2)
	public String getConstraintType() {
		return constraintType;
	}
	
	@ColumnHeader(title="REFERENCE_OWNER", index=3)
	public String getReferenceOwner() {
		return referenceOwner;
	}
	
	@ColumnHeader(title="REFERENCED_TABLE", index=4)
	public String getReferencedTable() {
		return referencedTable;
	}
	
	@ColumnHeader(title="REFERENCE_COLUMN", index=5)
	public String getReferenceColumnName() {
		return referenceColumnName;
	}
	
	@ColumnHeader(title="REFERENCE_CONSTRAINT", index=6)
	public String getReferenceConstraintName() {
		return referenceConstraintName;
	}
	
	

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	public void setReferenceOwner(String referenceOwner) {
		this.referenceOwner = referenceOwner;
	}

	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}

	public void setReferenceColumnName(String referenceColumnName) {
		this.referenceColumnName = referenceColumnName;
	}

	public void setReferenceConstraintName(String referenceConstraintName) {
		this.referenceConstraintName = referenceConstraintName;
	}

	
	public void setConstraintColumn(String constraintColumn) {
		this.constraintColumn = constraintColumn;
	}
	
	
	
}
