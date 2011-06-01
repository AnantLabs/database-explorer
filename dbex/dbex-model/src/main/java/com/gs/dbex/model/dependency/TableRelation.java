/**
 * 
 */
package com.gs.dbex.model.dependency;

import java.io.Serializable;

/**
 * @author sabuj.das
 *
 */
public abstract class TableRelation implements Serializable {

	private String relationTitle;
	private String relationType;
	private String foreignColumnName;

	public TableRelation() {
		
	}
	
	public String getRelationTitle() {
		return relationTitle;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationTitle(String relationTitle) {
		this.relationTitle = relationTitle;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getForeignColumnName() {
		return foreignColumnName;
	}

	public void setForeignColumnName(String foreignColumnName) {
		this.foreignColumnName = foreignColumnName;
	}
	

	
}
