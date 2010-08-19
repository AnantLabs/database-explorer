/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.sql.SqlDocument;

/**
 * @author sabuj.das
 *
 */
public class ResultFilterBuilderDialog extends JDialog {

	private static final String REMOVE_CURRENT_ROW_CMD = "REMOVE_CURRENT_ROW_CMD";
	private int currentRowCount = 0;
	
	private Map<String, JComponent> componentNameMap
		= new HashMap<String, JComponent>();
	
    /** Creates new form ResultFilterBuilderDialog */
    public ResultFilterBuilderDialog(Frame parent, boolean modal) {
        super(parent, modal);
        setAlwaysOnTop(true);
        initComponents();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel3 = new JLabel();
        mainPanel = new JPanel();
        verticalSplitPane = new JSplitPane();
        topPanel = new JPanel();
        jPanel1 = new JPanel();
        ownerNameLabel = new JLabel();
        ownerNameTextField = new JTextField();
        tableNameLabel = new JLabel();
        tableNameTextField = new JTextField();
        queryLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        inputQueryTextArea = new JTextArea(new SqlDocument());
        generatedQueryPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        generatedQueryTextPane = new JTextPane(new SqlDocument());
        bottomPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        addClauseButton = new JButton();
        okButton = new JButton();
        cancelButton = new JButton();

        FormListener formListener = new FormListener();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel.setLayout(new GridBagLayout());

        verticalSplitPane.setDividerLocation(150);
        verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setOneTouchExpandable(true);

        topPanel.setLayout(new GridBagLayout());

        jPanel1.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), " Input Query "));
        jPanel1.setMinimumSize(new Dimension(350, 100));
        jPanel1.setPreferredSize(new Dimension(350, 250));
        jPanel1.setLayout(new GridBagLayout());

        ownerNameLabel.setText("OWNER ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 0);
        jPanel1.add(ownerNameLabel, gridBagConstraints);

        ownerNameTextField.setEditable(false);
        ownerNameTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        jPanel1.add(ownerNameTextField, gridBagConstraints);

        tableNameLabel.setText("Table ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 0);
        jPanel1.add(tableNameLabel, gridBagConstraints);

        tableNameTextField.setEditable(false);
        tableNameTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(1, 0, 2, 2);
        jPanel1.add(tableNameTextField, gridBagConstraints);

        queryLabel.setText("Query ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 2, 0, 0);
        jPanel1.add(queryLabel, gridBagConstraints);

        inputQueryTextArea.setColumns(20);
        inputQueryTextArea.setEditable(true);
        inputQueryTextArea.setRows(3);
        inputQueryTextArea.setTabSize(4);
        inputQueryTextArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        inputQueryTextArea.setCaretColor(new Color(51, 51, 255));
        jScrollPane1.setViewportView(inputQueryTextArea);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 0, 2, 2);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        topPanel.add(jPanel1, gridBagConstraints);

        generatedQueryPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), " Generated Query "));
        generatedQueryPanel.setMinimumSize(new Dimension(150, 100));
        generatedQueryPanel.setPreferredSize(new Dimension(250, 250));
        generatedQueryPanel.setLayout(new GridBagLayout());

        
        generatedQueryTextPane.setEditable(true);
        generatedQueryTextPane.setCaretColor(new Color(51, 51, 255));
        generatedQueryTextPane.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(generatedQueryTextPane);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 5, 2, 2);
        generatedQueryPanel.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        topPanel.add(generatedQueryPanel, gridBagConstraints);

        verticalSplitPane.setTopComponent(topPanel);

        bottomPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), " WHERE : "));
        bottomPanel.setLayout(new GridBagLayout());

        jLabel1.setText("Logic");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Column");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomPanel.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Condition");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Value");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomPanel.add(jLabel5, gridBagConstraints);

        addClauseButton.setText("+");
        addClauseButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomPanel.add(addClauseButton, gridBagConstraints);

        verticalSplitPane.setRightComponent(bottomPanel);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainPanel.add(verticalSplitPane, gridBagConstraints);

        okButton.setText("Apply");
        okButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        mainPanel.add(okButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 0);
        mainPanel.add(cancelButton, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        
        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == addClauseButton) {
                ResultFilterBuilderDialog.this.addClauseButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                ResultFilterBuilderDialog.this.cancelButtonActionPerformed(evt);
            }
            else if (evt.getSource() == okButton) {
                ResultFilterBuilderDialog.this.okButtonActionPerformed(evt);
            }
            if(REMOVE_CURRENT_ROW_CMD.equals(evt.getActionCommand())){
            	JButton b = (JButton) evt.getSource();
            	String name = b.getName();
            	int i = 0, j = 0, p = 0;
            	String[] i_j = name.split("_");
            	i = Integer.parseInt(i_j[0]);
            	j = Integer.parseInt(i_j[1]);
            	int to = 5 + (5 * i) + j;
            	int from = to - 5;
            	for(p = to; p > from ; p--){
            		bottomPanel.remove(p);
            	}
            	bottomPanel.updateUI();
            }
        }
    }

    private void addClauseButtonActionPerformed(ActionEvent evt) {
    	GridBagConstraints gridBagConstraints;
    	
    	JComboBox logicBox = new JComboBox(),
    		columnsBox = new JComboBox(),
    		operatoprBox = new JComboBox();
    	JTextField valueTextField = new JTextField();
    	JButton removeButton = new JButton();
    	
    	//if(currentRowCount > 0){
    		logicBox.setModel(new javax.swing.DefaultComboBoxModel(ApplicationConstants.LOGICAL_OPERATORS));
    		logicBox.setName(""+currentRowCount + "_" + 0);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = currentRowCount + 1;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
            bottomPanel.add(logicBox, gridBagConstraints);
    	//}
        
        columnsBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"kfdjghsjdfhg", "dfjhfdjghksjd"}));
        columnsBox.setName(""+currentRowCount + "_" + 1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = currentRowCount + 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        bottomPanel.add(columnsBox, gridBagConstraints);
        
        operatoprBox.setModel(new javax.swing.DefaultComboBoxModel(ApplicationConstants.SQL_CONDITION_OPERATORS));
        operatoprBox.setName(""+currentRowCount + "_" + 2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = currentRowCount + 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        bottomPanel.add(operatoprBox, gridBagConstraints);

        valueTextField.setText("");
        valueTextField.setName(""+currentRowCount + "_" + 3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = currentRowCount + 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        bottomPanel.add(valueTextField, gridBagConstraints);

        removeButton.setText("-");
        removeButton.setName(""+currentRowCount + "_" + 4);
        removeButton.setActionCommand(REMOVE_CURRENT_ROW_CMD);
        removeButton.addActionListener(new FormListener());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = currentRowCount + 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        bottomPanel.add(removeButton, gridBagConstraints);
        
        bottomPanel.updateUI();
        currentRowCount++;
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
                ResultFilterBuilderDialog dialog = new ResultFilterBuilderDialog(new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration 
    private JButton addClauseButton;
    private JPanel bottomPanel;
    private JButton cancelButton;
    private JPanel generatedQueryPanel;
    private JTextPane generatedQueryTextPane;
    private JTextArea inputQueryTextArea;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JPanel mainPanel;
    private JButton okButton;
    private JLabel ownerNameLabel;
    private JTextField ownerNameTextField;
    private JLabel queryLabel;
    private JLabel tableNameLabel;
    private JTextField tableNameTextField;
    private JPanel topPanel;
    private JSplitPane verticalSplitPane;
    // End of variables declaration

}