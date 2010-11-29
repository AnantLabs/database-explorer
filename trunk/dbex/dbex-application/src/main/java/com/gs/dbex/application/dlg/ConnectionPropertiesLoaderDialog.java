package com.gs.dbex.application.dlg;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.sql.processor.StringTokenizer;
import com.gs.dbex.application.tree.DatabaseDirectoryTree;
import com.gs.dbex.application.tree.cfg.ConnectionPropertyTreeNode;
import com.gs.dbex.integration.xmlbeans.ConnectionPropertiesXmlTransformer;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.collection.CollectionUtils;
import com.gs.utils.enums.DisplayTypeEnum;
import com.gs.utils.io.FileRWUtil;
import com.gs.utils.swing.display.DisplayUtils;
import com.gs.utils.swing.file.ExtensionFileFilter;
import com.gs.utils.swing.file.FileBrowserUtil;
import com.gs.utils.swing.tree.IconCellRenderer;
import com.gs.utils.swing.tree.IconData;
import com.gs.utils.swing.window.WindowUtil;
import com.gs.utils.text.StringUtil;

public class ConnectionPropertiesLoaderDialog extends JDialog implements ActionListener, TreeSelectionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5026939975969588968L;
	
	private static final ImageIcon ICON_ROOT = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(ApplicationConstants.IMAGE_PATH
					+ "Folder_table.png"));
	
	private static final ImageIcon ICON_FOLDER_CLOSED = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(ApplicationConstants.IMAGE_PATH
					+ "closedFolder.gif"));
	private static final ImageIcon ICON_FOLDER_OPEN = new ImageIcon(
			DatabaseDirectoryTree.class.getResource(ApplicationConstants.IMAGE_PATH
					+ "fldr_obj.gif"));
	private static final ImageIcon ICON_CONNECTION = new ImageIcon(
			DatabaseDirectoryTree.class
					.getResource(ApplicationConstants.IMAGE_PATH + "disconnect-16x16.png"));
	
	
	private Frame parentFrame;
	protected TreePath clickedPath;
	private final Map<String, List<ConnectionProperties>> fileConPropMap = new HashMap<String, List<ConnectionProperties>>();
	
    /** Creates new form ConnectionPropertiesLoaderDialog */
    public ConnectionPropertiesLoaderDialog(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Load Connection Properties");
        parentFrame = parent;
        initComponents();
        statusLabel.setVisible(false);
        statusProgressBar.setVisible(false);
        getRootPane().setDefaultButton(loadButton);
        WindowUtil.bringCenterTo(this, parent);
    }

    
    
    public Frame getParentFrame() {
		return parentFrame;
	}



	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        fileNameTextField = new JTextField();
        browseButton = new JButton();
        browseButton.addActionListener(this);
        statusLabel = new JLabel();
        statusProgressBar = new JProgressBar();
        jSeparator1 = new JSeparator();
        jLabel3 = new JLabel();
        jScrollPane1 = new JScrollPane();
        connectionPropertiesTree = new JTree();
        connectionPropertiesTree.addTreeSelectionListener(this);
        jLabel4 = new JLabel();
        jScrollPane2 = new JScrollPane();
        connectionPropertyTextArea = new JTextArea();
        reloadButton = new JButton();
        reloadButton.addActionListener(this);
        jSeparator2 = new JSeparator();
        cancelButton = new JButton();
        cancelButton.addActionListener(this);
        loadButton = new JButton();
        loadButton.addActionListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(627, 381));
        getContentPane().setLayout(new GridBagLayout());

        jLabel1.setText("File Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(fileNameTextField, gridBagConstraints);

        browseButton.setText("Browse");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(browseButton, gridBagConstraints);

        statusLabel.setText("Please Wait");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(statusLabel, gridBagConstraints);

        statusProgressBar.setMinimumSize(new Dimension(146, 19));
        statusProgressBar.setValue(0 );
		statusProgressBar.setMinimum(0);
		statusProgressBar.setMinimum(100);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(statusProgressBar, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 0);
        getContentPane().add(jSeparator1, gridBagConstraints);

        jLabel3.setText("Connection Properties");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(jLabel3, gridBagConstraints);

        connectionPropertiesTree.setForeground(new Color(0, 0, 204));
        connectionPropertiesTree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        //connectionPropertiesTree.setMaximumSize(new Dimension(62, 64));
        //connectionPropertiesTree.setPreferredSize(new Dimension(62, 64));
        connectionPropertiesTree.setMinimumSize(new Dimension(62, 64));
        connectionPropertiesTree.setModel(new DefaultTreeModel(null));
        connectionPropertiesTree.setAutoscrolls(true);
        TreeCellRenderer renderer = new IconCellRenderer();
        connectionPropertiesTree.setCellRenderer(renderer);
		//addTreeExpansionListener(new DatabaseExpansionListener());
		// addTreeSelectionListener(new DirSelectionListener());

		connectionPropertiesTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		connectionPropertiesTree.setShowsRootHandles(true);
		connectionPropertiesTree.setEditable(false);
        
        ToolTipManager.sharedInstance().registerComponent(connectionPropertiesTree);
		connectionPropertiesTree.putClientProperty("JTree.lineStyle", "Angled");
        jScrollPane1.setViewportView(connectionPropertiesTree);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel4.setText("Details");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(jLabel4, gridBagConstraints);

        connectionPropertyTextArea.setColumns(20);
        connectionPropertyTextArea.setEditable(false);
        connectionPropertyTextArea.setRows(5);
        connectionPropertyTextArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(connectionPropertyTextArea);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        reloadButton.setText("Reload");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(reloadButton, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 0);
        getContentPane().add(jSeparator2, gridBagConstraints);

        cancelButton.setText("Cancel");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(cancelButton, gridBagConstraints);

        loadButton.setText("Load");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(loadButton, gridBagConstraints);

        
        jLabel2.setMinimumSize(new Dimension(180, 0));
        jLabel2.setPreferredSize(new Dimension(180, 0));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(jLabel2, gridBagConstraints);
        
        pack();
    }

    

    // Variables declaration - do not modify
    private JButton browseButton;
    private JButton cancelButton;
    private JTree connectionPropertiesTree;
    private JTextArea connectionPropertyTextArea;
    private JTextField fileNameTextField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JButton loadButton;
    private JButton reloadButton;
    private JLabel statusLabel;
    private JProgressBar statusProgressBar;
    // End of variables declaration

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if(connectionPropertiesTree.equals(e.getSource())){
			if(null != connectionPropertiesTree.getSelectionPath() 
					&& null != connectionPropertiesTree.getSelectionPath().getLastPathComponent()){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) connectionPropertiesTree
						.getSelectionPath().getLastPathComponent();
				if (null != node) {
					Object obj = node.getUserObject();
					if (null != obj && obj instanceof IconData) {
						obj = ((IconData) obj).getObject();
						if (null != obj && obj instanceof ConnectionPropertyTreeNode) {
							connectionPropertyTextArea
									.setText(((ConnectionPropertyTreeNode) obj)
											.printFullData());
						}
					}
				}
			}
			
			
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(browseButton.equals(e.getSource())){
			browseAndLoadFile();
		} else if(reloadButton.equals(e.getSource())){
			String fileNames = fileNameTextField.getText();
			if(StringUtil.hasValidContent(fileNames)){
				List<File> files = new ArrayList<File>();
				java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(fileNames, "|");
				while(tokenizer.hasMoreTokens()){
					String fileName = tokenizer.nextToken();
					if(StringUtil.hasValidContent(fileName)){
						File file = new File(fileName);
						if(file.exists() && !file.isDirectory()){
							files.add(file);
						}
					}
				}
				if(files.size() > 0){
					populateFromFiles(CollectionUtils.toArray(files));
				}
			}
		} else if(loadButton.equals(e.getSource())){
			
		} else if(cancelButton.equals(e.getSource())){
			dispose();
		}
	}



	public void browseAndLoadFile() {
		ExtensionFileFilter filter = new ExtensionFileFilter(
				new String[]{"xml"}, "XML Files (.xml)");
		final File[] files = FileBrowserUtil.openMultipleFile(parentFrame, filter, false);
		populateFromFiles(files);
	}



	public void populateFromFiles(final File[] files) {
		if(null != files && files.length > 0){
			Runnable loadRunner = new Runnable() {
				
				@Override
				public void run() {
					statusProgressBar.setIndeterminate(true);
					fileConPropMap.clear();
					StringBuffer fileNames = new StringBuffer();
					WindowUtil.enableComponents(false, fileNameTextField, browseButton, reloadButton,
							connectionPropertiesTree, loadButton);
					WindowUtil.visibleComponents(true, statusLabel, statusProgressBar);
					for (int i = 0; i < files.length; i++) {
						File file = files[i];
						if(null == file)
							continue;
						String xmlText = FileRWUtil.getContents(file);
						List<ConnectionProperties> connectionProperties = 
							new ConnectionPropertiesXmlTransformer().readAllConnectionProperties(xmlText);
						if(null != connectionProperties && connectionProperties.size() > 0){
							fileConPropMap.put(file.getAbsolutePath(), connectionProperties);
							fileNames.append(file.getAbsolutePath());
							if(i < files.length -1){
								fileNames.append("|");
							}
						} else {
							DisplayUtils.displayMessage(parentFrame, "File [ " + file.getAbsolutePath() + " ] \n" +
									"contains invalid xml format.", DisplayTypeEnum.ERROR);
						}
					}
					fileNameTextField.setText(fileNames.toString());
					populateTree(fileConPropMap);
					WindowUtil.enableComponents(true, fileNameTextField, browseButton, reloadButton,
							connectionPropertiesTree, loadButton);
					WindowUtil.visibleComponents(false, statusLabel, statusProgressBar);
				}

				private void populateTree(
						Map<String, List<ConnectionProperties>> fileConPropMap) {
					connectionPropertiesTree.setModel(new DefaultTreeModel(null));
					Set<String> keys = fileConPropMap.keySet();
					
					
					DefaultMutableTreeNode rootFolderNode = new DefaultMutableTreeNode(
							new IconData(ICON_ROOT, ICON_ROOT, "Connection Configurations"));
					for (String key : keys) {
						List<ConnectionProperties> properties = fileConPropMap.get(key);
						DefaultMutableTreeNode connFolderNode = new DefaultMutableTreeNode(
								new IconData(ICON_FOLDER_CLOSED, ICON_FOLDER_OPEN, key));
						for (ConnectionProperties connectionProperties : properties) {
							ConnectionPropertyTreeNode cptn = new ConnectionPropertyTreeNode(connectionProperties);
							DefaultMutableTreeNode connPropNode = new DefaultMutableTreeNode(
									new IconData(ICON_CONNECTION, null, cptn));
							connFolderNode.add(connPropNode);
						}
						rootFolderNode.add(connFolderNode);
					}
					
					DefaultTreeModel treeModel = new DefaultTreeModel(rootFolderNode);
					connectionPropertiesTree.setModel(treeModel);
					//connectionPropertiesTree.updateUI();
				}
			};
			
			new Thread(loadRunner).start();
		}
	}

	
}
