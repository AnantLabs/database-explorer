/**
 * 
 */
package com.gs.dbex.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sabuj Das
 *
 */
public class XmlResultsVo implements Serializable {

	private List<XmlRowVo> xmlRowList;
	
	public XmlResultsVo() {
		xmlRowList = new ArrayList<XmlRowVo>();
	}

	public List<XmlRowVo> getXmlRowList() {
		return xmlRowList;
	}

	public void setXmlRowList(List<XmlRowVo> xmlRowList) {
		this.xmlRowList = xmlRowList;
	}
	
	
}
