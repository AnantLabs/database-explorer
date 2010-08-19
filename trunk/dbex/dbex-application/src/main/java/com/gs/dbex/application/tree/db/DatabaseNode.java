package com.gs.dbex.application.tree.db;

import javax.swing.tree.DefaultMutableTreeNode;

public interface DatabaseNode<T>{
	public boolean expand(DefaultMutableTreeNode parent);
}
