/**
 * 
 */
package com.gs.dbex.application.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sabuj.das
 *
 */
public class WordStyles implements Serializable {

	private List<WordStyle> wordStyleList;
	
	private transient Map<String, WordStyle> wordStyleMap;
	
	public WordStyles() {
		wordStyleList = new ArrayList<WordStyle>();
		wordStyleMap = new HashMap<String, WordStyle>();
	}
	
	public void loadStyleMap(){
		if(wordStyleList != null){
			for (WordStyle ws : wordStyleList) {
				ws.getWordColorList().loadStyleMap();
				wordStyleMap.put(ws.getWordType(), ws);
			}
		}
	}
	
	public WordStyle getStyleByType(String type){
		return wordStyleMap.get(type);
	}

	/**
	 * @return the wordStyleList
	 */
	public List<WordStyle> getWordStyleList() {
		return wordStyleList;
	}

	/**
	 * @param wordStyleList the wordStyleList to set
	 */
	public void setWordStyleList(List<WordStyle> wordStyleList) {
		this.wordStyleList = wordStyleList;
	}
	
	
}
