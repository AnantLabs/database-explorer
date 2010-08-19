/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.collection.CollectionUtils;

/**
 * @author sabuj.das
 *
 */
public class SearchColumnPanel extends JPanel implements ActionListener {
	
	private static final Logger logger = Logger.getLogger(SearchColumnPanel.class);
	
	private static final Icon DB_SEARCH_ICON = new ImageIcon(
			SearchTablePanel.class.getResource(ApplicationConstants.IMAGE_PATH + "database_search.png"));
	private static final Icon LOADING_SEARCH_RESULT_ICON = new ImageIcon(
			SearchTablePanel.class.getResource(ApplicationConstants.IMAGE_PATH + "loading_001.gif"));
	
	private JFrame parentFrame;
	private ConnectionProperties connectionProperties;
	private String[] availableSchemaNames;
	
	
    public SearchColumnPanel(JFrame parentFrame,
			ConnectionProperties connectionProperties) {
    	this.connectionProperties = connectionProperties;
    	this.parentFrame = parentFrame;
    	Connection connection = null;
    	String[] schemaNames = null;
		try{
			connection = connectionProperties.getDataSource().getConnection();
			Set<String> schemas = new OracleDbGrabber().getAvailableSchemaNames(connection);
			if(logger.isDebugEnabled()){
				logger.debug("[ " + schemas.size() + " ] available schema found.");
			}
			schemaNames = new String[schemas.size()];
			int i=0;
			for (String s : schemas) {
				schemaNames[i++] = s;
			}
			availableSchemaNames = schemaNames;
		}catch(SQLException se){
			logger.error("Cannot copy table", se);
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	
        initComponents();
        if(availableSchemaNames != null){
        	availableSchemasComboBox.setModel(new DefaultComboBoxModel(availableSchemaNames));
        	int pos = 0;//CollectionUtils.getLocation(connectionProperties.getDatabaseName(), availableSchemaNames);
        	if(pos > -1){
        		availableSchemasComboBox.setSelectedIndex(pos);
        	}
        }

    }

    
    
    public JFrame getParentFrame() {
		return parentFrame;
	}



	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}



	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}



	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}



	public String[] getAvailableSchemaNames() {
		return availableSchemaNames;
	}



	public void setAvailableSchemaNames(String[] availableSchemaNames) {
		this.availableSchemaNames = availableSchemaNames;
	}



	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        columnNameTextField = new JTextField();
        jLabel3 = new JLabel();
        availableSchemasComboBox = new JComboBox();
        allSchemaCheckBox = new JCheckBox();
        jSeparator1 = new JSeparator();
        jLabel5 = new JLabel();
        jScrollPane1 = new JScrollPane();
        searchResultTable = new JTable();
        clearButton = new JButton();
        imageLabel = new JLabel();
        searchButton = new JButton();
        resultLabel = new JLabel();

        setLayout(new GridBagLayout());

        jLabel1.setText("<HTML> <BODY> <B><U>S</U>earch Column</B> ( * = any string, ? = any char) </BODY> </HTML>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(8, 2, 8, 2);
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Column Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(columnNameTextField, gridBagConstraints);

        jLabel3.setText("In Schema");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel3, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(availableSchemasComboBox, gridBagConstraints);

        allSchemaCheckBox.setText("All available Schemas");
        allSchemaCheckBox.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        add(allSchemaCheckBox, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        add(jSeparator1, gridBagConstraints);

        jLabel5.setText("Search Result  ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel5, gridBagConstraints);

        searchResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        searchResultTable.setColumnSelectionAllowed(true);
        searchResultTable.setGridColor(new Color(153, 204, 255));
        jScrollPane1.setViewportView(searchResultTable);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jScrollPane1, gridBagConstraints);

        clearButton.setText("Clear");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(clearButton, gridBagConstraints);

        imageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imageLabel.setIcon(DB_SEARCH_ICON);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(imageLabel, gridBagConstraints);

        searchButton.setText("Search");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(searchButton, gridBagConstraints);

        resultLabel.setText("hjhgjh");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(resultLabel, gridBagConstraints);
    }

    

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == allSchemaCheckBox) {
            SearchColumnPanel.this.allSchemaCheckBoxActionPerformed(evt);
        } else if (evt.getSource() == allSchemaCheckBox) {
            search();
        }
    }

    private void search() {
		
	}



	private void allSchemaCheckBoxActionPerformed(ActionEvent evt) {
        if(allSchemaCheckBox.isSelected()){
            availableSchemasComboBox.setEnabled(false);
        } else if(!allSchemaCheckBox.isSelected()){
            availableSchemasComboBox.setEnabled(true);
        }
    }


    private JCheckBox allSchemaCheckBox;
    private JComboBox availableSchemasComboBox;
    private JButton clearButton;
    private JTextField columnNameTextField;
    private JLabel imageLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JLabel resultLabel;
    private JButton searchButton;
    private JTable searchResultTable;

}
