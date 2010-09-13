/**
 * 
 */
package com.gs.dbex.model.syntax;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sabuj Das
 *
 */
public class WordStyle implements Serializable {

	private String wordType;
	private WordFont wordFont;
	
	private StyleColors wordColorList;
	
	public WordStyle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the wordType
	 */
	public String getWordType() {
		return wordType;
	}

	/**
	 * @return the wordFont
	 */
	public WordFont getWordFont() {
		return wordFont;
	}

	/**
	 * @param wordType the wordType to set
	 */
	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

	/**
	 * @param wordFont the wordFont to set
	 */
	public void setWordFont(WordFont wordFont) {
		this.wordFont = wordFont;
	}

	/**
	 * @return the wordColorList
	 */
	public StyleColors getWordColorList() {
		return wordColorList;
	}

	/**
	 * @param wordColorList the wordColorList to set
	 */
	public void setWordColorList(StyleColors wordColorList) {
		this.wordColorList = wordColorList;
	}

}
