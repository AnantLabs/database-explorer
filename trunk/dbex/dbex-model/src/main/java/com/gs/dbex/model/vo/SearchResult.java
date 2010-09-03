/**
 * 
 */
package com.gs.dbex.model.vo;

import java.sql.Timestamp;

/**
 * @author sabuj.das
 *
 */
public class SearchResult {

	private String ownerName;
	private String objectName;
	private String objectType;
	private Timestamp createdDate;
	private Timestamp lastDDLDate;
	private String status;
	private Character temporary;
	private Character secondary;
	private Character granted;
	
	public SearchResult() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastDDLDate() {
		return lastDDLDate;
	}
	public void setLastDDLDate(Timestamp lastDDLDate) {
		this.lastDDLDate = lastDDLDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Character getTemporary() {
		return temporary;
	}
	public void setTemporary(Character temporary) {
		this.temporary = temporary;
	}
	public Character getSecondary() {
		return secondary;
	}
	public void setSecondary(Character secondary) {
		this.secondary = secondary;
	}
	public Character getGranted() {
		return granted;
	}
	public void setGranted(Character granted) {
		this.granted = granted;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((objectName == null) ? 0 : objectName.hashCode());
		result = prime * result
				+ ((ownerName == null) ? 0 : ownerName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SearchResult))
			return false;
		SearchResult other = (SearchResult) obj;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		return true;
	}
	
	
}
