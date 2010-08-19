/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author sabuj.das
 *
 *		TABLE_CAT String => table catalog (may be null)
 *		TABLE_SCHEM String => table schema (may be null)
 *		TABLE_NAME String => table name
 *		TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
 *		REMARKS String => explanatory comment on the table
 *		TYPE_CAT String => the types catalog (may be null)
 *		TYPE_SCHEM String => the types schema (may be null)
 *		TYPE_NAME String => type name (may be null)
 *		SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null)
 *		REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null) 
 **/
public enum TableMetaDataEnum {

	TABLE_CAT("TABLE_CAT"),
	TABLE_SCHEM("TABLE_SCHEM"),
	TABLE_NAME("TABLE_NAME"),
	TABLE_TYPE("TABLE_TYPE"),
	REMARKS("REMARKS"),
	TYPE_CAT("TYPE_CAT"),
	TYPE_SCHEM("TYPE_SCHEM"),
	TYPE_NAME("TYPE_NAME"),
	SELF_REFERENCING_COL_NAME("SELF_REFERENCING_COL_NAME"),
	REF_GENERATION("REF_GENERATION");
	
	private String code;

	private TableMetaDataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	
}
