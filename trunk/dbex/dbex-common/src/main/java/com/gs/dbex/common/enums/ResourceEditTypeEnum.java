/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum ResourceEditTypeEnum {

	RENAME("RENAME", "Rename"),
	COPY("COPY", "Copy"),
	DROP("DROP", "Drop"),
	TRUNCATE("TRUNCATE", "Truncate"),
	COMMENT("COMMENT", "Comment"),
	MODIFY_ALL("MODIFY_ALL", "Modify");
	
	private String code, description;

	private ResourceEditTypeEnum(String code, String description) {
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
