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

import java.text.MessageFormat;

/**
 * Representation of a interpreted symbol from a processor.
 * <p>
 * 
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
 */
/**
 * A Symbol represents the information shared between similar tokens, i.e. their type and spelling.
 */
public class TextSymbol {

    /**
     * The type isused to classify symbols.
     * <p>
     * It also distinguishes different symbols with the same spelling, where necessary.
     */
    public final TokenType type;

    /**
     * The spelling.
     */
    public final String name;

    /**
     * Construct a symbol from its type and name.
     */
    public TextSymbol(TokenType type, String name) {

        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {

        return MessageFormat.format("TextSymbol [name=''{0}'', type=''{1}'']", name, type);
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TextSymbol) {
            TextSymbol that = (TextSymbol) obj;
            return name.equalsIgnoreCase(that.name) && type == that.type;
        }
        return false;
    }

}
