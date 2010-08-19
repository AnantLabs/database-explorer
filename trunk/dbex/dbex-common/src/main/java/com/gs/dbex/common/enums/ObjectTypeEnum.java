/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum ObjectTypeEnum {

	CONSUMER_GROUP	("CONSUMER GROUP","CONSUMER GROUP"),
	SEQUENCE	("SEQUENCE","SEQUENCE"),
	SCHEDULE	("SCHEDULE","SCHEDULE"),
	PROCEDURE	("PROCEDURE","PROCEDURE"),
	OPERATOR	("OPERATOR","OPERATOR"),
	WINDOW	("WINDOW","WINDOW"),
	PACKAGE	("PACKAGE","PACKAGE"),
	LIBRARY	("LIBRARY","LIBRARY"),
	JOB_CLASS	("JOB CLASS","JOB CLASS"),
	TABLE	("TABLE","TABLE"),
	SYNONYM	("SYNONYM","SYNONYM"),
	VIEW	("VIEW","VIEW"),
	FUNCTION	("FUNCTION","FUNCTION"),
	WINDOW_GROUP	("WINDOW GROUP","WINDOW GROUP"),
	TYPE	("TYPE","TYPE"),
	EVALUATION_CONTEXT	("EVALUATION CONTEXT","EVALUATION CONTEXT");
	
	private String typeCode;
	private String description;
	
	private ObjectTypeEnum(String code, String desc){
		this.typeCode = code;
		this.description = desc;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
