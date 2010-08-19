/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author sabuj.das
 *
 */
public enum CatalogMetadataEnum {

	TABLE_CAT("TABLE_CAT");
	
	private String code;

	private CatalogMetadataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
