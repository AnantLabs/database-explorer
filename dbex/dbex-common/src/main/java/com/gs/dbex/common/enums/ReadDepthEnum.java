/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum ReadDepthEnum {

	DEEP("DEEP", "Deep Reading - Read all the informations."),
	MEDIUM("MEDIUM", "Medium Reading - Read avarage amount of informations."),
	SHALLOW("SHALLOW", "Shallow Reading - Read minimum amount of informations.");
	
	private String code;
	private String description;
	
	private ReadDepthEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
