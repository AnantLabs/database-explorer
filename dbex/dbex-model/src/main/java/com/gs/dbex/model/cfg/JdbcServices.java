/**
 * 
 */
package com.gs.dbex.model.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sabuj.das
 *
 */
public class JdbcServices {

	private List<String> classNameList;
	
	public JdbcServices() {
		classNameList = new ArrayList<String>();
	}

	public List<String> getClassNameList() {
		return classNameList;
	}

	public void setClassNameList(List<String> classNameList) {
		this.classNameList = classNameList;
	}
	
	
}
