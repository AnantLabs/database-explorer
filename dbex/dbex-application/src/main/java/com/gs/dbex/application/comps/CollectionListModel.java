/**
 * 
 */
package com.gs.dbex.application.comps;

import java.util.List;

import javax.swing.AbstractListModel;

/**
 * @author sabuj.das
 * 
 */
public class CollectionListModel<T extends Object> extends AbstractListModel {

	private List<T> dataList;

	public CollectionListModel(List<T> dataList) {
		this.dataList = dataList;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getSize() {
		return (null != dataList) ? dataList.size() : 0;
	}

	public T getElementAt(int index) {
		return (null != dataList) ? (T)dataList.get(index) : null;
	}

	public void addElement(T element){
		addElementAt(element, getSize());
	}
	
	public void addElementAt(T element, int index){
		dataList.add(index, element);
	}
}
