package com.gs.dbex.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;


public class Index extends BaseDbModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 799664973359626509L;
	
	private List<Column> indexedColumns;
	private boolean unique;
	private boolean fullText;


	public Index() {
		this.indexedColumns = new ArrayList<Column>();
	}
	
	public List<Column> getIndexedColumns() {
		return indexedColumns;
	}
	public void setIndexedColumns(List<Column> indexedColumns) {
		this.indexedColumns = indexedColumns;
	}
	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}
	/**
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	/**
	 * @return the fullText
	 */
	public boolean isFullText() {
		return fullText;
	}
	/**
	 * @param fullText the fullText to set
	 */
	public void setFullText(boolean fullText) {
		this.fullText = fullText;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
