/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum DatabaseStorageTypeEnum {

	CATALOG_STORAGE("CATALOG", "Store as Catalog"),
	SCHEMA_STORAGE("SCHEMA", "Store as Schema"),
	UNKNOWN("UNKNOWN", "Unknown");
	
	private String code;
	private String description;

	private DatabaseStorageTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static DatabaseStorageTypeEnum getTypeByDesc(String dsc){
		if(CATALOG_STORAGE.getDescription().equals(dsc))
			return CATALOG_STORAGE;
		if(SCHEMA_STORAGE.getDescription().equals(dsc))
			return SCHEMA_STORAGE;
		return UNKNOWN;
	}
	
	public static DatabaseStorageTypeEnum getDatabaseStorageTypeEnum(String code){
		if(CATALOG_STORAGE.getCode().equals(code))
			return CATALOG_STORAGE;
		if(SCHEMA_STORAGE.getCode().equals(code))
			return SCHEMA_STORAGE;
		return UNKNOWN;
	}
}
