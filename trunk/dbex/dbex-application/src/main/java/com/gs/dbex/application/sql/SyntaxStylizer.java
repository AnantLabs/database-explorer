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
package com.gs.dbex.application.sql;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.mapping.FontStyle;
import com.gs.dbex.application.mapping.StyleColor;
import com.gs.dbex.application.mapping.StyleConfiguration;
import com.gs.dbex.application.mapping.StyleConfigurationHelper;
import com.gs.dbex.application.mapping.WordFont;
import com.gs.dbex.application.mapping.WordStyle;
import com.gs.dbex.application.sql.processor.TokenType;
import com.gs.dbex.common.enums.SqlStyleEnum;
import com.gs.utils.io.IOUtil;
import com.gs.utils.xml.rw.XmlRWUtils;


/**
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
 */
public class SyntaxStylizer implements PreferenceChangeListener {

    private Hashtable<TokenType, Style> styleMap = new Hashtable<TokenType, Style>();
    private JTextPane componentOwner = null;

    public SyntaxStylizer(JTextPane componentOwner) {

        this.componentOwner = componentOwner;
        initializeStyles();
    }

    public Style styleForWord(TokenType tokenType) {

        Style style = styleMap.get(tokenType);
        if (style == null) {
            return styleMap.get(TokenType.WHITESPACE);
        }
        return style;
    }

    public void preferenceChange(PreferenceChangeEvent event) {

    }

    public void changeStyle(TokenType type, Color color) {

        Style style = componentOwner.addStyle(type.toString(), null);
        StyleConstants.setForeground(style, color);
        styleMap.put(type, style);
    }

    public void changeStyle(TokenType type, Color color, int fontStyle) {

        Style style = componentOwner.addStyle(type.toString(), null);
        StyleConstants.setForeground(style, color);
        if ((fontStyle & Font.BOLD) != 0)
            StyleConstants.setBold(style, true);
        if ((fontStyle & Font.ITALIC) != 0)
            StyleConstants.setItalic(style, true);
        styleMap.put(type, style);
    }
    
    public void changeStyle(TokenType type, WordStyle wordStyle){
    	if(wordStyle == null)
    		return;
    	Style style = componentOwner.addStyle(type.toString(), null);
    	
    	StyleColor fg = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.FORE_GROUND);
    	StyleConstants.setForeground(style, Color.decode(fg.getColorCode()));
    	
