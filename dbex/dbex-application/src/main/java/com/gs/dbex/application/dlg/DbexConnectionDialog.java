/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

import com.gs.dbex.application.comps.CollectionListModel;
import com.gs.dbex.application.connection.driver.JdbcDriverManagerDialog;
import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.constants.GuiCommandConstants;
import com.gs.dbex.application.context.ApplicationCommonContext;
import com.gs.dbex.application.event.ApplicationEventHandler;
import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.common.enums.DatabaseStorageTypeEnum;
import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.historyMgr.ApplicationDataHistoryMgr;
import com.gs.dbex.historyMgr.DbexHistoryMgrBeanFactory;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.swing.display.DisplayUtils;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class DbexConnectionDialog extends JDialog 
implements ActionListener, ListSelectionListener, PropertyChangeListener, KeyListener, WindowListener {

	/**
	 * 	Generated :: serialVersionUID = 1803937201488762305L
	 */
	private static final long serialVersionUID = 1803937201488762305L;
	
	private static final Logger logger = Logger.getLogger(DbexConnectionDialog.class);
	private static final DbexCommonContext dbexCommonContext = DbexCommonContext.getInstance();
	private static final ApplicationCommonContext applicationCommonContext = ApplicationCommonContext.getInstance();
	
	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	private Frame parentFrame;
	private ConnectionProperties connectionProperties;
	
    private JToolBar actionToolBar;
    private ButtonGroup schemaCatalogButtonGroup;
    private JButton cancelButton;
    private JRadioButton catalogRadioButton;
    private JButton clearButton;
    private JButton connectButton;
    private JLabel connectionNameLabel;
    private JList connectionNameList;
    private JComboBox dbTypeComboBox;
    private JButton deleteButton;
    private JTextField driverClassTextField;
    private JButton driverMgrButton;
    private JToggleButton editUrlToggleButton;
    private JButton exportAllButton;
    private JPanel fieldValuePanel;
    private JTextField hostNameTextField;
    private JLabel jLabel1;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JToolBar.Separator jSeparator1;
    private JToolBar.Separator jSeparator2;
    private JToolBar.Separator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JButton loadConnectionsButton;
    private JButton moveDownButton;
    private JButton moveUpButton;
    private JButton newConnectionButton;
    private JPasswordField passwordPasswordField;
    private JFormattedTextField portNumberFormattedTextField;
    private JButton saveAllButton;
    private JButton saveAsButton;
    private JButton saveButton;
    private JCheckBox savePasswordCheckBox;
    private JLabel schemaLabel;
    private JTextField schemaNameTextField;
    private JRadioButton schemaRadioButton;
    private JLabel sidLabel;
    private JTextField sidTextField;
    private JButton testConnectionButton;
    private JTextField urlTextField;
    private JTextField userNameTextField;
	
    /** Creates new form DbexConnectionDialog */
    public DbexConnectionDialog(Frame parent, boolean modal) {
        super(parent, modal);
        parentFrame = parent;
        initComponents();
        populateInitialData();
        databaseTypeChanged();
    }

    public int showDialog(){
    	logger.info("Opening Connection Dialog");
    	setVisible(true);
    	return selectedOption;
    }
    
    private void populateInitialData(){
    	List<ConnectionProperties> pl = new ArrayList<ConnectionProperties>();
    	if(applicationCommonContext.getConnectionPropertiesMap() != null && applicationCommonContext.getConnectionPropertiesMap().size() >0){
    		Collection<ConnectionProperties> l = applicationCommonContext.getConnectionPropertiesMap().values();
    		pl.addAll(l);
    	}
    	Collections.sort(pl);
    	CollectionListModel<ConnectionProperties> model = new CollectionListModel<ConnectionProperties>(pl);
    	connectionNameList.setModel(model);
    	connectionNameList.setSelectedIndex(0);
    	connectionNameList.updateUI();
    	
    }
    
    public int getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}

	public Frame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(Frame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        schemaCatalogButtonGroup = new ButtonGroup();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        connectionNameList = new JList();
        actionToolBar = new JToolBar();
        newConnectionButton = new JButton();
        loadConnectionsButton = new JButton();
        jSeparator1 = new JToolBar.Separator();
        saveButton = new JButton();
        saveAsButton = new JButton();
        saveAllButton = new JButton();
        jSeparator2 = new JToolBar.Separator();
        clearButton = new JButton();
        deleteButton = new JButton();
        jSeparator3 = new JToolBar.Separator();
        exportAllButton = new JButton();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        connectionNameLabel = new JLabel();
        jLabel4 = new JLabel();
        dbTypeComboBox = new JComboBox();
        jLabel5 = new JLabel();
        driverClassTextField = new JTextField();
        driverMgrButton = new JButton();
        jSeparator4 = new JSeparator();
        fieldValuePanel = new JPanel();
        jLabel3 = new JLabel();
        hostNameTextField = new JTextField();
        jLabel7 = new JLabel();
        portNumberFormattedTextField = new JFormattedTextField();
        jLabel8 = new JLabel();
        userNameTextField = new JTextField();
        jLabel9 = new JLabel();
        passwordPasswordField = new JPasswordField();
        savePasswordCheckBox = new JCheckBox();
        schemaLabel = new JLabel();
        schemaNameTextField = new JTextField();
        sidLabel = new JLabel();
        sidTextField = new JTextField();
        jLabel12 = new JLabel();
        schemaRadioButton = new JRadioButton();
        catalogRadioButton = new JRadioButton();
        jLabel13 = new JLabel();
        jSeparator5 = new JSeparator();
        jLabel6 = new JLabel();
        urlTextField = new JTextField();
        editUrlToggleButton = new JToggleButton();
        moveUpButton = new JButton();
        moveDownButton = new JButton();
        cancelButton = new JButton();
        testConnectionButton = new JButton();
        connectButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(this);
        setTitle("Connect");
        setMinimumSize(new Dimension(640, 450));
        setPreferredSize(getMinimumSize());

        jPanel1.setName("jPanel1"); 
        jPanel1.setLayout(new GridBagLayout());

        jLabel1.setFont(new Font("Tahoma", 0, 16));
        jLabel1.setForeground(new Color(0, 51, 255));
        jLabel1.setText("Connection Names");
        jLabel1.setName("jLabel1"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(jLabel1, gridBagConstraints);

        jScrollPane1.setName("jScrollPane1"); 

        
        connectionNameList.setName("connectionNameList"); 
        connectionNameList.addListSelectionListener(this);
        connectionNameList.addPropertyChangeListener(this);
        connectionNameList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(connectionNameList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        actionToolBar.setFloatable(false);
        actionToolBar.setRollover(true);
        actionToolBar.setName("actionToolBar"); 

        newConnectionButton.setIcon(new ImageIcon(getClass().getResource("/images/new_connection.gif"))); 
        newConnectionButton.setToolTipText("New Connection");
        newConnectionButton.setFocusable(false);
        newConnectionButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newConnectionButton.setName("newConnectionButton"); 
        newConnectionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newConnectionButton.addActionListener(this);
        actionToolBar.add(newConnectionButton);

        loadConnectionsButton.setIcon(new ImageIcon(getClass().getResource("/images/go-bottom.png"))); 
        loadConnectionsButton.setToolTipText("Load Connections");
        loadConnectionsButton.setFocusable(false);
        loadConnectionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        loadConnectionsButton.setName("loadConnectionsButton"); 
        loadConnectionsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        loadConnectionsButton.addActionListener(this);
        actionToolBar.add(loadConnectionsButton);

        jSeparator1.setName("jSeparator1"); 
        actionToolBar.add(jSeparator1);

        saveButton.setIcon(new ImageIcon(getClass().getResource("/images/save_edit.gif"))); 
        saveButton.setToolTipText("Save");
        saveButton.setEnabled(false);
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveButton.setName("saveButton"); 
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveButton.addActionListener(this);
        actionToolBar.add(saveButton);

        saveAsButton.setIcon(new ImageIcon(getClass().getResource("/images/saveas_edit.gif"))); 
        saveAsButton.setToolTipText("Save As...");
        saveAsButton.setFocusable(false);
        saveAsButton.setEnabled(false);
        saveAsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveAsButton.setName("saveAsButton"); 
        saveAsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveAsButton.addActionListener(this);
        actionToolBar.add(saveAsButton);

        saveAllButton.setIcon(new ImageIcon(getClass().getResource("/images/saveall_edit.gif"))); 
        saveAllButton.setToolTipText("Save All...");
        saveAllButton.setFocusable(false);
        saveAllButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveAllButton.setName("saveAllButton"); 
        saveAllButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveAllButton.addActionListener(this);
        actionToolBar.add(saveAllButton);

        jSeparator2.setName("jSeparator2"); 
        actionToolBar.add(jSeparator2);

        clearButton.setIcon(new ImageIcon(getClass().getResource("/images/edit-clear.png"))); 
        clearButton.setToolTipText("Clear");
        clearButton.setFocusable(false);
        clearButton.setEnabled(false);
        clearButton.setHorizontalTextPosition(SwingConstants.CENTER);
        clearButton.setName("clearButton"); 
        clearButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        clearButton.addActionListener(this);
        actionToolBar.add(clearButton);

        deleteButton.setIcon(new ImageIcon(getClass().getResource("/images/delete_edit.gif"))); 
        deleteButton.setToolTipText("Delete");
        deleteButton.setFocusable(false);
        deleteButton.setEnabled(false);
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setName("deleteButton"); 
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteButton.addActionListener(this);
        actionToolBar.add(deleteButton);

        jSeparator3.setName("jSeparator3"); 
        actionToolBar.add(jSeparator3);

        exportAllButton.setIcon(new ImageIcon(getClass().getResource("/images/export_wiz.gif"))); 
        exportAllButton.setToolTipText("Export All to XML");
        exportAllButton.setFocusable(false);
        exportAllButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exportAllButton.setName("exportAllButton"); 
        exportAllButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        exportAllButton.addActionListener(this);
        actionToolBar.add(exportAllButton);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(actionToolBar, gridBagConstraints);

        jPanel2.setName("jPanel2"); 
        jPanel2.setLayout(new GridBagLayout());

        jLabel2.setFont(new Font("Tahoma", 0, 14));
        jLabel2.setForeground(new Color(0, 51, 204));
        jLabel2.setText("Connection Details  ");
        jLabel2.setName("jLabel2"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(jLabel2, gridBagConstraints);

        connectionNameLabel.setText("${connName}");
        connectionNameLabel.setName("connectionNameLabel"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(connectionNameLabel, gridBagConstraints);

        jLabel4.setText("Database Type");
        jLabel4.setName("jLabel4"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(jLabel4, gridBagConstraints);

        dbTypeComboBox.setModel(new DefaultComboBoxModel(
        		new String[]{DatabaseTypeEnum.ORACLE.getDescription(),
                		DatabaseTypeEnum.MYSQL.getDescription(),
                		DatabaseTypeEnum.MSSQL_2005.getDescription(),
                		DatabaseTypeEnum.OTHER.getDescription()}));
        dbTypeComboBox.setName("dbTypeComboBox"); 
        //dbTypeComboBox.addPropertyChangeListener(this);
        dbTypeComboBox.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(dbTypeComboBox, gridBagConstraints);

        jLabel5.setText("Driver Class Name");
        jLabel5.setName("jLabel5"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(jLabel5, gridBagConstraints);

        driverClassTextField.setName("driverClassTextField"); 
        driverClassTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(driverClassTextField, gridBagConstraints);

        driverMgrButton.setText("Driver Mgr...");
        driverMgrButton.setName("driverMgrButton"); 
        driverMgrButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(driverMgrButton, gridBagConstraints);

        jSeparator4.setName("jSeparator4"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 0, 2, 0);
        jPanel2.add(jSeparator4, gridBagConstraints);

        fieldValuePanel.setBorder(BorderFactory.createTitledBorder(
        		null, " Field Values ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
        		new Font("Tahoma", 0, 11), new Color(102, 102, 255))); 
        fieldValuePanel.setName("fieldValuePanel"); 
        fieldValuePanel.setLayout(new GridBagLayout());

        jLabel3.setText("Host Name");
        jLabel3.setName("jLabel3"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(jLabel3, gridBagConstraints);

        hostNameTextField.setName("hostNameTextField"); 
        hostNameTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(hostNameTextField, gridBagConstraints);

        jLabel7.setText("Port Number");
        jLabel7.setName("jLabel7"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(jLabel7, gridBagConstraints);

        portNumberFormattedTextField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("###0"))));
        portNumberFormattedTextField.setName("portNumberFormattedTextField"); 
        portNumberFormattedTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(portNumberFormattedTextField, gridBagConstraints);

        jLabel8.setText("User Name");
        jLabel8.setName("jLabel8"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(jLabel8, gridBagConstraints);

        userNameTextField.setName("userNameTextField"); 
        userNameTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(userNameTextField, gridBagConstraints);

        jLabel9.setText("Password");
        jLabel9.setName("jLabel9"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(jLabel9, gridBagConstraints);

        passwordPasswordField.setName("passwordPasswordField"); 
        passwordPasswordField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(passwordPasswordField, gridBagConstraints);

        savePasswordCheckBox.setText("Save Password");
        savePasswordCheckBox.setName("savePasswordCheckBox"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(savePasswordCheckBox, gridBagConstraints);

        schemaLabel.setText("Schema");
        schemaLabel.setName("schemaLabel"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(schemaLabel, gridBagConstraints);

        schemaNameTextField.setName("schemaNameTextField"); 
        schemaNameTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(schemaNameTextField, gridBagConstraints);

        sidLabel.setText("SID/Service Name");
        sidLabel.setName("sidLabel"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(sidLabel, gridBagConstraints);

        sidTextField.setName("sidTextField"); 
        sidTextField.addKeyListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(sidTextField, gridBagConstraints);

        jLabel12.setText("Storage Type");
        jLabel12.setName("jLabel12"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(jLabel12, gridBagConstraints);

        schemaCatalogButtonGroup.add(schemaRadioButton);
        schemaRadioButton.setSelected(true);
        schemaRadioButton.setText("SCHEMA");
        schemaRadioButton.setName("schemaRadioButton"); 
        schemaRadioButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(schemaRadioButton, gridBagConstraints);

        schemaCatalogButtonGroup.add(catalogRadioButton);
        catalogRadioButton.setText("CATALOG");
        catalogRadioButton.setName("catalogRadioButton"); 
        catalogRadioButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fieldValuePanel.add(catalogRadioButton, gridBagConstraints);

        jLabel13.setName("jLabel13"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        fieldValuePanel.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(fieldValuePanel, gridBagConstraints);

        jSeparator5.setName("jSeparator5"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 0);
        jPanel2.add(jSeparator5, gridBagConstraints);

        jLabel6.setText("Connection URL");
        jLabel6.setName("jLabel6"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(jLabel6, gridBagConstraints);

        urlTextField.setEditable(false);
        urlTextField.setName("urlTextField"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(urlTextField, gridBagConstraints);

        editUrlToggleButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.gif"))); 
        editUrlToggleButton.setText("Edit");
        editUrlToggleButton.setName("editUrlToggleButton"); 
        editUrlToggleButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(editUrlToggleButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(jPanel2, gridBagConstraints);

        moveUpButton.setIcon(new ImageIcon(getClass().getResource("/images/Stock Index Up.png"))); 
        moveUpButton.setName("moveUpButton"); 
        moveUpButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(moveUpButton, gridBagConstraints);

        moveDownButton.setIcon(new ImageIcon(getClass().getResource("/images/go-down.png"))); 
        moveDownButton.setName("moveDownButton"); 
        moveDownButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(moveDownButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.setName("cancelButton"); 
        cancelButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(cancelButton, gridBagConstraints);

        testConnectionButton.setText("Test");
        testConnectionButton.setName("testConnectionButton"); 
        testConnectionButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(testConnectionButton, gridBagConstraints);

        connectButton.setText("Connect");
        connectButton.setName("connectButton"); 
        connectButton.addActionListener(this);
        connectButton.setActionCommand(GuiCommandConstants.CREATE_CONNECTION_ACT_CMD);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(connectButton, gridBagConstraints);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == newConnectionButton) {
            DbexConnectionDialog.this.newConnectionButtonActionPerformed(evt);
        }
        else if (evt.getSource() == loadConnectionsButton) {
            DbexConnectionDialog.this.loadConnectionsButtonActionPerformed(evt);
        }
        else if (evt.getSource() == saveButton) {
            DbexConnectionDialog.this.saveButtonActionPerformed(evt);
        }
        else if (evt.getSource() == saveAsButton) {
            DbexConnectionDialog.this.saveAsButtonActionPerformed(evt);
        }
        else if (evt.getSource() == saveAllButton) {
            DbexConnectionDialog.this.saveAllButtonActionPerformed(evt);
        }
        else if (evt.getSource() == deleteButton) {
            DbexConnectionDialog.this.deleteButtonActionPerformed(evt);
        }
        else if (evt.getSource() == clearButton) {
            DbexConnectionDialog.this.clearButtonActionPerformed(evt);
        }
        else if (evt.getSource() == exportAllButton) {
            DbexConnectionDialog.this.exportAllButtonActionPerformed(evt);
        }
        else if (evt.getSource() == driverMgrButton) {
            DbexConnectionDialog.this.driverMgrButtonActionPerformed(evt);
        }
        else if (evt.getSource() == schemaRadioButton) {
            DbexConnectionDialog.this.schemaRadioButtonActionPerformed(evt);
        }
        else if (evt.getSource() == catalogRadioButton) {
            DbexConnectionDialog.this.catalogRadioButtonActionPerformed(evt);
        }
        else if (evt.getSource() == editUrlToggleButton) {
            DbexConnectionDialog.this.editUrlToggleButtonActionPerformed(evt);
        }
        else if (evt.getSource() == moveUpButton) {
            DbexConnectionDialog.this.moveUpButtonActionPerformed(evt);
        }
        else if (evt.getSource() == moveDownButton) {
            DbexConnectionDialog.this.moveDownButtonActionPerformed(evt);
        }
        else if (evt.getSource() == cancelButton) {
            DbexConnectionDialog.this.cancelButtonActionPerformed(evt);
        }
        else if (evt.getSource() == testConnectionButton) {
            DbexConnectionDialog.this.testConnectionButtonActionPerformed(evt);
        }
        else if (evt.getSource() == connectButton) {
            DbexConnectionDialog.this.connectButtonActionPerformed(evt);
        }
        else if (evt.getSource() == dbTypeComboBox) {
            DbexConnectionDialog.this.dbTypeComboBoxActionPerformed(evt);
        }
        else if (evt.getSource() == savePasswordCheckBox) {
            DbexConnectionDialog.this.savePasswordCheckBoxActionPerformed(evt);
        }
    }


	

	public void keyPressed(KeyEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
        if (evt.getSource() == hostNameTextField) {
            DbexConnectionDialog.this.hostNameTextFieldKeyReleased(evt);
        }
        else if (evt.getSource() == portNumberFormattedTextField) {
            DbexConnectionDialog.this.portNumberFormattedTextFieldKeyReleased(evt);
        }
        else if (evt.getSource() == userNameTextField) {
            DbexConnectionDialog.this.userNameTextFieldKeyReleased(evt);
        }
        else if (evt.getSource() == passwordPasswordField) {
            DbexConnectionDialog.this.passwordPasswordFieldKeyReleased(evt);
        }
        else if (evt.getSource() == schemaNameTextField) {
            DbexConnectionDialog.this.schemaNameTextFieldKeyReleased(evt);
        }
        else if (evt.getSource() == sidTextField) {
            DbexConnectionDialog.this.sidTextFieldKeyReleased(evt);
        }
        else if (evt.getSource() == driverClassTextField) {
            DbexConnectionDialog.this.driverClassTextFieldKeyReleased(evt);
        }
    }

    

	public void keyTyped(KeyEvent evt) {
    }

    public void windowActivated(WindowEvent evt) {
    }

    public void windowClosed(WindowEvent evt) {
    }

    public void windowClosing(WindowEvent evt) {
        if (evt.getSource() == DbexConnectionDialog.this) {
            DbexConnectionDialog.this.formWindowClosing(evt);
        }
    }

    public void windowDeactivated(WindowEvent evt) {
    }

    public void windowDeiconified(WindowEvent evt) {
    }

    public void windowIconified(WindowEvent evt) {
    }

    public void windowOpened(WindowEvent evt) {
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == connectionNameList) {
            DbexConnectionDialog.this.connectionNameListPropertyChange(evt);
        }
        else if (evt.getSource() == dbTypeComboBox) {
            DbexConnectionDialog.this.dbTypeComboBoxPropertyChange(evt);
        }
    }

    public void valueChanged(ListSelectionEvent evt) {
        if (evt.getSource() == connectionNameList) {
            DbexConnectionDialog.this.connectionNameListValueChanged(evt);
        }
    }

	private void cancelButtonActionPerformed(ActionEvent evt) {                                             
		selectedOption = ApplicationConstants.CANCEL_OPTION;
    	dispose();
	}                                            

	private void connectionNameListValueChanged(ListSelectionEvent evt) {                                                
		ConnectionProperties p = (ConnectionProperties) connectionNameList.getSelectedValue();
		if(p != null){
			populateFromProperties(p);
			changeButtonEnabled(p);
		}
	}                                               

	private void connectionNameListPropertyChange(PropertyChangeEvent evt) {                                                  
		
	}                                                 

	private void newConnectionButtonActionPerformed(ActionEvent evt) {                                                    
		String connectionName = DisplayUtils.readString(this,"Connection Name");
		if(StringUtil.hasValidContent(connectionName)){
			ConnectionProperties p = new ConnectionProperties();
			p.setConnectionName(connectionName);
			p.setDisplayOrder(connectionNameList.getModel().getSize()+1);
			p.setPropertySaved(false);
			((CollectionListModel<ConnectionProperties>)connectionNameList.getModel()).addElement(p);
			connectionNameList.setSelectedIndex(connectionNameList.getModel().getSize()-1);
			connectionNameList.updateUI();
			applicationCommonContext.getConnectionPropertiesMap().put(p.getConnectionName(), p);
			changeButtonEnabled(p);
		} else {
			DisplayUtils.displayMessage(this,"Invalid Connection Name");
		}
	}                                                   

	private void loadConnectionsButtonActionPerformed(ActionEvent evt) {                                                      
		// TODO add your handling code here:
	}                                                     

	private void saveButtonActionPerformed(ActionEvent evt) {                                           
		ConnectionProperties p = populateToProperties();
		if(p != null){
			applicationCommonContext.getConnectionPropertiesMap().put(p.getConnectionName(), p);
			p.setPropertySaved(true);
			changeButtonEnabled(p);
		}
	}                                          

	private void saveAsButtonActionPerformed(ActionEvent evt) {                                             
		ConnectionProperties p = populateToProperties();
		if(p != null){
			applicationCommonContext.getConnectionPropertiesMap().put(p.getConnectionName(), p);
			p.setPropertySaved(true);
			changeButtonEnabled(p);
		}
	}                                            

	private void saveAllButtonActionPerformed(ActionEvent evt) {                                              
		saveAllConnectionProperties();
	}                                             

	private void deleteButtonActionPerformed(ActionEvent evt) {                                             
		// TODO add your handling code here:
	}                                            

	private void clearButtonActionPerformed(ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void exportAllButtonActionPerformed(ActionEvent evt) {                                                
		// TODO add your handling code here:
	}                                               

	private void dbTypeComboBoxActionPerformed(ActionEvent evt) {
		databaseTypeChanged();
	}
	
	private void savePasswordCheckBoxActionPerformed(ActionEvent evt) {
		connectionPropertiesModified();
	}
	
	private void databaseTypeChanged(){
		String databaseName = dbTypeComboBox.getSelectedItem().toString();
		if(DatabaseTypeEnum.ORACLE.getDescription().equals(databaseName)){
			driverClassTextField.setText(ApplicationConstants.ORACLE_DRIVER_NAME);
			driverClassTextField.setEditable(false);
			urlTextField.setText(ApplicationConstants.ORACLE_CONNECTION_URL_PATTERN);
			schemaRadioButton.setSelected(true);
			catalogRadioButton.setSelected(false);
			schemaRadioButton.setEnabled(false);
			catalogRadioButton.setEnabled(false);
			sidTextField.setVisible(true);
			sidLabel.setVisible(true);
			if(schemaRadioButton.isSelected()){
				schemaLabel.setText("Schema Name");
			} else {
				schemaLabel.setText("Catalog Name");
			}
		}
		else if(DatabaseTypeEnum.MYSQL.getDescription().equals(databaseName)){
			driverClassTextField.setText(ApplicationConstants.MYSQL_DRIVER_NAME);
			driverClassTextField.setEditable(false);
			urlTextField.setText(ApplicationConstants.MYSQL_CONNECTION_URL_PATTERN);
			schemaRadioButton.setSelected(false);
			catalogRadioButton.setSelected(true);
			schemaRadioButton.setEnabled(false);
			catalogRadioButton.setEnabled(false);
			sidTextField.setVisible(false);
			sidLabel.setVisible(false);
			if(catalogRadioButton.isSelected()){
				schemaLabel.setText("Catalog Name");
			} else {
				schemaLabel.setText("Schema Name");
			}
		}
		else if(DatabaseTypeEnum.MSSQL_2005.getDescription().equals(databaseName)){
			driverClassTextField.setText(ApplicationConstants.MSSQL_DRIVER_NAME);
			driverClassTextField.setEditable(false);
			urlTextField.setText(ApplicationConstants.MSSQL_CONNECTION_URL_PATTERN);
			schemaRadioButton.setSelected(false);
			catalogRadioButton.setSelected(true);
			schemaRadioButton.setEnabled(false);
			catalogRadioButton.setEnabled(false);
			sidTextField.setVisible(false);
			sidLabel.setVisible(false);
			if(catalogRadioButton.isSelected()){
				schemaLabel.setText("Catalog Name");
			} else {
				schemaLabel.setText("Schema Name");
			}
		}
		else {
			driverClassTextField.setText("");
			driverClassTextField.setEditable(true);
			urlTextField.setText("");
			schemaRadioButton.setSelected(true);
			catalogRadioButton.setSelected(false);
			schemaRadioButton.setEnabled(true);
			catalogRadioButton.setEnabled(true);
			sidTextField.setVisible(true);
			sidLabel.setVisible(true);
			if(schemaRadioButton.isSelected()){
				schemaLabel.setText("Schema Name");
			} else {
				schemaLabel.setText("Catalog Name");
			}
		}
		connectionPropertiesModified();
	}
	
	private void dbTypeComboBoxPropertyChange(PropertyChangeEvent evt) {                                              
		
	}                                             

	private void driverMgrButtonActionPerformed(ActionEvent evt) {                                                
		JdbcDriverManagerDialog driverManagerDialog = new JdbcDriverManagerDialog(parentFrame, true);
		int option = driverManagerDialog.showDialog();
		if(option == ApplicationConstants.APPLY_OPTION){
			System.out.println("OK");
			connectionPropertiesModified();
		}
	}                                               

	private void moveUpButtonActionPerformed(ActionEvent evt) {                                             
		// TODO add your handling code here:
	}                                            

	private void moveDownButtonActionPerformed(ActionEvent evt) {                                               
		// TODO add your handling code here:
	}                                              

	private void editUrlToggleButtonActionPerformed(ActionEvent evt) {                                                    
		if(editUrlToggleButton.isSelected()){
			urlTextField.setEditable(true);
			enableFileds(false);
		} else if(!editUrlToggleButton.isSelected()){
			urlTextField.setEditable(false);
			enableFileds(true);
		}
	}                                                   

	private void enableFileds(boolean b) {
		hostNameTextField.setEnabled(b);
		portNumberFormattedTextField.setEnabled(b);
		userNameTextField.setEnabled(b);
		passwordPasswordField.setEnabled(b);
		schemaNameTextField.setEnabled(b);
		sidTextField.setEnabled(b);
	}

	private void testConnectionButtonActionPerformed(ActionEvent evt) {                                                     
		// TODO add your handling code here:
	}                                                    

	private void connectButtonActionPerformed(ActionEvent evt) {                                              
		ConnectionProperties properties = populateToProperties();
		if(properties != null){
			ApplicationEventHandler handler = new ApplicationEventHandler();
			handler.setParent(getParent());
			handler.setSourceForm(this);
			handler.setData(properties);
			handler.actionPerformed(evt);
			selectedOption = ApplicationConstants.APPLY_OPTION;
	        dispose();
		}
	}                                             

	private void schemaRadioButtonActionPerformed(ActionEvent evt) {                                                  
		if(schemaRadioButton.isSelected()){
			schemaLabel.setText("Schema Name");
		} else {
			schemaLabel.setText("Catalog Name");
		}
		connectionPropertiesModified();
	}                                                 

	private void catalogRadioButtonActionPerformed(ActionEvent evt) {                                                   
		if(catalogRadioButton.isSelected()){
			schemaLabel.setText("Catalog Name");
		} else {
			schemaLabel.setText("Schema Name");
		}
		connectionPropertiesModified();
	}                                                  

	private void hostNameTextFieldKeyReleased(KeyEvent evt) {                                              
		connectionPropertiesModified();
	}                                             

	private void portNumberFormattedTextFieldKeyReleased(KeyEvent evt) {                                                         
		connectionPropertiesModified();
	}                                                        

	private void userNameTextFieldKeyReleased(KeyEvent evt) {                                              
		connectionPropertiesModified();
	}                                             

	private void passwordPasswordFieldKeyReleased(KeyEvent evt) {                                                  
		connectionPropertiesModified();
	}                                                 

	private void schemaNameTextFieldKeyReleased(KeyEvent evt) {                                                
		connectionPropertiesModified();
	}                                               

	private void sidTextFieldKeyReleased(KeyEvent evt) {                                         
		connectionPropertiesModified();
	}
	
	private void driverClassTextFieldKeyReleased(KeyEvent evt) {
		connectionPropertiesModified();
	}
	
	private void formWindowClosing(WindowEvent evt) {
		selectedOption = ApplicationConstants.CANCEL_OPTION;
		logger.info("Closing Connection Dialog");
	}

	private void saveAllConnectionProperties(){
		ApplicationDataHistoryMgr dataHistoryMgr = DbexHistoryMgrBeanFactory.getInstance().getApplicationDataHistoryMgr();
		Collection<ConnectionProperties> list = applicationCommonContext.getConnectionPropertiesMap().values();
		List<ConnectionProperties> ps = new ArrayList<ConnectionProperties>();
		ps.addAll(list);
		boolean b = dataHistoryMgr.saveAllConnectionProperties(ps);
		if(b){
			System.out.println("Saved");
		} else {
			System.out.println("NOT Saved");
		}
	}
	
	private ConnectionProperties populateToProperties(){
		ConnectionProperties p = (ConnectionProperties) connectionNameList.getSelectedValue();
		if(p == null){
			p = new ConnectionProperties();
			p.setDisplayOrder(applicationCommonContext.getConnectionPropertiesMap().size()+1);
		}
		p.setConnectionName(connectionNameLabel.getText());
		if(null != dbTypeComboBox.getSelectedItem()){
			p.setDatabaseType(DatabaseTypeEnum.getDatabaseTypeEnumByName(dbTypeComboBox.getSelectedItem().toString()).getCode());
		}
		p.setConnectionUrl(urlTextField.getText());
		p.getDatabaseConfiguration().setHostName(hostNameTextField.getText());
		if(StringUtil.hasValidContent(portNumberFormattedTextField.getText())){
			p.getDatabaseConfiguration().setPortNumber(Integer.valueOf(portNumberFormattedTextField.getText()));
		}
		p.getDatabaseConfiguration().setUserName(userNameTextField.getText());
		p.getDatabaseConfiguration().setDriverClassName(driverClassTextField.getText());
		p.getDatabaseConfiguration().setPassword(passwordPasswordField.getText());
		p.getDatabaseConfiguration().setSchemaName(schemaNameTextField.getText());
		p.getDatabaseConfiguration().setSidServiceName(sidTextField.getText());
		p.getDatabaseConfiguration().setSavePassword(savePasswordCheckBox.isSelected());
		if(schemaRadioButton.isSelected()){
			p.getDatabaseConfiguration().setStorageType(DatabaseStorageTypeEnum.SCHEMA_STORAGE.getCode());
		}
		else if(catalogRadioButton.isSelected()){
			p.getDatabaseConfiguration().setStorageType(DatabaseStorageTypeEnum.CATALOG_STORAGE.getCode());
		}
		else {
			p.getDatabaseConfiguration().setStorageType(DatabaseStorageTypeEnum.UNKNOWN.getCode());
		}
		
		
		return p;
	}
	
	private void populateFromProperties(ConnectionProperties p){
		if (null != p) {
			connectionNameLabel.setText(StringUtil.hasValidContent(p
					.getConnectionName()) ? p.getConnectionName()
					: dbexCommonContext.getDefaultHostName());
			dbTypeComboBox.setSelectedItem(StringUtil.hasValidContent(p
					.getDatabaseType()) ? p.getDatabaseType()
					: DatabaseTypeEnum.OTHER.getDescription());
			DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.getDatabaseTypeEnumByName(p.getDatabaseType());
			if (null != p.getDatabaseConfiguration()) {
				driverClassTextField.setText(StringUtil.hasValidContent(p
						.getDatabaseConfiguration().getDriverClassName()) ? p
						.getDatabaseConfiguration().getDriverClassName() : "");
				hostNameTextField.setText(StringUtil.hasValidContent(p
						.getDatabaseConfiguration().getHostName()) ? p
						.getDatabaseConfiguration().getHostName()
						: dbexCommonContext.getDefaultHostName());
				portNumberFormattedTextField
						.setText(""
								+ (null != p.getDatabaseConfiguration()
										.getPortNumber() ? p
										.getDatabaseConfiguration()
										.getPortNumber() : dbexCommonContext
										.getDefaultPortNumber()));
				userNameTextField.setText(StringUtil.hasValidContent(p
						.getDatabaseConfiguration().getUserName()) ? p
						.getDatabaseConfiguration().getUserName() : "");
				savePasswordCheckBox.setSelected(p.getDatabaseConfiguration().isSavePassword());
				if(p.getDatabaseConfiguration().isSavePassword()){
					passwordPasswordField.setText(StringUtil.hasValidContent(p
							.getDatabaseConfiguration().getPassword()) ? p
							.getDatabaseConfiguration().getPassword() : "");
				} else {
					passwordPasswordField.setText("");
				}
				if(DatabaseTypeEnum.OTHER.equals(databaseTypeEnum)){
					if(DatabaseStorageTypeEnum.SCHEMA_STORAGE.getCode().equals(p.getDatabaseConfiguration().getStorageType())){
						schemaRadioButton.setSelected(true);
						catalogRadioButton.setSelected(false);
					} else if(DatabaseStorageTypeEnum.CATALOG_STORAGE.getCode().equals(p.getDatabaseConfiguration().getStorageType())){
						schemaRadioButton.setSelected(false);
						catalogRadioButton.setSelected(true);
					} else {
						schemaRadioButton.setSelected(false);
						catalogRadioButton.setSelected(false);
					}
				}
				sidTextField.setText(StringUtil.hasValidContent(p
						.getDatabaseConfiguration().getSidServiceName()) ? p
						.getDatabaseConfiguration().getSidServiceName() : "");
			}

		}
	}
	
	private void changeButtonEnabled(ConnectionProperties properties){
		if(connectionNameList.getSelectedIndex() >= 0){ // something is selected
			saveButton.setEnabled(true && !properties.isPropertySaved());
			saveAsButton.setEnabled(true);
			deleteButton.setEnabled(true);
			clearButton.setEnabled(true);
		} else {
			saveButton.setEnabled(false);
			saveAsButton.setEnabled(false);
			deleteButton.setEnabled(false);
			clearButton.setEnabled(false);
		}
	}
	
	private void connectionPropertiesModified(){
		ConnectionProperties p = (ConnectionProperties) connectionNameList.getSelectedValue();
		if(p != null){
			p.setPropertySaved(false);
			changeButtonEnabled(p);
		}
	}
	
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                DbexConnectionDialog dialog = new DbexConnectionDialog(new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


}
