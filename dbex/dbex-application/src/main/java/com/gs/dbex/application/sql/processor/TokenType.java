/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 *
 * The Initial Developer of the Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 * Portions created by Mark A. Kobold are Copyright (C) 2000-2007. All Rights Reserved.
 *
 * Contributor(s): 
 *  Mark A. Kobold [mkobold <at> isqlviewer <dot> com].
 *  
 * If you didn't download this code from the following link, you should check
 * if you aren't using an obsolete version: http://www.isqlviewer.com
 */
package com.gs.dbex.application.sql.processor;

/**
 * Enumeration of Token Types that can processed within the SQL Processor.
 * <p>
 * 
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
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
