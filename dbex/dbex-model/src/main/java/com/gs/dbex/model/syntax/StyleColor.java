/**
 * 
 */
package com.gs.dbex.model.syntax;

import java.awt.Color;
import java.io.Serializable;

import com.gs.utils.awt.ColorUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class StyleColor implements Serializable {

	private boolean editable;
	private String type;
	private String colorCode;
	private Integer red;
	private Integer green;
	private Integer blue;
	
	public StyleColor() {
		
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the colorCode
	 */
	public String getColorCode() {
		return colorCode;
	}

	/**
	 * @return the red
	 */
	public Integer getRed() {
		return red;
	}

	/**
	 * @return the green
	 */
	public Integer getGreen() {
		return green;
	}

	/**
	 * @return the blue
	 */
	public Integer getBlue() {
		return blue;
	}

	/**
	 * @param colorCode the colorCode to set
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(Integer red) {
		this.red = red;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(Integer green) {
		this.green = green;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	public void setColor(Color color) {
		if(color == null)
			return;
		setRed(color.getRed());
		setGreen(color.getGreen());
		setBlue(color.getBlue());
		setColorCode(ColorUtil.encodeColor(color));
	}

	
}
