package com.gs.dbex.application.tree.db;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class FolderNode<T> implements DatabaseNode<T>, Comparable<FolderNode<T>>{
	protected String name;
	protected List<T> contentList;

	public FolderNode(String name, List<T> contentList) {
		this.name = name;
		this.contentList = contentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<T> getContentList() {
		return contentList;
	}

	public void setContentList(List<T> contentList) {
		this.contentList = contentList;
	}

	public int compareTo(FolderNode<T> o) {
		return getName().compareTo(o.getName());
	};
	
	
	public String toString() {
		return getName();
	}

	
	public boolean expand(DefaultMutableTreeNode parent) {
		return true;
	}
}