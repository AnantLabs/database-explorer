package com.gs.dbex.common.enums;

public enum PrivilegeMetaDataEnum {

	/**
	      1.  TABLE_CAT String => table catalog (may be null)
		   2. TABLE_SCHEM String => table schema (may be null)
		   3. TABLE_NAME String => table name
		   4. GRANTOR String => grantor of access (may be null)
		   5. GRANTEE String => grantee of access
		   6. PRIVILEGE String => name of access (SELECT, INSERT, UPDATE, REFRENCES, ...)
		   7. IS_GRANTABLE String => "YES" if grantee is permitted to grant to others; "NO" if not; null if unknown 
	 */
	
	TABLE_CAT("TABLE_CAT"),
	TABLE_SCHEM("TABLE_SCHEM"),
	TABLE_NAME("TABLE_NAME"),
	COLUMN_NAME("COLUMN_NAME"),
	GRANTOR("GRANTOR"),
	GRANTEE("GRANTEE"),
	PRIVILEGE("PRIVILEGE"),
	IS_GRANTABLE("IS_GRANTABLE");
	
	
	private String code;
	
	private PrivilegeMetaDataEnum(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
		
}
