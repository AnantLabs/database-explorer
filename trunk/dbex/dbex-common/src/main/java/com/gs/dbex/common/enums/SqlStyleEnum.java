/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum SqlStyleEnum {

	KEY_WORD("KEY_WORD"),
	SCHEMA_NAME("SCHEMA_NAME"),
	TABLE_NAME("TABLE_NAME"),
	COLUMN_NAME("COLUMN_NAME"),
	OPERATOR("OPERATOR"),
	BRACKET("BRACKET"),
	NUMBER("NUMBER"),
	CHAR_VALUE("CHAR_VALUE"),
	STRING_VALUE("STRING_VALUE"),
	DEFAULT("DEFAULT"),
	UNKNOWN("UNKNOWN"),
	ERROR("ERROR"),
	COMMENT("COMMENT"),
	MATCHED_BRACKET("MATCHED_BRACKET"),
	CURRENT_LINE("CURRENT_LINE"),
	FUNCTION("FUNCTION"),
	PROCEDURE("PROCEDURE"),
	START_COMMENT("START_COMMENT"),
	MID_COMMENT("MID_COMMENT"),
	END_COMMENT("END_COMMENT"),
	WHITESPACE("WHITESPACE"),
	SYSTEM_FUNCTION("SYSTEM_FUNCTION"),
    SYSTEM_PROCEDURE("SYSTEM_PROCEDURE"),
    USER_FUNCTION("USER_FUNCTION"),
    USER_PROCEDURE("USER_PROCEDURE");
	
	private String code;

	private SqlStyleEnum(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	public static SqlStyleEnum getTypeByCode(String code){
		if (KEY_WORD.getCode().equalsIgnoreCase(code)) {
			return KEY_WORD;
		} else if (SCHEMA_NAME.getCode().equalsIgnoreCase(code)) {
			return SCHEMA_NAME;
		} else if (TABLE_NAME.getCode().equalsIgnoreCase(code)) {
			return TABLE_NAME;
		} else if (COLUMN_NAME.getCode().equalsIgnoreCase(code)) {
			return COLUMN_NAME;
		} else if (OPERATOR.getCode().equalsIgnoreCase(code)) {
			return OPERATOR;
		} else if (BRACKET.getCode().equalsIgnoreCase(code)) {
			return BRACKET;
		} else if (NUMBER.getCode().equalsIgnoreCase(code)) {
			return NUMBER;
		} else if (CHAR_VALUE.getCode().equalsIgnoreCase(code)) {
			return CHAR_VALUE;
		} else if (STRING_VALUE.getCode().equalsIgnoreCase(code)) {
			return STRING_VALUE;
		} else if (DEFAULT.getCode().equalsIgnoreCase(code)) {
			return DEFAULT;
		} else if (UNKNOWN.getCode().equalsIgnoreCase(code)) {
			return UNKNOWN;
		} else if (ERROR.getCode().equalsIgnoreCase(code)) {
			return ERROR;
		} else if (COMMENT.getCode().equalsIgnoreCase(code)) {
			return COMMENT;
		} else if (MATCHED_BRACKET.getCode().equalsIgnoreCase(code)) {
			return MATCHED_BRACKET;
		} else if (CURRENT_LINE.getCode().equalsIgnoreCase(code)) {
			return CURRENT_LINE;
		} else if (FUNCTION.getCode().equalsIgnoreCase(code)) {
			return FUNCTION;
		} else if (PROCEDURE.getCode().equalsIgnoreCase(code)) {
			return PROCEDURE;
		} else if (START_COMMENT.getCode().equalsIgnoreCase(code)) {
			return START_COMMENT;
		} else if (MID_COMMENT.getCode().equalsIgnoreCase(code)) {
			return MID_COMMENT;
		} else if (END_COMMENT.getCode().equalsIgnoreCase(code)) {
			return END_COMMENT;
		} else if (WHITESPACE.getCode().equalsIgnoreCase(code)) {
			return WHITESPACE;
		}
		return DEFAULT;
	}
}
