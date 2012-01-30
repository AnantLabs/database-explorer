package com.gs.dbex.application.tree.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.gs.dbex.application.tree.DatabaseDirectoryTree;
import com.gs.dbex.common.ApplicationContextProvider;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.utils.swing.tree.IconData;

public class TableNode implements DatabaseNode<Table>, Comparable<TableNode> {
	protected Table table;
	protected ConnectionProperties connectionProperties;

	public TableNode(Table table, ConnectionProperties p) {
		this.table = table;
		this.connectionProperties = p;
	}

	public boolean hasColumns() {
		if (null != getTable().getColumnlist()
				&& getTable().getColumnlist().size() > 0)
			return true;
		return false;
	}

	
	public Table getTable() {
		return table;
	}

	
	public void setTable(Table table) {
		this.table = table;
	}

	
	public String toString() {
		return table.toString();
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

		List<Column> columns = table.getColumnlist();
		boolean hasCols = false;
		if (columns == null || columns.size() <= 0) {
			try {
				table = ((DatabaseMetadataService)
						ApplicationContextProvider.getInstance().getApplicationContext()
						.getBean(DatabaseMetadataService.BEAN_NAME)
					).getTableDetails(connectionProperties, 
						table.getSchemaName(), table.getModelName(), ReadDepthEnum.DEEP);
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
			if (table.getColumnlist() == null || table.getColumnlist().size() <= 0) {
				return true;
			} 
			columns = table.getColumnlist();
		}
			

		Vector<ColumnNode> columnNodeVector = new Vector<ColumnNode>();

		for (int k = 0; k < columns.size(); k++) {
			Column c = columns.get(k);
			ColumnNode columnNode = new ColumnNode(c);
			boolean isAdded = false;
			for (int i = 0; i < columnNodeVector.size(); i++) {
				ColumnNode nd = columnNodeVector.elementAt(i);
				if (columnNode.compareTo(nd) < 0) {
					columnNodeVector.insertElementAt(columnNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				columnNodeVector.addElement(columnNode);
		}

		for (int i = 0; i < columnNodeVector.size(); i++) {
			ColumnNode nd = columnNodeVector.elementAt(i);
			IconData idata = null;
			if (nd.getColumn().getPrimaryKey()) {
				idata = new IconData(DatabaseDirectoryTree.ICON_PK_COLUMN,
						DatabaseDirectoryTree.ICON_PK_COLUMN, nd);
			} else if (nd.getColumn().getForeignKey()) {
				idata = new IconData(DatabaseDirectoryTree.ICON_FK_COLUMN,
						DatabaseDirectoryTree.ICON_FK_COLUMN, nd);
			} else {
				idata = new IconData(DatabaseDirectoryTree.ICON_COLUMN,
						DatabaseDirectoryTree.ICON_COLUMN, nd);
			}
			if (idata == null) {
				continue;
			}
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);
		}

		return true;
	}

	
	public int compareTo(TableNode o) {
		return this.getTable().getModelName().compareTo(
				o.getTable().getModelName());
	}
}
