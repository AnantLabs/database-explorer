/**
 * 
 */
package com.gs.dbex.application.tree;

import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.tree.db.ColumnNode;
import com.gs.dbex.application.tree.db.DatabaseNode;
import com.gs.dbex.application.tree.db.DatabaseTreeNode;
import com.gs.dbex.application.tree.db.FolderNode;
import com.gs.dbex.application.tree.db.SchemaNode;
import com.gs.dbex.application.tree.db.TableNode;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.swing.tree.IconCellRenderer;
import com.gs.utils.swing.tree.IconData;

/**
 * @author sabuj.das
 * 
 */
public class DatabaseDirectoryTree extends JTree implements ApplicationConstants{
	
	/**
	 * serialVersionUID = -5007366575847331963L;
	 */
	private static final long serialVersionUID = -5007366575847331963L;
	
	public static final ImageIcon ICON_ROOT_DATABASES = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH
					+ "DB_dev_perspective.gif"));
	public static final ImageIcon ICON_ROOT_DATABASE = new ImageIcon(
			DatabaseDirectoryTree.class
					.getResource(IMAGE_PATH + "Database.png"));
	public static final ImageIcon ICON_SCHEMA = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "schema.gif"));
	public static final ImageIcon ICON_TABLE = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "table.gif"));
	public static final ImageIcon ICON_COLUMN = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "columns.gif"));

	public static final ImageIcon ICON_PK_COLUMN = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH
					+ "PrimaryKeyColumn.gif"));
	public static final ImageIcon ICON_FK_COLUMN = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "ForeignKeyColumn.gif"));
	public static final ImageIcon ICON_FOLDER_TABLE = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "Folder_table.png"));
	public static final ImageIcon ICON_TABLE_DELETED = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(IMAGE_PATH + "deleted_table.gif"));

	private String rootNodeName;
	private Database database;
	private ConnectionProperties connectionProperties;

	public DatabaseDirectoryTree() {
	}

	public DatabaseDirectoryTree(ConnectionProperties connectionProperties, Database db) {
		this.connectionProperties = connectionProperties;
		this.database = db;
		initComponents();
	}

	public DefaultTreeModel getTreeModel() {
		return defaultTreeModel;
	}

	private void initComponents() {
		topNode = populateDatabaseTree(database);
		defaultTreeModel = new DefaultTreeModel(topNode);
		setModel(defaultTreeModel);
		ToolTipManager.sharedInstance().registerComponent(this);
		putClientProperty("JTree.lineStyle", "Angled");
		TreeCellRenderer renderer = new IconCellRenderer();
		setCellRenderer(renderer);
		addTreeExpansionListener(new DatabaseExpansionListener());
		// addTreeSelectionListener(new DirSelectionListener());

		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		setShowsRootHandles(true);
		setEditable(false);

	}
	
	public void reload(Database db){
		this.database = db;
		removeAll();
		initComponents();
	}

	public DefaultMutableTreeNode populateDatabaseTree(Database database) {
		DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(
				new IconData(ICON_ROOT_DATABASES, null, new DatabaseTreeNode(
						database, getConnectionProperties())));
		for (Schema schema : database.getSchemaList()) {
			DefaultMutableTreeNode sNode = new DefaultMutableTreeNode(
					new IconData(ICON_SCHEMA, null, new SchemaNode(schema, getConnectionProperties())));
			// add all the tables in the table folder.
			DefaultMutableTreeNode tableFolderNode = new DefaultMutableTreeNode(
					new IconData(ICON_FOLDER_TABLE, ICON_SCHEMA, 
							new FolderNode<Table>("Tables", schema.getTableList())));
			if(null != schema.getTableList() && schema.getTableList().size() > 0){
				for (Table t : schema.getTableList()) {
					DefaultMutableTreeNode tNode = null;
					TableNode tn = new TableNode(t, getConnectionProperties());
					
					if(t.isDeleted()){
						tNode = new DefaultMutableTreeNode(
								new IconData(ICON_TABLE_DELETED, null, tn), true);
					}else{
						tNode = new DefaultMutableTreeNode(
								new IconData(ICON_TABLE, null, tn), true);
						
					}
					for (Column c : t.getColumnlist()) {
						DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(
								new IconData(ICON_COLUMN, null, new ColumnNode(c)));
						if (null != c.getPrimaryKey() && Boolean.TRUE.equals(c.getPrimaryKey())) {
							cNode = new DefaultMutableTreeNode(new IconData(
									ICON_PK_COLUMN, null, new ColumnNode(c)));
						}else if (null != c.getForeignKey() && Boolean.TRUE.equals(c.getForeignKey())) {
							cNode = new DefaultMutableTreeNode(new IconData(
									ICON_FK_COLUMN, null, new ColumnNode(c)));
						} 
						tNode.add(cNode);
					}
					tNode.add(new DefaultMutableTreeNode(new Boolean(true)));
					tableFolderNode.add(tNode);
				}
			}
			//tableFolderNode.add(new DefaultMutableTreeNode(new Boolean(true)));
			sNode.add(tableFolderNode);
			dbNode.add(sNode);
		}
		return dbNode;
	}

	public String getToolTipText(MouseEvent ev) {
		if (ev == null)
			return null;
		TreePath path = getPathForLocation(ev.getX(), ev.getY());
		if (path != null) {
			DatabaseNode<?> dbNode = getDatabaseNode(getTreeNode(path));
			if (dbNode == null)
				return null;
			return dbNode.toString();
		}
		return null;
	}

	public DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	public <T> DatabaseNode<T> getDatabaseNode(DefaultMutableTreeNode node) {
		if (node == null)
			return null;
		Object obj = node.getUserObject();
		if (obj instanceof IconData)
			obj = ((IconData) obj).getObject();
		if (obj instanceof DatabaseNode){
			return (DatabaseNode<T>) obj;
		}
		else
			return null;
	}

	public JTextField getDisplayTextField() {
		return displayTextField;
	}

	public void setDisplayTextField(JTextField displayTextField) {
		this.displayTextField = displayTextField;
	}

	private DefaultMutableTreeNode topNode;
	private JTextField displayTextField;
	protected DefaultTreeModel defaultTreeModel;

	protected JPopupMenu m_popup;
	protected Action treeAction;
	protected TreePath clickedPath;


	private static String selectedPath = "";
	
	
	class DatabaseExpansionListener implements TreeExpansionListener{

		
		public void treeCollapsed(TreeExpansionEvent event) {
			
		}

		
		public void treeExpanded(TreeExpansionEvent event) {
			final DefaultMutableTreeNode node = getTreeNode(
	                event.getPath());
			final DatabaseNode<Object> databaseNode = getDatabaseNode(node);
			Thread runner = new Thread() {
				public void run() {
					if (databaseNode != null && databaseNode.expand(node)) {
						Runnable runnable = new Runnable() {
							public void run() {
								defaultTreeModel.reload(node);
								updateUI();
							}
						};
						SwingUtilities.invokeLater(runnable);
					}
				}
			};
            
            runner.start();
		}
		
	}


	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	
	

}
