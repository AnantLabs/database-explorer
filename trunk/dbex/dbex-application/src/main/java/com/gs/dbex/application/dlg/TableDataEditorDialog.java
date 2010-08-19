package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import com.gs.dbex.application.accesscontrol.AuthorizationController;
import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.panel.TableRowEditorPanel;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.text.StringUtil;

/**
 * 
 * @author sabuj.das
 */
public class TableDataEditorDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 280459765585383490L;
	
	private static final AuthorizationController authorizationController = AuthorizationController.getInstance();
	
	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	private JFrame parentComponent;	
	private JTable table;
	private ConnectionProperties connectionProperties;
	private String schemaName, tableName;
	private String pkColumnName;
	private Object pkColumnValue;
	
	private JButton cancelButton;
	private JPanel dataEditorPanel;
	private JScrollPane dataEditorScrollPane;
	private JTabbedPane dataEditorTabbedPane;
	private JPanel generatedSqlPanel;
	private JTextPane generatedSqlTextPane;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JScrollPane jScrollPane2;
	private JTextField schemaNameTextField;
	private JTextField tableNameTextField;
	private JPanel mainPanel;
	private JButton okButton;
	
	private TableRowEditorPanel rowEditorPanel;


	/** Creates new form TableDataEditorDialog */
	public TableDataEditorDialog(JFrame parent, final JTable t) {
		super(parent, true);
		parentComponent = parent;
		table = t;
		initComponents();
		setMinimumSize(new Dimension(400, 300));
		setPreferredSize(getMinimumSize());
		getRootPane().setDefaultButton(okButton);
		if(null == pkColumnValue){
			setTitle("Add Record");
		} else {
			setTitle("Edit Record");
		}
	}
	
	public int showEditorDialog(){
		rowEditorPanel = new TableRowEditorPanel(getTable(), getSchemaName(), getTableName(), getConnectionProperties());
		dataEditorScrollPane.setViewportView(rowEditorPanel);
		setVisible(true);
        return selectedOption;
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		mainPanel = new JPanel();
		jLabel1 = new JLabel();
		schemaNameTextField = new JTextField();
		jLabel2 = new JLabel();
		tableNameTextField = new JTextField();
		dataEditorPanel = new JPanel();
		dataEditorTabbedPane = new JTabbedPane();
		dataEditorScrollPane = new JScrollPane();
		generatedSqlPanel = new JPanel();
		jScrollPane2 = new JScrollPane();
		generatedSqlTextPane = new JTextPane();
		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		okButton = new JButton();
		okButton.addActionListener(this);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		mainPanel.setLayout(new GridBagLayout());

		jLabel1.setFont(new Font("Tahoma", 1, 11)); 
		jLabel1.setText("Schema : ");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(2, 2, 0, 0);
		mainPanel.add(jLabel1, gridBagConstraints);

		schemaNameTextField.setBackground(new Color(255, 255, 255));
		schemaNameTextField.setEditable(false);
		schemaNameTextField.setForeground(new Color(0, 51, 204));
		schemaNameTextField.setText(StringUtil.hasValidContent(schemaName) ? schemaName : "");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(2, 0, 0, 2);
		mainPanel.add(schemaNameTextField, gridBagConstraints);

		jLabel2.setFont(new Font("Tahoma", 1, 11)); 
		jLabel2.setText("Table : ");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(2, 2, 0, 0);
		mainPanel.add(jLabel2, gridBagConstraints);

		tableNameTextField.setBackground(new Color(255, 255, 255));
		tableNameTextField.setText(StringUtil.hasValidContent(tableName) ? tableName : "");
		tableNameTextField.setEditable(false);
		tableNameTextField.setForeground(new Color(0, 51, 204));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(2, 0, 0, 2);
		mainPanel.add(tableNameTextField, gridBagConstraints);

		dataEditorPanel.setLayout(new BorderLayout());
		
		

		dataEditorTabbedPane.addTab("DATA", dataEditorScrollPane);

		generatedSqlPanel.setLayout(new BorderLayout());

		generatedSqlTextPane.setEditable(false);
		generatedSqlTextPane.setMargin(new Insets(5, 5, 5, 5));
		jScrollPane2.setViewportView(generatedSqlTextPane);

		generatedSqlPanel.add(jScrollPane2, BorderLayout.CENTER);

		dataEditorTabbedPane.addTab("SQL", generatedSqlPanel);

		dataEditorPanel.add(dataEditorTabbedPane, BorderLayout.CENTER);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new Insets(5, 2, 5, 2);
		mainPanel.add(dataEditorPanel, gridBagConstraints);

		cancelButton.setText("Cancel");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(2, 2, 2, 0);
		mainPanel.add(cancelButton, gridBagConstraints);

		okButton.setText("OK");
		okButton.setEnabled(authorizationController.hasAccess(null, ""));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(2, 0, 2, 2);
		mainPanel.add(okButton, gridBagConstraints);
		
		

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		addWindowListener(new WindowListener(){

			
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void windowClosing(WindowEvent e) {
				selectedOption = ApplicationConstants.CANCEL_OPTION;
			}

			
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		pack();
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(okButton)){
			setSelectedOption(ApplicationConstants.APPLY_OPTION);
			dispose();
		}else if(e.getSource().equals(cancelButton)){
			setSelectedOption(ApplicationConstants.CANCEL_OPTION);
			dispose();
		} 
	}
	
	/**
	 * @return the selectedOption
	 */
	public int getSelectedOption() {
		return selectedOption;
	}

	/**
	 * @return the parentComponent
	 */
	public JFrame getParentComponent() {
		return parentComponent;
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @return the connectionProperties
	 */
	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the pkColumnName
	 */
	public String getPkColumnName() {
		return pkColumnName;
	}

	/**
	 * @return the pkColumnValue
	 */
	public Object getPkColumnValue() {
		return pkColumnValue;
	}

	/**
	 * @param selectedOption the selectedOption to set
	 */
	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}

	/**
	 * @param parentComponent the parentComponent to set
	 */
	public void setParentComponent(JFrame parentComponent) {
		this.parentComponent = parentComponent;
	}

	/**
	 * @param connectionProperties the connectionProperties to set
	 */
	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		schemaNameTextField.setText(schemaName);
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
		tableNameTextField.setText(tableName);
	}

	/**
	 * @param pkColumnName the pkColumnName to set
	 */
	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}

	/**
	 * @param pkColumnValue the pkColumnValue to set
	 */
	public void setPkColumnValue(Object pkColumnValue) {
		this.pkColumnValue = pkColumnValue;
	}

	
	
}
