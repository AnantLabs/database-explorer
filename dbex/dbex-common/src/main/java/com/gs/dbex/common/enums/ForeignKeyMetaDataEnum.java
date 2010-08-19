/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 * 
 */
public enum ForeignKeyMetaDataEnum {

	PKTABLE_CAT("PKTABLE_CAT"), 
	PKTABLE_SCHEM("PKTABLE_SCHEM"), 
	PKTABLE_NAME("PKTABLE_NAME"), 
	PKCOLUMN_NAME("PKCOLUMN_NAME"), 
	FKTABLE_CAT("FKTABLE_CAT"), 
	FKTABLE_SCHEM("FKTABLE_SCHEM"), 
	FKTABLE_NAME("FKTABLE_NAME"), 
	FKCOLUMN_NAME("FKCOLUMN_NAME"),
	KEY_SEQ("KEY_SEQ"), 
	UPDATE_RULE("UPDATE_RULE"), 
	DELETE_RULE("DELETE_RULE"), 
	FK_NAME("FK_NAME"), 
	PK_NAME("PK_NAME"), 
	DEFERRABILITY("DEFERRABILITY");

	private String code;

	private ForeignKeyMetaDataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
