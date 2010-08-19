/**
 * 
 */
package com.gs.dbex.application.tree;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.utils.swing.tree.IconCellRenderer;
import com.gs.utils.swing.tree.IconData;

/**
 * @author sabuj.das
 * 
 */
public class TableColumnTree extends JTree implements ApplicationConstants {

	public static final ImageIcon ICON_ALL_COLUMN = new ImageIcon(
			TableColumnTree.class.getResource(IMAGE_PATH + "columngroup.gif"));
	
	public static final ImageIcon ICON_COLUMN = new ImageIcon(
			TableColumnTree.class.getResource(IMAGE_PATH + "columns.gif"));
	public static final String ALL_COLUMN_TEXT = "All Columns";

	private DefaultMutableTreeNode topNode;
	protected DefaultTreeModel defaultTreeModel;
	
	private Table databaseTable;


	public TableColumnTree(Table databaseTable) {
		this.databaseTable = databaseTable;
		init();
		
		
	}
	
	private void init(){
		topNode = populateTableColumnTree(databaseTable);
		defaultTreeModel = new DefaultTreeModel(topNode);
		setModel(defaultTreeModel);
		ToolTipManager.sharedInstance().registerComponent(this);
		putClientProperty("JTree.lineStyle", "Angled");
		TreeCellRenderer renderer = new IconCellRenderer();
		setCellRenderer(renderer);
	}

	private DefaultMutableTreeNode populateTableColumnTree(Table table) {
		DefaultMutableTreeNode allColumnsNode = new DefaultMutableTreeNode(
				new IconData(ICON_ALL_COLUMN, null, ALL_COLUMN_TEXT));
		for (Column c : table.getColumnlist()) {
			DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(
					new IconData(ICON_COLUMN, null, c.getModelName()));
			allColumnsNode.add(cNode);
		}
		
		return allColumnsNode;
	}
}
