/**
 * 
 */
package com.gs.dbex.model.vo;

import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class TableSearchCriteria extends SearchCriteria {

	private static final String TABLE_SEARCH_QUERY_PART_1
		= 		"SELECT * " +
				"FROM " +
				"	ALL_OBJECTS OBJ, " +
				"	ALL_TABLES TAB " +
				"WHERE " +
				"	OBJ.OWNER = TAB.OWNER " +
				"	AND OBJ.OBJECT_NAME = TAB.TABLE_NAME " +
				"	AND OBJ.OBJECT_TYPE = 'TABLE' ";
	
	private String tableType;
	private String searchQuery;
	
	public TableSearchCriteria(String ownerName, String searchString,
			String searchObjectType, boolean inAllOwners) {
		super(ownerName, searchString, searchObjectType, inAllOwners);
		initSearchQuery();
	}
	
	
	
	public void initSearchQuery() {
		StringBuffer queryBuffer = new StringBuffer(TABLE_SEARCH_QUERY_PART_1);
		if(!isInAllOwners()){
			if(StringUtil.hasValidContent(getOwnerName())){
				queryBuffer.append(" AND OBJ.OWNER = '")
					.append(getOwnerName().toUpperCase())
					.append("' ");
			}
		}
		
		if(StringUtil.hasValidContent(getSearchString())){
			queryBuffer.append(" AND TAB.TABLE_NAME ")
				.append(formTableName(getSearchString()))
				.append(" ");
		}
		searchQuery = queryBuffer.toString();
	}

	private Object formTableName(String searchString) {
		StringBuffer nameBuffer = new StringBuffer();
		if(searchString.contains("*") || searchString.contains("?")){
			nameBuffer.append(" LIKE ")
				.append("'")
				.append(searchString.replaceAll("\\*", "%").replaceAll("\\?", "_").toUpperCase())
				.append("'");
		} else {
			nameBuffer.append(" = ")
				.append("'")
				.append(searchString.toUpperCase())
				.append("'");
		}
		return nameBuffer.toString();
	}

	public TableSearchCriteria(String ownerName, String searchString,
			String searchObjectType, boolean inAllOwners, String type) {
		super(ownerName, searchString, searchObjectType, inAllOwners);
		tableType = type;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
	
	
}
