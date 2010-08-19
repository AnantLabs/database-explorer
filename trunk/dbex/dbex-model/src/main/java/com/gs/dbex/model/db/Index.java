package com.gs.dbex.model.db;

import com.gs.dbex.model.BaseDbModel;


public class Index extends BaseDbModel {

	private Column indexedColumn;
	private boolean unique;
	private boolean fullText;
	/**
	 * @return the indexedColumn
	 */
	public Column getIndexedColumn() {
		return indexedColumn;
	}
	/**
	 * @param indexedColumn the indexedColumn to set
	 */
	public void setIndexedColumn(Column indexedColumn) {
		this.indexedColumn = indexedColumn;
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
