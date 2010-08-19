
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.iframe.DatabaseViewerInternalFrame;
import com.gs.dbex.application.util.WindowUtil;

/**
 *
 * @author sabuj.das
 */
public class OpenResourceDialog extends JDialog {

	private int selectedOption = ApplicationConstants.CANCEL_OPTION;
	
	private List<String> schemaNameList, tableNameList;
	private JFrame parentFrame;
	private DatabaseViewerInternalFrame targetDbViewerIFrame;
    
	private String selectedSchemaName, selectedTableName;
	
    public OpenResourceDialog(JFrame parent, List<String> schemaNameList, List<String> tableNameList,
    		DatabaseViewerInternalFrame targetDbViewerIFrame) {
        super(parent, true);
        this.schemaNameList = schemaNameList;
        this.tableNameList = tableNameList;
        this.parentFrame = parent;
        this.targetDbViewerIFrame = targetDbViewerIFrame;
        initComponents();
        setMinimumSize(new Dimension(300, 400));
        setPreferredSize(getMinimumSize());
        WindowUtil.bringCenterTo(this, parentFrame);
        getRootPane().setDefaultButton(openButton);
        selectItemTextField.requestFocus();
        showAllResources();
    }
    
    
    private void showAllResources() {
    	final String[] matchedRessNames = getMatchedResourceNames("");
        matchingItemsList.setModel(new AbstractListModel() {
            String[] strings = matchedRessNames;
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        int count = matchingItemsList.getModel().getSize();
        if(count >= 1){
        	matchingItemsList.setSelectedIndex(0);
        }
	}




	public int showOpenDialog() {
        this.setVisible(true);
        return selectedOption;
    }
    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        jLabel1 = new JLabel();
        schemaNameComboBox = new JComboBox();
        jLabel2 = new JLabel();
        selectItemTextField = new JTextField();
        jLabel3 = new JLabel();
        jScrollPane1 = new JScrollPane();
        matchingItemsList = new JList();
        resourcePathTextField = new JTextField();
        cancelButton = new JButton();
        openButton = new JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel.setLayout(new GridBagLayout());

        jLabel1.setText("SCHEMA : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel1, gridBagConstraints);

        schemaNameComboBox.setModel(new DefaultComboBoxModel(getSchemaNameList().toArray()));
        schemaNameComboBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(schemaNameComboBox, gridBagConstraints);

        jLabel2.setText("Select an Item : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel2, gridBagConstraints);

        selectItemTextField.addKeyListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(selectItemTextField, gridBagConstraints);

        jLabel3.setText("Matching Items : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jLabel3, gridBagConstraints);

        matchingItemsList.addListSelectionListener(formListener);
        jScrollPane1.setViewportView(matchingItemsList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(jScrollPane1, gridBagConstraints);

        resourcePathTextField.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(resourcePathTextField, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(cancelButton, gridBagConstraints);

        openButton.setText("Open");
        openButton.setEnabled(false);
        openButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(openButton, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener, KeyListener, ListSelectionListener, WindowListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == schemaNameComboBox) {
                OpenResourceDialog.this.schemaNameComboBoxActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                OpenResourceDialog.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == openButton) {
                OpenResourceDialog.this.openButtonActionPerformed(evt);
            }
        }

        public void keyPressed(KeyEvent evt) {
        	int code = evt.getKeyCode();
        	if(KeyEvent.VK_DOWN == code){
        		if(matchingItemsList.getModel().getSize() > 0){
        			matchingItemsList.requestFocus();
        			matchingItemsList.setSelectedIndex(0);
        		}
        	}
        }

        public void keyReleased(KeyEvent evt) {
        	if (evt.getSource() == selectItemTextField) {
                OpenResourceDialog.this.selectItemTextFieldKeyTyped(evt);
            }
        }

        public void keyTyped(KeyEvent evt) {
            
        }

        public void valueChanged(ListSelectionEvent evt) {
            if (evt.getSource() == matchingItemsList) {
                OpenResourceDialog.this.matchingItemsListValueChanged(evt);
            }
        }
		
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void windowClosing(WindowEvent arg0) {
			setSelectedOption(ApplicationConstants.CANCEL_OPTION);
			dispose();
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

    private void schemaNameComboBoxActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void selectItemTextFieldKeyTyped(KeyEvent evt) {
    	String key = selectItemTextField.getText();
        final String[] matchedRessNames = getMatchedResourceNames(key);
        matchingItemsList.setModel(new AbstractListModel() {
            String[] strings = matchedRessNames;
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
    }

    private String[] getMatchedResourceNames(String key) {
		List<String> matchedNames = new ArrayList<String>();
		for (String table : tableNameList) {
			if(table.toLowerCase().startsWith(key.toLowerCase())){
				matchedNames.add(table);
			}
		}
		String[] arr = new String[matchedNames.size()];
		for (int i = 0; i < matchedNames.size(); i++) {
			arr[i] = matchedNames.get(i);
		}
		return arr;
	}


	private void matchingItemsListValueChanged(ListSelectionEvent evt) {
		if(matchingItemsList.getSelectedIndex() < 0)
		{
			openButton.setEnabled(false);
			return;
		}
		openButton.setEnabled(true);
        Object o = matchingItemsList.getSelectedValue();
        if(o != null){
        	resourcePathTextField.setText(schemaNameComboBox.getSelectedItem().toString()
        			+ "." + o.toString());
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
    	setSelectedOption(ApplicationConstants.CANCEL_OPTION);
		dispose();
    }

    private void openButtonActionPerformed(ActionEvent evt) {
    	setSelectedOption(ApplicationConstants.APPLY_OPTION);
    	setSelectedSchemaName(schemaNameComboBox.getSelectedItem().toString());
    	setSelectedTableName(matchingItemsList.getSelectedValue().toString());
		dispose();
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


	// Variables declaration
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JPanel mainPanel;
    private JList matchingItemsList;
    private JButton openButton;
    private JTextField resourcePathTextField;
    private JComboBox schemaNameComboBox;
    private JTextField selectItemTextField;
    // End of variables declaration

	/**
	 * @return the schemaNameList
	 */
	public List<String> getSchemaNameList() {
		return schemaNameList;
	}


	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}


	/**
	 * @return the targetDbViewerIFrame
	 */
	public DatabaseViewerInternalFrame getTargetDbViewerIFrame() {
		return targetDbViewerIFrame;
	}


	/**
	 * @param schemaNameList the schemaNameList to set
	 */
	public void setSchemaNameList(List<String> schemaNameList) {
		this.schemaNameList = schemaNameList;
	}


	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}


	/**
	 * @param targetDbViewerIFrame the targetDbViewerIFrame to set
	 */
	public void setTargetDbViewerIFrame(
			DatabaseViewerInternalFrame targetDbViewerIFrame) {
		this.targetDbViewerIFrame = targetDbViewerIFrame;
	}


	/**
	 * @return the selectedSchemaName
	 */
	public String getSelectedSchemaName() {
		return selectedSchemaName;
	}


	/**
	 * @param selectedSchemaName the selectedSchemaName to set
	 */
	public void setSelectedSchemaName(String selectedSchemaName) {
		this.selectedSchemaName = selectedSchemaName;
	}


	/**
	 * @return the selectedTableName
	 */
	public String getSelectedTableName() {
		return selectedTableName;
	}


	/**
	 * @param selectedTableName the selectedTableName to set
	 */
	public void setSelectedTableName(String selectedTableName) {
		this.selectedTableName = selectedTableName;
	}

    
}
