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
 * @author Sabuj Das
 *
 */
public class StyleConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3212545444521071784L;
	
	private List<SyntaxStyle> syntaxStyleList;
	
	private transient Map<String, SyntaxStyle> syntaxStylemMap;
	
	public StyleConfiguration() {
		syntaxStyleList = new ArrayList<SyntaxStyle>();
		syntaxStylemMap = new HashMap<String, SyntaxStyle>();
	}
	
	public void loadStyleMap(){
		if(syntaxStyleList != null){
			for (SyntaxStyle style : syntaxStyleList) {
				if(null == style.getWordStyleList())
					style.setWordStyleList(new WordStyles());
				style.getWordStyleList().loadStyleMap();
				syntaxStylemMap.put(style.getLanguage(), style);
			}
		}
	}
	
	public SyntaxStyle getStyleByLanguage(String language){
		return syntaxStylemMap.get(language);
	}

	
	/**
	 * @return the syntaxStyleList
	 */
	public List<SyntaxStyle> getSyntaxStyleList() {
		return syntaxStyleList;
	}

	/**
	 * @param syntaxStyleList the syntaxStyleList to set
	 */
	public void setSyntaxStyleList(List<SyntaxStyle> syntaxStyleList) {
		this.syntaxStyleList = syntaxStyleList;
	}
	
	

}
