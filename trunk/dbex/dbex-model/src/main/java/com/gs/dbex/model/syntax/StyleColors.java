/**
 * 
 */
package com.gs.dbex.model.syntax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sabuj.das
 *
 */
public class StyleColors implements Serializable {

	private List<StyleColor> colorList = new ArrayList<StyleColor>();
	
	private transient Map<String, StyleColor> colorMap;
	
	public StyleColors() {
		colorMap = new HashMap<String, StyleColor>();
	}

	public void loadStyleMap(){
		if(colorList != null){
			for (StyleColor c : colorList) {
				colorMap.put(c.getType(), c);
			}
		}
	}
	
	public StyleColor getStyleByType(String type){
		return colorMap.get(type);
	}

	/**
	 * @return the colorList
	 */
	public List<StyleColor> getColorList() {
		return colorList;
	}

	/**
	 * @param colorList the colorList to set
	 */
	public void setColorList(List<StyleColor> colorList) {
		this.colorList = colorList;
	}
	
	
}