    	StyleColor bg = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.BACK_GROUND);
    	StyleConstants.setBackground(style, Color.decode(bg.getColorCode()));
    	
    	WordFont wordFont = wordStyle.getWordFont();
    	StyleConstants.setFontFamily(style, wordFont.getFontName());
    	StyleConstants.setFontSize(style, wordFont.getFontSize());
    	
    	FontStyle fontStyle = wordFont.getFontStyle();
    	if(fontStyle.isBold()){
    		StyleConstants.setBold(style, true);
    	}
    	if(fontStyle.isItalic()){
    		StyleConstants.setItalic(style, true);
    	}
    	if(fontStyle.isUnderlined()){
    		StyleConstants.setUnderline(style, true);
    	}
    	styleMap.put(type, style);
    }

    private void initializeStyles() {

    	StyleConfiguration configuration = readSavedStyles();
    	configuration.loadStyleMap();
    	WordStyle wordStyle = null;
    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.KEY_WORD.getCode(), configuration);
    	changeStyle(TokenType.KEYWORD, wordStyle);
    	
    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.BRACKET.getCode(), configuration);
    	changeStyle(TokenType.BRACKET, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.CHAR_VALUE.getCode(), configuration);
    	changeStyle(TokenType.CHARACTER, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.COMMENT.getCode(), configuration);
    	changeStyle(TokenType.COMMENT, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.DEFAULT.getCode(), configuration);
    	changeStyle(TokenType.WHITESPACE, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.ERROR.getCode(), configuration);
    	changeStyle(TokenType.UNRECOGNIZED, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.FUNCTION.getCode(), configuration);
    	changeStyle(TokenType.FUNCTION, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.NUMBER.getCode(), configuration);
    	changeStyle(TokenType.NUMBER, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.STRING_VALUE.getCode(), configuration);
    	changeStyle(TokenType.STRING, wordStyle);

    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.SCHEMA_NAME.getCode(), configuration);
    	changeStyle(TokenType.SCHEMA_NAME, wordStyle);
    	
    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.TABLE_NAME.getCode(), configuration);
    	changeStyle(TokenType.TABLE_NAME, wordStyle);
    	
    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.COLUMN_NAME.getCode(), configuration);
    	changeStyle(TokenType.COLUMN_NAME, wordStyle);
    	
    	wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.OPERATOR.getCode(), configuration);
    	changeStyle(TokenType.OPERATOR, wordStyle);
    	
        /*changeStyle(TokenType.UNRECOGNIZED, Color.RED);
        changeStyle(TokenType.WHITESPACE, Color.BLACK);
        changeStyle(TokenType.WORD, Color.BLACK);

        changeStyle(TokenType.COMMENT, Color.decode("#3F7F5F"), Font.ITALIC);
        changeStyle(TokenType.START_COMMENT, Color.decode("#3F7F5F"), Font.ITALIC);
        changeStyle(TokenType.MID_COMMENT, Color.decode("#3F7F5F"), Font.ITALIC);
        changeStyle(TokenType.END_COMMENT, Color.decode("#3F7F5F"), Font.ITALIC);

        changeStyle(TokenType.VARIABLE, Color.decode("#003e85"), Font.ITALIC | Font.BOLD);

        
        WordStyle wordStyle = StyleConfigurationHelper.getWordStyleByType("SQL", 
        		SqlStyleEnum.KEY_WORD.getCode(), configuration);
        if(wordStyle != null){
        	String code = wordStyle.getWordColorList().getStyleByType("FG").getColorCode();
        	int style = Font.PLAIN;
        	if(wordStyle.getWordFont().getFontStyle().isBold()){
        		style = Font.BOLD;
        	}
        	changeStyle(TokenType.KEYWORD, Color.decode(code), style);
        }else
        	changeStyle(TokenType.KEYWORD, Color.decode("#7F0069"), Font.BOLD);
        
        changeStyle(TokenType.STRING, Color.BLUE);
        changeStyle(TokenType.CHARACTER, Color.decode("#008400"));
        changeStyle(TokenType.FUNCTION, Color.BLUE);

        changeStyle(TokenType.TABLE_NAME, Color.decode("#003e85"));

        Style style = componentOwner.addStyle(TokenType.TABLE_NAME.name(), null);
        StyleConstants.setForeground(style, Color.decode("#3f7f5f"));
        StyleConstants.setUnderline(style, true);
        style.addAttribute("hyperlinked", Boolean.TRUE);

        styleMap.put(TokenType.TABLE_NAME, style);*/
    }

    /*public static void main(String[] args) {
    	File in = new File("D:\\SVN_HOME\\MY_PROJECTS\\oracle-gui\\trunk\\documents\\sample\\SQL_Syntax\\sql-syntax-style.xml");
		File map = new File("D:\\SVN_HOME\\MY_PROJECTS\\oracle-gui\\trunk\\documents\\sample\\SQL_Syntax\\sql-syntax-style-mapping.xml");
		StyleConfiguration configuration = XmlRWUtils.readUsingCastor(in, map);
		if(configuration == null ){
			System.out.println("kjdfhl kglkdjfg h");
		}
	}*/
    
	private StyleConfiguration readSavedStyles() {
		InputStream mappingInputStream = IOUtil.getResourceAsStream(ApplicationConstants.SQL_SYNTAX_MAPPING_FILE);
		//File dataFile = IOUtil.mkfile(ApplicationConstants.SYNTAX_DATA_FILE);
		File dataFile = new File(ApplicationConstants.SYNTAX_DATA_FILE);
		try {
			return XmlRWUtils.readUsingCastor(dataFile, mappingInputStream);
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}