/**
 * 
 */
package com.gs.dbex.application.list.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;

import com.gs.dbex.model.db.Column;
import com.gs.utils.common.FieldSpecificComparator;

/**
 * @author sabuj.das
 *
 */
public class ColumnNameListModel extends AbstractListModel {

	private List<Column> columnList;
	private Map<String, Column> columnMap;
	
	public ColumnNameListModel(List<Column> columnList) {
		super();
		this.columnList = columnList;
		columnMap = new HashMap<String, Column>();
		for (Column column : columnList) {
			columnMap.put(column.getModelName(), column);
		}
	}
	
	public Column getColumnByName(String name){
		return columnMap.get(name);
	}
	
	/**
	 * @return the columnMap
	 */
	public Map<String, Column> getColumnMap() {
		return columnMap;
	}

	public String getElementAt(int index) {
		return columnList.get(index).getModelName();
	}

	
	public int getSize() {
		return columnList.size();
	}

	/**
	 * @return the columnList
	 */
	public List<Column> getColumnList() {
		return columnList;
	}

	/**
	 * @param columnList the columnList to set
	 */
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
		columnMap = new HashMap<String, Column>();
		for (Column column : columnList) {
			columnMap.put(column.getModelName(), column);
		}
	}

	public void addColumn(Column c){
		if(c != null){
			columnList = new ArrayList<Column>();
			columnMap.put(c.getModelName(), c);
			Collection<Column> cc = columnMap.values();
			for (Column column : cc) {
				columnList.add(column);
			}
		}
	}

	public void removeColumn(Column c){
		if(c != null){
			columnList = new ArrayList<Column>();
			columnMap.remove(c.getModelName());
			Collection<Column> cc = columnMap.values();
			for (Column column : cc) {
				columnList.add(column);
			}
		}
	}

	public Set<String> getColumnNames(){
		return columnMap.keySet();
	}
	
	public void clear(){
		setColumnList(new ArrayList<Column>());
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
		Collections.swap(columnList, i, j);
		refresh();
		return true;
	}
	
	public void refresh() {
		columnMap.clear();
		for (Column column : columnList) {
			columnMap.put(column.getModelName(), column);
		}
	}

	public void swap(Column c1, Column c2){
		Column temp = c1;
		c1 = c2;
		c2 = temp;
	}
	
	public void sortByName(){
		FieldSpecificComparator<Column, Integer> comparator
			= new FieldSpecificComparator<Column, Integer>("modelName");
		Collections.sort(columnList, comparator);
	}
}
