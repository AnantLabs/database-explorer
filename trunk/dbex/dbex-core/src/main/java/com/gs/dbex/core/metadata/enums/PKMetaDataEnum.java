/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author sabuj.das
 * 
 */
public enum PKMetaDataEnum {

	/**
	 * 1. TABLE_CAT String => table catalog (may be null)
	 */
	TABLE_CAT("TABLE_CAT"),
	/**
	 * 2. TABLE_SCHEM String => table schema (may be null)
	 */
	TABLE_SCHEM("TABLE_SCHEM"),
	/**
	 * 3. TABLE_NAME String => table name
	 */
	TABLE_NAME("TABLE_NAME"),
	/**
	 * 4. COLUMN_NAME String => column name
	 */
	COLUMN_NAME("COLUMN_NAME"),
	/**
	 * 5. KEY_SEQ short => sequence number within primary key( a value of 1
	 * represents the first column of the primary key, a value of 2 would
	 * represent the second column within the primary key).
	 */
	KEY_SEQ("KEY_SEQ"),
	/**
	 * 6. PK_NAME String => primary key name (may be null)
	 */
	PK_NAME("PK_NAME");

	private String code;

	private PKMetaDataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
