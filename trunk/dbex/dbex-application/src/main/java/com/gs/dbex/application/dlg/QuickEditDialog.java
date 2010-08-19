/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.application.util.WindowUtil;
import com.gs.dbex.application.vo.QuickEditVO;

/**
 * @author sabuj das
 *
 */
public class QuickEditDialog extends JDialog implements ActionListener, WindowListener {

	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	
	private JFrame parentFrame;
	private QuickEditVO quickEditVO;
	
	
    public QuickEditDialog(JFrame parent, QuickEditVO quickEditVO) {
        super(parent, true);
        this.parentFrame = parent;
        this.quickEditVO = quickEditVO;
        initComponents();
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(getMinimumSize());
        WindowUtil.bringCenterTo(this, parentFrame);
        getRootPane().setDefaultButton(updateButton);
        valueTextArea.requestFocus();
    }

    public int showDialog() {
        this.setVisible(true);
        return selectedOption;
    }
    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        jLabel1 = new JLabel();
        schemaNameTextField = new JTextField();
        jLabel2 = new JLabel();
        tableNameTextField = new JTextField();
        jLabel3 = new JLabel();
        jScrollPane1 = new JScrollPane();
        valueTextArea = new JTextArea();
        cancelButton = new JButton();
        updateButton = new JButton();
        columnNameLabel = new JLabel();
        jSeparator1 = new JSeparator();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        setTitle("Quick Edit"); 
        setName("Form"); 

        updateButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new GridBagLayout());

        jLabel1.setText("SCHEMA : "); 
        jLabel1.setName("jLabel1");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel1, gridBagConstraints);

        schemaNameTextField.setBackground(new Color(255, 255, 255));
        schemaNameTextField.setEditable(false);
        schemaNameTextField.setForeground(new Color(0, 0, 204));
        schemaNameTextField.setText(getQuickEditVO().getSchemaName()); 
        schemaNameTextField.setName("schemaNameTextField"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(schemaNameTextField, gridBagConstraints);

        jLabel2.setText("TABLE : ");
        jLabel2.setName("jLabel2"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel2, gridBagConstraints);

        tableNameTextField.setBackground(new Color(255, 255, 255));
        tableNameTextField.setEditable(false);
        tableNameTextField.setForeground(new Color(0, 0, 204));
        tableNameTextField.setText(getQuickEditVO().getTableName()); 
        tableNameTextField.setName("tableNameTextField");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(tableNameTextField, gridBagConstraints);

        jLabel3.setText("Value : ");
        jLabel3.setName("jLabel3");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel3, gridBagConstraints);

        jScrollPane1.setName("jScrollPane1"); 

        valueTextArea.setText(getQuickEditVO().getCurrentColumnValue());
        valueTextArea.setColumns(20);
        valueTextArea.setRows(5);
        valueTextArea.setName("valueTextArea"); 
        jScrollPane1.setViewportView(valueTextArea);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jScrollPane1, gridBagConstraints);

        cancelButton.setText("Cancel"); 
        cancelButton.setName("cancelButton");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(4, 2, 2, 2);
        mainPanel.add(cancelButton, gridBagConstraints);

        updateButton.setText("Update"); 
        updateButton.setName("updateButton"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(4, 2, 2, 2);
        mainPanel.add(updateButton, gridBagConstraints);

        columnNameLabel.setText(getQuickEditVO().getCurrentColumnName() + " = ?");
        columnNameLabel.setName("columnNameLabel"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 2, 4, 2);
        mainPanel.add(columnNameLabel, gridBagConstraints);

        jSeparator1.setName("jSeparator1");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 0);
        mainPanel.add(jSeparator1, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    
    
    public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(updateButton)){
			setSelectedOption(ApplicationConstants.APPLY_OPTION);
			
			String v = valueTextArea.getText().trim();
			String q = "UPDATE "+ getQuickEditVO().getSchemaName() 
						+ "." + getQuickEditVO().getTableName() + 
				" SET "+ getQuickEditVO().getCurrentColumnName() + " = '" + v
				+ "' WHERE ROWID = '" + getQuickEditVO().getRowid() + "' AND ORA_ROWSCN = '"
				+ getQuickEditVO().getOraRowscn() + "'";
			Connection con = null;
			try{
				con = getQuickEditVO().getConnectionProperties().getDataSource().getConnection();
				Statement stmt = con.prepareStatement(q);
				int i = stmt.executeUpdate(q);
				DisplayUtils.displayMessage(getParentFrame(), "[ " + i + " ] rows updated.",
						DisplayTypeEnum.INFO);
			}catch(Exception ex){
				DisplayUtils.displayMessage(getParentFrame(), ex.getMessage(), DisplayTypeEnum.ERROR);
				return;
			}finally{
				if(con != null){
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			dispose();
		}else if(e.getSource().equals(cancelButton)){
			setSelectedOption(ApplicationConstants.CANCEL_OPTION);
			dispose();
		} 
	}

    // Variables declaration 
    private JButton cancelButton;
    private JLabel columnNameLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JPanel mainPanel;
    private JTextField schemaNameTextField;
    private JTextField tableNameTextField;
    private JButton updateButton;
    private JTextArea valueTextArea;
    // End of variables declaration



	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}


	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}


	/**
	 * @return the quickEditVO
	 */
	public QuickEditVO getQuickEditVO() {
		return quickEditVO;
	}


	/**
	 * @param quickEditVO the quickEditVO to set
	 */
	public void setQuickEditVO(QuickEditVO quickEditVO) {
		this.quickEditVO = quickEditVO;
	}

	/**
	 * @return the selectedOption
	 */
	public int getSelectedOption() {
		return selectedOption;
	}

	/**
	 * @param selectedOption the selectedOption to set
	 */
	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}

	
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowClosing(WindowEvent e) {
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


    
}
