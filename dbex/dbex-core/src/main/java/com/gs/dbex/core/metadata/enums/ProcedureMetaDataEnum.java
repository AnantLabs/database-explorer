/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author sabuj.das
 * 
 */
public enum ProcedureMetaDataEnum {

	/**
	 * PROCEDURE_CAT String => procedure catalog (may be null)
	 */
	PROCEDURE_CAT("PROCEDURE_CAT"),
	/**
	 * PROCEDURE_SCHEM String => procedure schema (may be null)
	 */
	PROCEDURE_SCHEM("PROCEDURE_SCHEM"),
	/**
	 * PROCEDURE_NAME String => procedure name
	 */
	PROCEDURE_NAME("PROCEDURE_NAME"),
	/**
	 * REMARKS String => explanatory comment on the procedure
	 */
	REMARKS("REMARKS"),
	/**
	 * PROCEDURE_TYPE short => kind of procedure:
	 * <ul>
	 * <li>procedureResultUnknown - Cannot determine if a return value will be
	 * returned</li>
	 * <li>procedureNoResult - Does not return a return value</li>
	 * <li>procedureReturnsResult - Returns a return value</li>
	 * </ul>
	 */
	PROCEDURE_TYPE("PROCEDURE_TYPE"),
	/**
	 * SPECIFIC_NAME String => The name which uniquely identifies this procedure
	 * within its schema.
	 */
	SPECIFIC_NAME("SPECIFIC_NAME"),
	/**
	 * COLUMN_NAME String => column/parameter name
	 */
	COLUMN_NAME("COLUMN_NAME"),
	/**
	 * COLUMN_TYPE Short => kind of column/parameter:
	 * <ul>
	 * <li>procedureColumnUnknown - nobody knows</li>
	 * <li>procedureColumnIn - IN parameter</li>
	 * <li>procedureColumnInOut - INOUT parameter</li>
	 * <li>procedureColumnOut - OUT parameter</li>
	 * <li>procedureColumnReturn - procedure return value</li>
	 * <li>procedureColumnResult - result column in ResultSet</li>
	 * </ul>
	 */
	COLUMN_TYPE("COLUMN_TYPE")
	;

	private String code;

	private ProcedureMetaDataEnum(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

}
