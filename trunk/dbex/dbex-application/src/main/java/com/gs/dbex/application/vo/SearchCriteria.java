/**
 * 
 */
package com.gs.dbex.application.vo;

/**
 * @author sabuj.das
 *
 */
public class SearchCriteria {

	private String ownerName;
	private String searchString;
	private String searchObjectType;
	private boolean inAllOwners;
	
	
	
	
	public SearchCriteria(String ownerName, String searchString,
			String searchObjectType, boolean inAllOwners) {
		this.ownerName = ownerName;
		this.searchString = searchString;
		this.searchObjectType = searchObjectType;
		this.inAllOwners = inAllOwners;
	}

	public boolean isInAllOwners() {
		return inAllOwners;
	}

	public void setInAllOwners(boolean inAllOwners) {
		this.inAllOwners = inAllOwners;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getSearchString() {
		return searchString;
	}

	public String getSearchObjectType() {
		return searchObjectType;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void setSearchObjectType(String searchObjectType) {
		this.searchObjectType = searchObjectType;
	}
	
	
	
}
