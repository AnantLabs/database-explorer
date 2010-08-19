package com.gs.dbex.application.tree.db;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.gs.dbex.application.tree.DatabaseDirectoryTree;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.swing.tree.IconData;

public class SchemaNode implements DatabaseNode<Schema>, Comparable<SchemaNode> {
	protected Schema schema;
	protected ConnectionProperties connectionProperties;
	
	public SchemaNode(Schema s, ConnectionProperties connectionProperties) {
		this.schema = s;
	}

	public boolean hasTables() {
		if (null != getSchema().getTableList()
				&& getSchema().getTableList().size() > 0)
			return true;
		return false;
	}

	
	public Schema getSchema() {
		return schema;
	}

	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	
	public String toString() {
		return schema.toString();
	}

	public boolean expand(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent
				.getFirstChild();
		if (flag == null) // No flag
			return false;
		Object obj = flag.getUserObject();
		if (!(obj instanceof Boolean))
			return false; // Already expanded

		parent.removeAllChildren(); // Remove Flag

		List<Table> tables = schema.getTableList();
		if (tables == null) {
			return true;
		}

		Vector<TableNode> tableNodeVector = new Vector<TableNode>();

		for (int k = 0; k < tables.size(); k++) {
			Table t = tables.get(k);
			TableNode tableNode = new TableNode(t, connectionProperties);
			boolean isAdded = false;
			for (int i = 0; i < tableNodeVector.size(); i++) {
				TableNode nd = tableNodeVector.elementAt(i);
				if (tableNode.compareTo(nd) < 0) {
					tableNodeVector.insertElementAt(tableNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				tableNodeVector.addElement(tableNode);
		}

		for (int i = 0; i < tableNodeVector.size(); i++) {
			TableNode nd = tableNodeVector.elementAt(i);
			IconData idata = null;
			if(nd.table.isDeleted()){
				idata = new IconData(DatabaseDirectoryTree.ICON_TABLE_DELETED,
						DatabaseDirectoryTree.ICON_TABLE_DELETED, nd);
			}else{
				idata = new IconData(DatabaseDirectoryTree.ICON_TABLE,
						DatabaseDirectoryTree.ICON_TABLE, nd);
			}
			if (idata == null) {
				continue;
			}
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);

			if (nd.hasColumns())
				node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		return true;
	}

	
	public int compareTo(SchemaNode o) {
		return schema.getModelName().compareTo(o.getSchema().getModelName());
	}
}
