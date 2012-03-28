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
public class XmlRowVo implements Serializable {

	private List<XmlColumnVo> xmlColumnList;
	
	public XmlRowVo() {
		xmlColumnList = new ArrayList<XmlColumnVo>();
	}

	public List<XmlColumnVo> getXmlColumnList() {
		return xmlColumnList;
	}

	public void setXmlColumnList(List<XmlColumnVo> xmlColumnList) {
		this.xmlColumnList = xmlColumnList;
	}
	
	
}
