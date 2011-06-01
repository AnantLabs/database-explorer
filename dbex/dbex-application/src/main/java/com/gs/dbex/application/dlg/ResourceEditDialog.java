/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.BeansException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.panel.ResourceCommentPanel;
import com.gs.dbex.application.panel.ResourceRenamePanel;
import com.gs.dbex.application.panel.TruncateTablePanel;
import com.gs.dbex.application.table.CopyTablePanel;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.application.util.SqlGeneratorUtil;
import com.gs.dbex.application.util.WindowUtil;
import com.gs.dbex.common.ApplicationContextProvider;
import com.gs.dbex.common.enums.ResourceEditTypeEnum;
import com.gs.dbex.common.enums.ResourceTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class ResourceEditDialog<T> extends JDialog implements ActionListener, KeyListener, WindowListener {

	private static final Logger logger = Logger.getLogger(ResourceEditDialog.class);
	
	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	
	private T resource;
	private String schemaName;
	private ConnectionProperties connectionProperties;
	private JFrame parentFrame;
	private ResourceTypeEnum resourceTypeEnum;
	private ResourceEditTypeEnum resourceEditTypeEnum;
	
	private ResourceRenamePanel resourceRenamePanel;
	private CopyTablePanel copyTablePanel;
	private ResourceCommentPanel<T> resourceCommentPanel;
	private TruncateTablePanel truncateTablePanel;
	

	/**
	 * Generated serialVersionUID = 8219746741623031954L
	 */
	private static final long serialVersionUID = 8219746741623031954L;
	
	public ResourceEditDialog(JFrame parentFrame, boolean modal,
			ConnectionProperties connectionProperties, String schemaName,
			T resource, ResourceTypeEnum resourceType, ResourceEditTypeEnum resourceEditType) {
        super(parentFrame, modal);
        this.parentFrame = parentFrame;
        this.connectionProperties = connectionProperties;
        this.schemaName = schemaName;
        this.resource = resource;
        resourceTypeEnum = resourceType;
        resourceEditTypeEnum = resourceEditType;
        
        if(connectionProperties == null)
        	return; //TODO: throw exception
        if(!StringUtil.hasValidContent(schemaName))
        	return; //TODO: throw exception
        if(resource == null)
        	return; //TODO: throw exception
        
        initComponents();
        if(logger.isDebugEnabled()){
    		logger.debug("Resource Type : " + resourceType.getResourceName());
    		logger.debug("Resource Edit Type : " + resourceEditType.getDescription());
    	}
        // if the resource is a Table
        if(ResourceTypeEnum.TABLE.equals(resourceType)){
        	
        	Table table = null;
        	if(resource instanceof Table){
        		table = (Table)resource;
        		tableNameTextField.setText(table.getModelName());
        	}
        	if(ResourceEditTypeEnum.RENAME.equals(resourceEditType)){
        		setTitle("Rename Table");
        		logger.info("Rename Table");
        		resourceRenamePanel = new ResourceRenamePanel();
        		resourceRenamePanel.getResourceLabel().setText("New Table Name: ");
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(resourceRenamePanel, BorderLayout.CENTER);
        		String sql = SqlGeneratorUtil.generateTableRenameSQL(schemaName, table.getModelName(), 
        				resourceRenamePanel.getNewResourceNameTextField().getText());
        		sqlTextArea.setText(sql);
        		resourceRenamePanel.getNewResourceNameTextField().addKeyListener(this);
        	} else if(ResourceEditTypeEnum.DROP.equals(resourceEditType)){
        		setTitle("Drop Table");
        		logger.info("Drop Table");
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(
        				new JLabel("Do you want to DROP the table [ " + table.getModelName() + " ] " +
        						"?"), BorderLayout.NORTH);
        		sqlTextArea.setText(SqlGeneratorUtil.generateDropTableSQL(getSchemaName(), table.getModelName()));
        	} else if(ResourceEditTypeEnum.COPY.equals(resourceEditType)){
        		setTitle("Copy Table");
        		logger.info("Copy Table");
        		copyTablePanel = new CopyTablePanel();
        		String[] schemaNames = null;
        		try{
        			Set<String> schemas = ((DatabaseMetadataService) ApplicationContextProvider.getInstance().getApplicationContext()
        				.getBean(DatabaseMetadataService.BEAN_NAME)).getAvailableSchemaNames(connectionProperties); 
        			if(logger.isDebugEnabled()){
        				logger.debug("[ " + schemas.size() + " ] available schema found.");
        			}
        			schemaNames = new String[schemas.size()];
        			int i=0;
        			for (String s : schemas) {
						schemaNames[i++] = s;
					}
        		} catch (DbexException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
        		copyTablePanel.setSchemaNames(schemaNames);
        		copyTablePanel.setSourceTableName(table.getModelName());
        		copyTablePanel.getNewSchemaListComboBox().addActionListener(this);
        		copyTablePanel.getNewTableNameTextField().addKeyListener(this);
        		copyTablePanel.getCopyDataCheckBox().addActionListener(this);
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(copyTablePanel, BorderLayout.CENTER);
        		sqlTextArea.setText(
        				SqlGeneratorUtil.generateCopyTableSql(getSchemaName(), table.getModelName(),
        						"" + copyTablePanel.getNewSchemaListComboBox().getSelectedItem(), 
        						copyTablePanel.getNewTableNameTextField().getText(), 
        						copyTablePanel.getCopyDataCheckBox().isSelected())
        			);
        	} else if(ResourceEditTypeEnum.TRUNCATE.equals(resourceEditType)){
        		setTitle("Truncate Table");
        		logger.info("Truncate Table");
        		truncateTablePanel = new TruncateTablePanel();
        		truncateTablePanel.getDropRadioButton().addActionListener(this);
        		truncateTablePanel.getReuseRadioButton().addActionListener(this);
        		boolean drop = truncateTablePanel.getDropRadioButton().isSelected();
        		if(truncateTablePanel.getReuseRadioButton().isSelected()){
        			drop = false;
        		}
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(truncateTablePanel, BorderLayout.CENTER);
        		sqlTextArea.setText(
        				SqlGeneratorUtil.generateTruncateTableSQL(
        						getSchemaName(), table.getModelName(), drop
        				)
        			);
        	} else if(ResourceEditTypeEnum.COMMENT.equals(resourceEditType)){
        		setTitle("Comment on Table");
        		logger.info("Comment on Table");
        		variableComponentPanel.removeAll();
        		resourceCommentPanel = new ResourceCommentPanel<T>(resource);
        		resourceCommentPanel.getCommentTextArea().addKeyListener(this);
        		variableComponentPanel.add(resourceCommentPanel, BorderLayout.CENTER);
        		sqlTextArea.setText(SqlGeneratorUtil.generateCommentTableSQL(getSchemaName(), 
        				table.getModelName(),
        				resourceCommentPanel.getCommentTextArea().getText()));
        	}
        	
        } else if(ResourceTypeEnum.COLUMN.equals(resourceType)){
        	Column column = null;
        	if(resource instanceof Column){
        		column = (Column) resource;
        		tableNameTextField.setText(column.getTableName());
        	}
        	
        	if(ResourceEditTypeEnum.RENAME.equals(resourceEditType)){
        		setTitle("Rename Column - " + column.getModelName());
        		logger.info("Rename Column - " + column.getModelName());
        		resourceRenamePanel = new ResourceRenamePanel();
        		resourceRenamePanel.getResourceLabel().setText("New Column Name: ");
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(resourceRenamePanel, BorderLayout.CENTER);
        		String sql = SqlGeneratorUtil.generateColumnRenameSQL(schemaName, 
        				column.getTableName(),
        				column.getModelName(),
        				resourceRenamePanel.getNewResourceNameTextField().getText());
        		sqlTextArea.setText(sql);
        		resourceRenamePanel.getNewResourceNameTextField().addKeyListener(this);
        	} else if(ResourceEditTypeEnum.DROP.equals(resourceEditType)){
        		setTitle("Drop Table");
        		logger.info("Drop Table");
        		variableComponentPanel.removeAll();
        		variableComponentPanel.add(
        				new JLabel("Do you want to DROP the column [ " 
        						+ column.getTableName() + "." + column.getModelName() + " ] " +
        						"?"), BorderLayout.NORTH);
        		sqlTextArea.setText(SqlGeneratorUtil.generateDropColumnSQL(getSchemaName(), 
        				column.getTableName(),
        				column.getModelName()));
        	} else if(ResourceEditTypeEnum.COMMENT.equals(resourceEditType)){
        		setTitle("Comment on Table");
        		logger.info("Comment on Table");
        		variableComponentPanel.removeAll();
        		resourceCommentPanel = new ResourceCommentPanel<T>(resource);
        		resourceCommentPanel.getCommentTextArea().addKeyListener(this);
        		variableComponentPanel.add(resourceCommentPanel, BorderLayout.CENTER);
        		sqlTextArea.setText(SqlGeneratorUtil.generateCommentColumnSQL(
        				getSchemaName(),
        				column.getTableName(),
        				column.getModelName(),
        				resourceCommentPanel.getCommentTextArea().getText()));
        	}
        }
        
        setMinimumSize(new Dimension(450, 350));
        setPreferredSize(getMinimumSize());
        setSize(getPreferredSize());
        getRootPane().setDefaultButton(applyButton);
		WindowUtil.bringCenterTo(this, parentFrame);
        
    }


	public int showEditDialog(){
		logger.info("Opening ResourceEditDialog.");
		this.setVisible(true);
        return selectedOption;
	}
	
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainPanel = new JPanel();
        editTabbedPane = new JTabbedPane();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        wonerNameTextField = new JTextField();
        tableNameTextField = new JTextField();
        variableComponentPanel = new JPanel();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        sqlTextArea = new JTextArea();
        cancelButton = new JButton();
        applyButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel.setLayout(new GridBagLayout());

        jPanel1.setLayout(new GridBagLayout());

        jLabel1.setText("OWNER");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Table Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(jLabel2, gridBagConstraints);

        wonerNameTextField.setEditable(false);
        wonerNameTextField.setText(getSchemaName());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(wonerNameTextField, gridBagConstraints);

        tableNameTextField.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(tableNameTextField, gridBagConstraints);
        
        JSeparator separator = new JSeparator();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.insets = new Insets(5, 2, 5, 2);
        jPanel1.add(separator, gridBagConstraints);

        variableComponentPanel.setLayout(new BorderLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(variableComponentPanel, gridBagConstraints);

        editTabbedPane.addTab("Prompt", jPanel1);

        jPanel2.setLayout(new BorderLayout());

        sqlTextArea.setColumns(20);
        sqlTextArea.setEditable(false);
        sqlTextArea.setFont(new Font("Monospaced", 1, 14)); 
        sqlTextArea.setLineWrap(true);
        sqlTextArea.setRows(5);
        sqlTextArea.setTabSize(4);
        sqlTextArea.setBorder(BorderFactory.createMatteBorder(2, 10, 2, 10, new Color(204, 204, 255)));
        sqlTextArea.setCaretColor(new Color(0, 0, 204));
        sqlTextArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(sqlTextArea);

        jPanel2.add(jScrollPane1, BorderLayout.CENTER);

        editTabbedPane.addTab("SQL", jPanel2);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(editTabbedPane, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(cancelButton, gridBagConstraints);

        applyButton.setText("Apply");
        applyButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(applyButton, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == cancelButton) {
            ResourceEditDialog.this.cancelButtonActionPerformed(evt);
        }
        else if (evt.getSource() == applyButton) {
            ResourceEditDialog.this.applyButtonActionPerformed(evt);
        }
        if(copyTablePanel != null){
        	if(evt.getSource().equals(copyTablePanel.getNewSchemaListComboBox())
        			|| evt.getSource().equals(copyTablePanel.getCopyDataCheckBox())){
        		sqlTextArea.setText(
	    				SqlGeneratorUtil.generateCopyTableSql(
	    						getSchemaName(),
	    						this.tableNameTextField.getText(),
	    						"" + copyTablePanel.getNewSchemaListComboBox().getSelectedItem(), 
	    						copyTablePanel.getNewTableNameTextField().getText(), 
	    						copyTablePanel.getCopyDataCheckBox().isSelected())
	    			);
        	}
        }
        if(truncateTablePanel != null){
        	if(evt.getSource().equals(truncateTablePanel.getDropRadioButton())
        			|| evt.getSource().equals(truncateTablePanel.getReuseRadioButton())){
        		boolean drop = truncateTablePanel.getDropRadioButton().isSelected();
        		if(truncateTablePanel.getReuseRadioButton().isSelected()){
        			drop = false;
        		}
        		sqlTextArea.setText(
        				SqlGeneratorUtil.generateTruncateTableSQL(
        						getSchemaName(), tableNameTextField.getText(), drop
        				)
        			);
        	}
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
    	logger.info("Closing ResourceEditDialog.");
    	setSelectedOption(ApplicationConstants.CANCEL_OPTION);
    	dispose();
    }

    private void applyButtonActionPerformed(ActionEvent evt) {
    	setSelectedOption(ApplicationConstants.APPLY_OPTION);
    	/*QueryExecutionService executionService = new QueryExecutionServiceImpl(getConnectionProperties());
    	SqlQuery query = new SqlQuery(sqlTextArea.getText());
    	boolean exceptionOccured = false;
    	try {
    		logger.info("Executing query : " + query.getQuery());
			executionService.executeNonQuery(query);
		} catch (ApplicationException e) {
			DisplayUtils.displayMessage(parentFrame, e.getMessage());
			logger.error("Cannot execute query { " + query.getQuery() + " }\nReason: ", e);
			exceptionOccured = true;
		}
		if(!exceptionOccured){
			dispose();
		}*/
    	dispose();
    }

    // Variables declaration 
    private JButton applyButton;
    private JButton cancelButton;
    private JTabbedPane editTabbedPane;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTextArea sqlTextArea;
    private JPanel mainPanel;
    private JTextField tableNameTextField;
    private JPanel variableComponentPanel;
    private JTextField wonerNameTextField;
    // End of variables declaration

	
	public void keyPressed(KeyEvent e) {
		
	}


	
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	public void windowClosing(WindowEvent e) {
		logger.info("Closing ResourceEditDialog.");
		setSelectedOption(ApplicationConstants.CANCEL_OPTION);
    	dispose();
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

	
	public void keyReleased(KeyEvent e) {
		if(resourceRenamePanel != null){
			if(e.getSource().equals(resourceRenamePanel.getNewResourceNameTextField())){
				if(ResourceTypeEnum.TABLE.equals(getResourceTypeEnum())){
					sqlTextArea.setText(SqlGeneratorUtil.generateTableRenameSQL(
							getSchemaName(),
							tableNameTextField.getText(), 
							resourceRenamePanel.getNewResourceNameTextField().getText()));
				} else if(ResourceTypeEnum.COLUMN.equals(getResourceTypeEnum())){
					String sql = SqlGeneratorUtil.generateColumnRenameSQL(schemaName, 
	        				tableNameTextField.getText(),
	        				((Column)getResource()).getModelName(),
	        				resourceRenamePanel.getNewResourceNameTextField().getText());
	        		sqlTextArea.setText(sql);
				}
			}
		}
		if(copyTablePanel != null){
			if(e.getSource().equals(copyTablePanel.getNewTableNameTextField())){
				sqlTextArea.setText(
	    				SqlGeneratorUtil.generateCopyTableSql(
	    						getSchemaName(),
	    						this.tableNameTextField.getText(),
	    						"" + copyTablePanel.getNewSchemaListComboBox().getSelectedItem(), 
	    						copyTablePanel.getNewTableNameTextField().getText(), 
	    						copyTablePanel.getCopyDataCheckBox().isSelected())
	    			);
			}
		}
		if(resourceCommentPanel != null){
			if(e.getSource().equals(resourceCommentPanel.getCommentTextArea())){
				if(ResourceTypeEnum.TABLE.equals(getResourceTypeEnum())){
					sqlTextArea.setText(SqlGeneratorUtil.generateCommentTableSQL(getSchemaName(), 
	        				tableNameTextField.getText(),
	        				resourceCommentPanel.getCommentTextArea().getText()));
				} else if(ResourceTypeEnum.COLUMN.equals(getResourceTypeEnum())){
					sqlTextArea.setText(SqlGeneratorUtil.generateCommentColumnSQL(
	        				getSchemaName(),
	        				((Column)getResource()).getTableName(),
	        				((Column)getResource()).getModelName(),
	        				resourceCommentPanel.getCommentTextArea().getText()));
				}
				
			}
		}
	}


	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	public ResourceRenamePanel getTableRenamePanel() {
		return resourceRenamePanel;
	}


	public void setTableRenamePanel(ResourceRenamePanel tableRenamePanel) {
		this.resourceRenamePanel = tableRenamePanel;
	}


	public String getSchemaName() {
		return schemaName;
	}


	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}


	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}


	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}


	public JPanel getVariableComponentPanel() {
		return variableComponentPanel;
	}


	public void setVariableComponentPanel(JPanel variableComponentPanel) {
		this.variableComponentPanel = variableComponentPanel;
	}


	public int getSelectedOption() {
		return selectedOption;
	}


	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}


	public T getResource() {
		return resource;
	}


	public void setResource(T resource) {
		this.resource = resource;
	}


	public ResourceTypeEnum getResourceTypeEnum() {
		return resourceTypeEnum;
	}


	public void setResourceTypeEnum(ResourceTypeEnum resourceTypeEnum) {
		this.resourceTypeEnum = resourceTypeEnum;
	}


	public ResourceEditTypeEnum getResourceEditTypeEnum() {
		return resourceEditTypeEnum;
	}


	public void setResourceEditTypeEnum(ResourceEditTypeEnum resourceEditTypeEnum) {
		this.resourceEditTypeEnum = resourceEditTypeEnum;
	}


	
}
