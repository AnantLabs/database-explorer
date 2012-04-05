package com.gs.dbex.application.tree.db;

import javax.swing.tree.DefaultMutableTreeNode;

import com.gs.dbex.model.db.Column;

public class ColumnNode implements DatabaseNode<Column>, Comparable<ColumnNode> {
	protected Column column;

	public ColumnNode(Column column) {
		this.column = column;
	}

	
	public Column getColumn() {
		return column;
	}

	
	public void setColumn(Column column) {
		this.column = column;
	}

	
	public String toString() {
		return column.toString();
	}

	public boolean expand(DefaultMutableTreeNode parent) {
		return false;
	}

	
	public int compareTo(ColumnNode o) {
		return getColumn().getModelName().compareTo(
				o.getColumn().getModelName());
	}
}
