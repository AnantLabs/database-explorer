/**
 * 
 */
package com.gs.dbex.model.syntax;

import java.io.Serializable;

/**
 * @author sabuj.das
 *
 */
public class FontStyle implements Serializable {

	private boolean bold;
	private boolean italic;
	private boolean underlined;
	
	public FontStyle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @return the italic
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * @return the underlined
	 */
	public boolean isUnderlined() {
		return underlined;
	}

	/**
	 * @param bold the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * @param italic the italic to set
	 */
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * @param underlined the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}
	
	
}
