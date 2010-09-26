/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.sql.SqlDocument;

/**
 * @author sabuj.das
 *
 */
public class ResultFilterDialog extends JDialog implements ActionListener, WindowListener{

	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	
	private String inputQuery;
	private String outputQuery;
	private String filterQuery = "";
	private String connectionName;
	
	private JFrame parentComponent;	
	
    public ResultFilterDialog(JFrame parent, boolean modal, String connectionName) {
        super(parent, modal);
        parentComponent = parent;
        this.connectionName = connectionName;
        initComponents();
        getRootPane().setDefaultButton(okButton);
        setTitle("Filter");
    }

    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        setMinimumSize(new Dimension(450, 300));
        setPreferredSize(getMinimumSize());
        
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        queryTextPane = new JTextPane( new SqlDocument(connectionName));
        cancelButton = new JButton();
        okButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setLayout(new GridBagLayout());

        jPanel2.setBorder(BorderFactory.createTitledBorder(" WHERE "));
        jPanel2.setLayout(new BorderLayout());
        
        queryTextPane.setSize(350, 200);
        queryTextPane.setText(outputQuery);
        queryTextPane.setMargin(new Insets(2,2,2,2));
        jScrollPane1.setViewportView(queryTextPane);

        jPanel2.add(jScrollPane1, BorderLayout.CENTER);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 0, 3, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(0, 2, 2, 0);
        jPanel1.add(cancelButton, gridBagConstraints);

        okButton.setText("Apply");
        okButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(0, 0, 2, 2);
        jPanel1.add(okButton, gridBagConstraints);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }

    
    public int showFilterDialog() {
        this.setVisible(true);
        return selectedOption;
    }

    private JButton cancelButton;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JButton okButton;
    private JTextPane queryTextPane;


	public String getInputQuery() {
		return inputQuery;
	}


	public String getOutputQuery() {
		return outputQuery;
	}


	public JFrame getParentComponent() {
		return parentComponent;
	}


	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
	}


	public void setOutputQuery(String outputQuery) {
		this.outputQuery = outputQuery;
	}


	public void setParentComponent(JFrame parentComponent) {
		this.parentComponent = parentComponent;
	}

	public int getSelectedOption() {
		return selectedOption;
	}


	public void setSelectedOption(int selectedOption) {
		this.selectedOption = selectedOption;
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(okButton)){
			setSelectedOption(ApplicationConstants.APPLY_OPTION);
			setOutputQuery(getInputQuery() + " WHERE " + queryTextPane.getText());
			setFilterQuery(queryTextPane.getText());
			dispose();
		}else if(e.getSource().equals(cancelButton)){
			setSelectedOption(ApplicationConstants.CANCEL_OPTION);
			setOutputQuery(getInputQuery() );
			setFilterQuery(queryTextPane.getText());
			dispose();
		} 
	}


	
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	public void windowClosed(WindowEvent e) {
		
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


	public String getFilterQuery() {
		return filterQuery;
	}


	public void setFilterQuery(String filterQuery) {
		this.filterQuery = filterQuery;
		queryTextPane.setText(filterQuery);
	}

}