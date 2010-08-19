/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum SearchObjectMetaDataEnum {

	
	OWNER	("OWNER"),
	OBJECT_NAME	("OBJECT_NAME"),
	OBJECT_TYPE	("OBJECT_TYPE"),
	CREATED	("CREATED"),
	LAST_DDL_TIME	("LAST_DDL_TIME"),
	STATUS	("STATUS"),
	TEMPORARY	("TEMPORARY"),
	GRANTED	("GRANTED"),
	SECONDARY	("SECONDARY"),
	TABLE_NAME	("TABLE_NAME"),
	TABLESPACE_NAME	("TABLESPACE_NAME"),
	CLUSTER_NAME	("CLUSTER_NAME"),
	NUM_ROWS	("NUM_ROWS"),
	CACHE	("CACHE"),
	TABLE_LOCK	("TABLE_LOCK"),
	NESTED	("NESTED");
	
	private String type;
	
	private SearchObjectMetaDataEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	
}
