/**
 * 
 */
package com.gs.dbex.model.db;

import java.io.Serializable;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 *
 */
public class ForeignKey extends BaseDbModel implements Serializable {

	
	private String pkTableCat;
	private String pkTableSchem;
	private String pkTableName;
	private String pkColumnName;
	private String fkTableCat;
	private String fkTableSchem;
	private String fkTableName;
	private String fkColumnName;
	private Short keySeq;
	private Short updateRule;
	private Short deleteRule;
	private String fkName;
	private String pkName;
	private Short deferrability;
	
	private Boolean importedKey = false;
	
	
	public String getPkTableCat() {
		return pkTableCat;
	}
	public void setPkTableCat(String pkTableCat) {
		this.pkTableCat = pkTableCat;
	}
	public String getPkTableSchem() {
		return pkTableSchem;
	}
	public void setPkTableSchem(String pkTableSchem) {
		this.pkTableSchem = pkTableSchem;
	}
	public String getPkTableName() {
		return pkTableName;
	}
	public void setPkTableName(String pkTableName) {
		this.pkTableName = pkTableName;
	}
	public String getPkColumnName() {
		return pkColumnName;
	}
	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}
	public String getFkTableCat() {
		return fkTableCat;
	}
	public void setFkTableCat(String fkTableCat) {
		this.fkTableCat = fkTableCat;
	}
	public String getFkTableSchem() {
		return fkTableSchem;
	}
	public void setFkTableSchem(String fkTableSchem) {
		this.fkTableSchem = fkTableSchem;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}
	public String getFkColumnName() {
		return fkColumnName;
	}
	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}
	public Short getKeySeq() {
		return keySeq;
	}
	public void setKeySeq(Short keySeq) {
		this.keySeq = keySeq;
	}
	public Short getUpdateRule() {
		return updateRule;
	}
	public void setUpdateRule(Short updateRule) {
		this.updateRule = updateRule;
	}
	public Short getDeleteRule() {
		return deleteRule;
	}
	public void setDeleteRule(Short deleteRule) {
		this.deleteRule = deleteRule;
	}
	public String getFkName() {
		return fkName;
	}
	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public Short getDeferrability() {
		return deferrability;
	}
	public void setDeferrability(Short deferrability) {
		this.deferrability = deferrability;
	}
	public Boolean getImportedKey() {
		return importedKey;
	}
	public void setImportedKey(Boolean importedKey) {
		this.importedKey = importedKey;
	} 
	
	
}
