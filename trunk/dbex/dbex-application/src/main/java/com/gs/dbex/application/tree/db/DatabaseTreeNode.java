package com.gs.dbex.application.tree.db;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.gs.dbex.application.tree.DatabaseDirectoryTree;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.utils.swing.tree.IconData;

public class DatabaseTreeNode  implements DatabaseNode<Database> {

	protected Database database;
	protected ConnectionProperties connectionProperties;

	public DatabaseTreeNode(Database database, ConnectionProperties connectionProperties) {
		this.database = database;
		this.connectionProperties = connectionProperties;
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

		List<Schema> schemas = database.getSchemaList();
		if (schemas == null) {
			return true;
		}

		Vector<SchemaNode> schemaNodeVector = new Vector<SchemaNode>();

		for (int k = 0; k < schemas.size(); k++) {
			Schema s = schemas.get(k);
			SchemaNode schemaNode = new SchemaNode(s, connectionProperties);
			boolean isAdded = false;
			for (int i = 0; i < schemaNodeVector.size(); i++) {
				SchemaNode nd = schemaNodeVector.elementAt(i);
				if (schemaNode.compareTo(nd) < 0) {
					schemaNodeVector.insertElementAt(schemaNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				schemaNodeVector.addElement(schemaNode);
		}

		for (int i = 0; i < schemaNodeVector.size(); i++) {
			SchemaNode nd = schemaNodeVector.elementAt(i);
			IconData idata = new IconData(DatabaseDirectoryTree.ICON_SCHEMA,
					DatabaseDirectoryTree.ICON_SCHEMA, nd);

			if (idata == null) {
				continue;
			}
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);

			if (nd.hasTables())
				node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		return true;
	}


	public Database getDatabase() {
		return database;
	}

	
	public void setDatabase(Database database) {
		this.database = database;
	}

	
	public String toString() {
		return database.toString();
	}

}
