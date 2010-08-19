/**
 * 
 */
package com.gs.dbex.application.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sabuj Das
 *
 */
public class SyntaxStyle implements Serializable {

	
	private String syntaxStyleName;
	private String Language;
	private Integer displayOrder = 0;
	private StyleColor bodyBackgroundColor;
	
	private WordStyles wordStyleList;
	
	public SyntaxStyle() {
		
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return Language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		Language = language;
	}

	/**
	 * @return the syntaxStyleName
	 */
	public String getSyntaxStyleName() {
		return syntaxStyleName;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @return the bodyBackgroundColor
	 */
	public StyleColor getBodyBackgroundColor() {
		return bodyBackgroundColor;
	}


	/**
	 * @param syntaxStyleName the syntaxStyleName to set
	 */
	public void setSyntaxStyleName(String syntaxStyleName) {
		this.syntaxStyleName = syntaxStyleName;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @param bodyBackgroundColor the bodyBackgroundColor to set
	 */
	public void setBodyBackgroundColor(StyleColor bodyBackgroundColor) {
		this.bodyBackgroundColor = bodyBackgroundColor;
	}

	/**
	 * @return the wordStyleList
	 */
	public WordStyles getWordStyleList() {
		return wordStyleList;
	}

	/**
	 * @param wordStyleList the wordStyleList to set
	 */
	public void setWordStyleList(WordStyles wordStyleList) {
		this.wordStyleList = wordStyleList;
	}


	
}
