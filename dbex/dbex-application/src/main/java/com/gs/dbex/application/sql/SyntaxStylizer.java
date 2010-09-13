
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
import com.gs.dbex.application.sql.processor.TokenType;
import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.common.enums.SqlStyleEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.historyMgr.ApplicationDataHistoryMgr;
import com.gs.dbex.historyMgr.DbexHistoryMgrBeanFactory;
import com.gs.dbex.model.syntax.FontStyle;
import com.gs.dbex.model.syntax.StyleColor;
import com.gs.dbex.model.syntax.StyleConfiguration;
import com.gs.dbex.model.syntax.StyleConfigurationHelper;
import com.gs.dbex.model.syntax.WordFont;
import com.gs.dbex.model.syntax.WordStyle;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.utils.io.IOUtil;
import com.gs.utils.xml.rw.XmlRWUtils;



/**
 * @author Sabuj Das
 *
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
    	if(null == configuration)
    		return;
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
		ApplicationDataHistoryMgr applicationDataHistoryMgr = DbexHistoryMgrBeanFactory.getInstance().getApplicationDataHistoryMgr();
		if(null != applicationDataHistoryMgr){
			try {
				return applicationDataHistoryMgr.getStyleConfiguration(DbexCommonContext.getInstance().getSyntaxFileName());
			} catch (DbexException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}