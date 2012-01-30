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
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.common.ApplicationContextProvider;
import com.gs.dbex.common.enums.ObjectTypeEnum;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.TableSearchCriteria;
import com.gs.dbex.model.vo.TableSearchResult;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class SearchTablePanel extends JPanel implements ActionListener {

	private static final Logger logger = Logger.getLogger(SearchTablePanel.class);
	
	private static final Icon DB_SEARCH_ICON = new ImageIcon(
			SearchTablePanel.class.getResource(ApplicationConstants.IMAGE_PATH + "database_search.png"));
	private static final Icon LOADING_SEARCH_RESULT_ICON = new ImageIcon(
			SearchTablePanel.class.getResource(ApplicationConstants.IMAGE_PATH + "loading_001.gif"));
	
	private JFrame parentFrame;
	private ConnectionProperties connectionProperties;
	private String[] availableSchemaNames;
	
    public SearchTablePanel(JFrame parentFrame,
			ConnectionProperties connectionProperties) {
    	this.connectionProperties = connectionProperties;
    	this.parentFrame = parentFrame;
    	String[] schemaNames = null;
		try{
			Set<String> schemas = ((DatabaseMetadataService) ApplicationContextProvider.getInstance().getApplicationContext()
    				.getBean(DatabaseMetadataService.BEAN_NAME))
    				.getAvailableSchemaNames(connectionProperties, ReadDepthEnum.DEEP); 
			if(logger.isDebugEnabled()){
				logger.debug("[ " + schemas.size() + " ] available schema found.");
			}
			schemaNames = new String[schemas.size()];
			int i=0;
			for (String s : schemas) {
				schemaNames[i++] = s;
			}
			availableSchemaNames = schemaNames;
		}catch(DbexException se){
			logger.error("Cannot copy table", se);
		}catch(Exception se){
			logger.error("Cannot copy table", se);
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
        tableNameTextField = new JTextField();
        jLabel3 = new JLabel();
        availableSchemasComboBox = new JComboBox();
        allSchemaCheckBox = new JCheckBox();
        jLabel4 = new JLabel();
        normalCheckBox = new JCheckBox();
        externalCheckBox = new JCheckBox();
        indexedCheckBox = new JCheckBox();
        transactionCheckBox = new JCheckBox();
        sessionCheckBox = new JCheckBox();
        jSeparator1 = new JSeparator();
        jLabel5 = new JLabel();
        jScrollPane1 = new JScrollPane();
        searchResultTable = new JTable();
        clearButton = new JButton();
        searchResultLabel = new JLabel();
        searchButton = new JButton();
        imageLabel = new JLabel();

        setLayout(new GridBagLayout());

        jLabel1.setText("<HTML> <BODY> <B><U>S</U>earch Table</B> ( * = any string, ? = any char) </BODY> </HTML>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(8, 2, 8, 2);
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Table Name");
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
        add(tableNameTextField, gridBagConstraints);

        jLabel3.setText("In Schema");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel3, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(availableSchemasComboBox, gridBagConstraints);

        allSchemaCheckBox.setText("All available Schemas");
        allSchemaCheckBox.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        add(allSchemaCheckBox, gridBagConstraints);

        jLabel4.setText("Table Type");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel4, gridBagConstraints);

        normalCheckBox.setText("Normal");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        add(normalCheckBox, gridBagConstraints);

        externalCheckBox.setText("External");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(externalCheckBox, gridBagConstraints);

        indexedCheckBox.setText("Index Organized");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(indexedCheckBox, gridBagConstraints);

        transactionCheckBox.setText("Temporary (Transaction)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        add(transactionCheckBox, gridBagConstraints);

        sessionCheckBox.setText("Temporary (Session)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(sessionCheckBox, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        add(jSeparator1, gridBagConstraints);

        jLabel5.setText("Search Result  ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel5, gridBagConstraints);

        searchResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        searchResultTable.setColumnSelectionAllowed(true);
        searchResultTable.setGridColor(new Color(153, 204, 255));
        searchResultTable.setAutoCreateRowSorter(true);
        searchResultTable.setAutoscrolls(true);
        jScrollPane1.setViewportView(searchResultTable);
        searchResultTable.getColumnModel().getSelectionModel()
        	.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jScrollPane1, gridBagConstraints);

        clearButton.setText("Clear");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(clearButton, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(searchResultLabel, gridBagConstraints);

        searchButton.setText("Search");
        searchButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(searchButton, gridBagConstraints);

        imageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imageLabel.setIcon(DB_SEARCH_ICON);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(imageLabel, gridBagConstraints);
    }


    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == allSchemaCheckBox) {
            SearchTablePanel.this.allSchemaCheckBoxActionPerformed(evt);
        } else if (evt.getSource() == searchButton) {
            search();
        }
    }

    private void search() {
		String searchString = tableNameTextField.getText();
		boolean allSchemas = allSchemaCheckBox.isSelected();
		String ownerName = null;
		if(!allSchemas)
			ownerName = availableSchemasComboBox.getSelectedItem().toString();
		boolean valid = validateCriteria(searchString, allSchemas, ownerName);
		if(!valid)
			return;
		TableSearchCriteria criteria = new TableSearchCriteria(
				ownerName, 
				searchString, 
				ObjectTypeEnum.TABLE.getTypeCode(), 
				allSchemas);
		displayResult(criteria);
	}

    public boolean validateCriteria(String searchString, boolean allSchemas, String schemaName){
		boolean valid = true;
		StringBuffer messageBuffer = new StringBuffer("Required Fields: \n");
		if(!StringUtil.hasValidContent(searchString)){
			messageBuffer.append("1.\tTable Name\n");
			valid = valid & false;
		} else {
			valid = valid & true;
		}
		if(!allSchemaCheckBox.isSelected()){
			if(availableSchemasComboBox.getSelectedItem() == null){
				valid = valid & false;
				messageBuffer.append("2.\tOwner Name\n");
			}
		}
		
		if(!valid){
			DisplayUtils.displayMessage(getParentFrame(), messageBuffer.toString());
		}
		return valid;
	}
    
	private void displayResult(TableSearchCriteria criteria) {
		if(criteria == null)
			return;
		List<TableSearchResult> resultList = new ArrayList<TableSearchResult>();
		
		/*Connection connection = null;
		try {
			connection = getConnectionProperties().getDataSource().getConnection();
			QueryExecutionService service = new QueryExecutionServiceImpl(connectionProperties);
			ResultSet rs = service.executeSelect(connection, criteria.getSearchQuery());
			if(rs != null){
				while(rs.next()){
					TableSearchResult r = new TableSearchResult();
					r.setObjectName(rs.getString(SearchObjectMetaDataEnum.TABLE_NAME.getType()));
					r.setOwnerName(rs.getString(SearchObjectMetaDataEnum.OWNER.getType()));
					r.setTableSpaceName(rs.getString(SearchObjectMetaDataEnum.TABLESPACE_NAME.getType()));
					r.setNumberOfRows(rs.getLong(SearchObjectMetaDataEnum.NUM_ROWS.getType()));
					r.setStatus(rs.getString(SearchObjectMetaDataEnum.STATUS.getType()));
					
					r.setCreatedDate(rs.getTimestamp(SearchObjectMetaDataEnum.CREATED.getType()));
					resultList.add(r);
				}
			}
			if(rs != null){
				rs.close();
			}
		} catch (ApplicationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		
		searchResultTable.setModel(new CollectionTableModel<TableSearchResult>(resultList, TableSearchResult.class.getCanonicalName()));
		
		DrawingUtil.updateTableColumnWidth(searchResultTable);*/
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
    private JCheckBox externalCheckBox;
    private JLabel imageLabel;
    private JCheckBox indexedCheckBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JCheckBox normalCheckBox;
    private JButton searchButton;
    private JLabel searchResultLabel;
    private JTable searchResultTable;
    private JCheckBox sessionCheckBox;
    private JTextField tableNameTextField;
    private JCheckBox transactionCheckBox;

}
