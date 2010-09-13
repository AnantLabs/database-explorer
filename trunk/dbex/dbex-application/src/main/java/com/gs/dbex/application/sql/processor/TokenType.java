
package com.gs.dbex.application.sql.processor;

/**
 * Enumeration of Token Types that can processed within the SQL Processor.
 * <p>
 * 
 * @author Sabuj Das
 * 
 */
public enum TokenType {
    /**
     * 
     */
    UNRECOGNIZED,
    /**
     * 
     */
    WHITESPACE,
    /**
     * 
     */
    WORD,
    /**
     * 
     */
    NUMBER,
    /**
     * 
     */
    PUNCTUATION,
    /**
     * 
     */
    COMMENT,
    /**
     * 
     */
    START_COMMENT,
    /**
     * 
     */
    MID_COMMENT,
    /**
     * 
     */
    END_COMMENT,
    /**
     * 
     */
    TAG,
    /**
     * 
     */
    END_TAG,
    /**
     * 
     */
    KEYWORD,
    /**
     * 
     */
    FUNCTION,
    /**
     * 
     */
    IDENTIFIER,
    /**
     * 
     */
    LITERAL,
    /**
     * 
     */
    STRING,
    /**
     * 
     */
    CHARACTER,
    /**
     * 
     */
    OPERATOR,
    /**
     * 
     */
    BRACKET,
    /**
     * 
     */
    SEPARATOR,
    /**
     * 
     */
    URL,
    /**
     * 
     */
    START_VARIABLE,
    /**
     * 
     */
    VARIABLE,
    /**
     * 
     */
    SCHEMA_NAME,
    TABLE_NAME,
    COLUMN_NAME,
    /**
     */
    SYSTEM_FUNCTION,
    SYSTEM_PROCEDURE,
    USER_FUNCTION,
    USER_PROCEDURE;

    public static TokenType forByte(byte index) {

        if (index < 0) {
            return UNRECOGNIZED;
        }
        return values()[index];
    }
}
