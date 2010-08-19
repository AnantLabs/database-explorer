/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.tree.CheckBoxTreeManager;
import com.gs.dbex.application.tree.TableColumnTree;
import com.gs.dbex.application.util.WindowUtil;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 *
 */
public class ShowTableContentDialog  extends JDialog implements ActionListener, WindowListener{

	private Table table;
	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	
	
    public ShowTableContentDialog(Frame parent, boolean modal, Table table1) {
        super(parent, modal);
        table = table1;
        setSize(450,320);
        initComponents();
        
        WindowUtil.bringCenterTo(this, parent);
        getRootPane().setDefaultButton(showButton);
    }

    public int showDialog(){
    	setVisible(true);
    	return selectedOption;
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

	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}


	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}


	/**
	 * @return the checkTreeManager
	 */
	public CheckBoxTreeManager getCheckTreeManager() {
		return checkTreeManager;
	}

	/**
	 * @param checkTreeManager the checkTreeManager to set
	 */
	public void setCheckTreeManager(CheckBoxTreeManager checkTreeManager) {
		this.checkTreeManager = checkTreeManager;
	}

	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        jLabel1 = new JLabel();
        ownerNameTextField = new JTextField(getTable().getSchemaName());
        jLabel2 = new JLabel();
        tableNameTextField = new JTextField(getTable().getModelName());
        jLabel3 = new JLabel();
        jScrollPane1 = new JScrollPane();
        columnTree = new TableColumnTree(getTable());
        cancelButton = new JButton();
        showButton = new JButton();
        paginatedCheckBox = new JCheckBox();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Show Content by Column");

        mainPanel.setMinimumSize(new Dimension(400, 300));
        mainPanel.setLayout(new GridBagLayout());

        jLabel1.setText("Owner Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jLabel1, gridBagConstraints);

        ownerNameTextField.setEditable(false);
        ownerNameTextField.setFont(new Font("Tahoma", 1, 11)); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(ownerNameTextField, gridBagConstraints);

        jLabel2.setText("Table Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jLabel2, gridBagConstraints);

        tableNameTextField.setEditable(false);
        tableNameTextField.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(tableNameTextField, gridBagConstraints);

        jLabel3.setText("Select Columns to See the content");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jLabel3, gridBagConstraints);

        //columnTree.setMinimumSize(new Dimension(20, 20));
        //columnTree.setPreferredSize(new Dimension(20, 20));
        checkTreeManager = new CheckBoxTreeManager(columnTree); 
        jScrollPane1.add(columnTree);
        jScrollPane1.setViewportView(columnTree);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jScrollPane1, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(cancelButton, gridBagConstraints);

        showButton.setText("Show");
        showButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(showButton, gridBagConstraints);

        paginatedCheckBox.setSelected(true);
        paginatedCheckBox.setText("Paginated View");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(paginatedCheckBox, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    
 
    private JButton cancelButton;
    private TableColumnTree columnTree;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JPanel mainPanel;
    private JTextField ownerNameTextField;
    private JCheckBox paginatedCheckBox;
    private JButton showButton;
    private JTextField tableNameTextField;
    private CheckBoxTreeManager checkTreeManager;

	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(cancelButton)){
    		setSelectedOption(ApplicationConstants.CANCEL_OPTION);
    		dispose();
    	} else if(evt.getSource().equals(showButton)){
    		if(checkTreeManager != null){
    			
    			setSelectedOption(ApplicationConstants.APPLY_OPTION);
    		}else{
    			setSelectedOption(ApplicationConstants.CANCEL_OPTION);
    		}
    		dispose();
    	}
	}

	
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
