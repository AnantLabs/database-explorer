/**
 * 
 */
package com.gs.dbex.application.comps;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;

import com.gs.dbex.model.db.Column;

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
	
	public void sort(Comparator<T> comparator){
		if(null != comparator){
			Collections.sort(getDataList(), comparator);
		}
	}
	
	public boolean swap(int i, int j){
		if(i < 0 || j < 0){
			return false;
		}
		if(i > getSize() || j > getSize()){
			return false;
		}
		if(i == j){
			return false;
		}
		Collections.swap(dataList, i, j);
		return true;
	}
	
	
	
}
