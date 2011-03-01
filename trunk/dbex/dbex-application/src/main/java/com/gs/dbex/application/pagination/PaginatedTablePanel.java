/**
 * 
 */
package com.gs.dbex.application.pagination;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import oracle.sql.ROWID;

import org.apache.log4j.Logger;

import com.gs.dbex.application.comps.ExtensionFileFilter;
import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.dlg.QuickEditDialog;
import com.gs.dbex.application.dlg.ResultFilterDialog;
import com.gs.dbex.application.dlg.TableDataEditorDialog;
import com.gs.dbex.application.table.model.DataTableTableModel;
import com.gs.dbex.application.table.model.DataTableTableModelFactory;
import com.gs.dbex.application.table.model.ResultSetTableModelFactory;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.common.enums.TableDataExportTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.design.util.DesignUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.PaginationResult;
import com.gs.dbex.model.vo.QuickEditVO;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.text.StringUtil;



/**
 * @author sabuj.das
 *
 */
public class PaginatedTablePanel extends JPanel implements Serializable,
		ActionListener, MouseListener, KeyListener, FocusListener {

	/**
	 * Generated serialVersionUID = -4196778703846255569L;
	 */
	private static final long serialVersionUID = -4196778703846255569L;

	private static final Logger logger = Logger.getLogger(PaginatedTablePanel.class);
	
	public static final int MIN_RECORDS_PER_PAGE = 30;
	public static final int MAX_RECORDS_PER_PAGE = 99999;
	
	private JFrame parentFrame;
	private ConnectionProperties connectionProperties;
	private DataTableTableModelFactory dataTableTableModelFactory;
	private String currentFilter = "";
	private String queryString;
	private PaginationResult paginationResult;
	
	private Table databaseTable;
	

    public PaginatedTablePanel(JFrame parentFrame, ConnectionProperties connectionProperties, Table databaseTable) {
		this.databaseTable = databaseTable;
    	this.parentFrame = parentFrame;
        this.connectionProperties = connectionProperties;
        dataTableTableModelFactory = new DataTableTableModelFactory();
        paginationResult = new PaginationResult();
        paginationResult.setRowsPerPage(MIN_RECORDS_PER_PAGE);
        paginationResult.setStartRow(1);
        initComponents();
        targetTable.addMouseListener(this);
        targetTable.setCellSelectionEnabled(true);
        populatePaginatedResult(1);
	}

	public PaginationResult getPaginationResult() {
		return paginationResult;
	}

	public void populatePaginatedResult(int pageNumber){
    	if(dataTableTableModelFactory != null){
        	paginationResult.setCurrentPage(pageNumber);
        	showTableData();
        }
    }
    
    private int getTotalRecords(String filterSubQuery){
    	int totalRows = 0;
    	QueryExecutionService queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
    	if(null != queryExecutionService){
    		try {
				totalRows = queryExecutionService.getTotalRecords(connectionProperties, databaseTable, filterSubQuery);
			} catch (DbexException e) {
				DisplayUtils.displayMessage(getParentFrame(), e.getMessage(), DisplayTypeEnum.ERROR);
			}
    	}
		return totalRows;
    }
    
    private int getTotalRecords(){
    	int totalRows = 0;
    	QueryExecutionService queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
    	if(null != queryExecutionService){
    		try {
				totalRows = queryExecutionService.getTotalRecords(connectionProperties, databaseTable);
			} catch (DbexException e) {
				DisplayUtils.displayMessage(getParentFrame(), e.getMessage(), DisplayTypeEnum.ERROR);
			}
    	}
		return totalRows;
    }
    
    public void showTableData() {
		paginationResult.setRowAttributes(getTotalRecords());
		paginationResult.setEndRow(paginationResult.getStartRow() + paginationResult.getRowsPerPage());
		int rowNumFrom = paginationResult.getStartRow();
    	int rowNumTo = paginationResult.getEndRow();
    	try {
			targetTable.setModel(dataTableTableModelFactory.getResultSetTableModel(connectionProperties, databaseTable, rowNumFrom, rowNumTo));
			DesignUtil.updateTableColumnWidth(targetTable);
		} catch(Exception e){
			e.printStackTrace();
			DisplayUtils.displayMessage(getParentFrame(), e.getMessage(), DisplayTypeEnum.ERROR);
		}
		populatePageLinks();
		
	}
    
    public void showTableData(String filterSubQuery) {
    	int totalRecords = getTotalRecords(filterSubQuery);
    	paginationResult.setRowsPerPage(totalRecords);
    	paginationResult.setRowAttributes(totalRecords);
		paginationResult.setEndRow(paginationResult.getStartRow() + paginationResult.getRowsPerPage());
    	try {
    		DataTableTableModel tableModel = dataTableTableModelFactory.getResultSetTableModel(connectionProperties, databaseTable, filterSubQuery);
			targetTable.setModel(tableModel);
			DesignUtil.updateTableColumnWidth(targetTable);
		} catch(Exception e){
			DisplayUtils.displayMessage(getParentFrame(), e.getMessage(), DisplayTypeEnum.ERROR);
		}
		populatePageLinks();
	}

    
    private void populatePageLinks() {
    	if(paginationResult.isPreviousPage()){
			previousPageLabel.setEnabled(true);
			goToPreviousPageLabel.setVisible(true);
			goToFirstPageLinkLabel.setVisible(true);
		}else{
			previousPageLabel.setEnabled(false);
			goToPreviousPageLabel.setVisible(false);
			goToFirstPageLinkLabel.setVisible(false);
		}
		if(paginationResult.isNextPage()){
			nextPageLabel.setEnabled(true);
			goToNextPageLinkLabel.setVisible(true);
			goToLastPageLinkLabel.setVisible(true);
		}else{
			nextPageLabel.setEnabled(false);
			goToNextPageLinkLabel.setVisible(false);
			goToLastPageLinkLabel.setVisible(false);
		}
		
		totaPageslLabel.setText("Page "+ paginationResult.getCurrentPage()
				+ " of " + paginationResult.getTotalPages() + " page(s). { Total " +
				paginationResult.getTotalRows() + " records. }");
		
		alterPageNumbers();
	}
    
    public void alterPageNumbers(){
		resetPageNumbers();
		int first = getFirstPage();
		if(first>0){
			firstPageLinkLabel.setText(""+first);
			if(first == paginationResult.getCurrentPage()){
				firstPageLinkLabel.setForeground(Color.BLUE);
			}else{
				firstPageLinkLabel.setForeground(Color.BLACK);
			}
			if(first+1 <= paginationResult.getTotalPages()){
				secondPageLinkLabel.setText(""+(first+1));
				if(first+1 == paginationResult.getCurrentPage()){
					secondPageLinkLabel.setForeground(Color.BLUE);
				}else{
					secondPageLinkLabel.setForeground(Color.BLACK);
				}
				if(first+2 <= paginationResult.getTotalPages()){
					thirdPageLinkLabel.setText(""+(first+2));
					if(first+2 == paginationResult.getCurrentPage()){
						thirdPageLinkLabel.setForeground(Color.BLUE);
					}else{
						thirdPageLinkLabel.setForeground(Color.BLACK);
					}
					if(first+3 <= paginationResult.getTotalPages()){
						fourthPageLinkLabel.setText(""+(first+3));
						if(first+3 == paginationResult.getCurrentPage()){
							fourthPageLinkLabel.setForeground(Color.BLUE);
						}else{
							fourthPageLinkLabel.setForeground(Color.BLACK);
						}
						if(first+4 <= paginationResult.getTotalPages()){
							fifthPageLinkLabel.setText(""+(first+4));
							if(first+4 == paginationResult.getCurrentPage()){
								fifthPageLinkLabel.setForeground(Color.BLUE);
							}else{
								fifthPageLinkLabel.setForeground(Color.BLACK);
							}
						}
					}
				}
			}
		}
		
	}
    
    private void resetPageNumbers(){
		firstPageLinkLabel.setText("");
		secondPageLinkLabel.setText("");
		thirdPageLinkLabel.setText("");
		fourthPageLinkLabel.setText("");
		fifthPageLinkLabel.setText("");
	}
    
    private int getFirstPage(){
		int page = 0;
		if(paginationResult.getTotalPages() <=5 || paginationResult.getCurrentPage() <=3){
			page = 1;
		}else if(paginationResult.getCurrentPage() + 1 > paginationResult.getTotalPages()){
			page = paginationResult.getCurrentPage() - 4;
		}else if(paginationResult.getCurrentPage()+2 > paginationResult.getTotalPages()){
			page = paginationResult.getCurrentPage()-3;
		}else{
			page = paginationResult.getCurrentPage()-2;
		}
		return page;
	}

	public void gotoNextPage(){
    	if(paginationResult.getCurrentPage() >= paginationResult.getTotalPages()){
    		return ;
    	}
    	paginationResult.setCurrentPage(
    		paginationResult.getCurrentPage() + 1);
    	gotoPage(paginationResult.getCurrentPage());
    }
    
    public void gotoPreviousPage(){
    	if(paginationResult.getCurrentPage() <= 0){
    		return ;
    	}
    	paginationResult.setCurrentPage(
    		paginationResult.getCurrentPage() - 1);
    	gotoPage(paginationResult.getCurrentPage());
    }
    
    public void gotoPage(int pageNumber){
    	
    	populatePaginatedResult(pageNumber);
    }
    
   
    /**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}

	/**
	 * @return the connectionProperties
	 */
	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	/**
	 * @param connectionProperties the connectionProperties to set
	 */
	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Table getDatabaseTable() {
		return databaseTable;
	}

	public void setDatabaseTable(Table databaseTable) {
		this.databaseTable = databaseTable;
	}

	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        topNavigationPanel = new JPanel();
        previousPageLabel = new JLabel();
        rowsPerPageLabel = new JLabel();
        rowsPerPageTextField = new JTextField();
        refreshButton = new JButton();
        hiddenLabel_01 = new JLabel();
        showActionsToolbarCheckBox = new JCheckBox();
        nextPageLabel = new JLabel();
        tablePanel = new JPanel();
        actionsToolBar = new JToolBar();
        addNewRecordButton = new JButton();
        editRecordButton = new JButton();
        deleteRecordButton = new JButton();
        jSeparator1 = new JToolBar.Separator();
        exportAsLabel = new JLabel();
        exportTypeComboBox = new JComboBox();
        exportButton = new JButton();
        jSeparator2 = new JToolBar.Separator();
        filterButton = new JButton();
        targetTableScrollPane = new JScrollPane();
        targetTable = new JTable();
        bottomNavigationPanel = new JPanel();
        totaPageslLabel = new JLabel();
        pagerPanel = new JPanel();
        firstPageLinkLabel = new JLabel();
        secondPageLinkLabel = new JLabel();
        thirdPageLinkLabel = new JLabel();
        fourthPageLinkLabel = new JLabel();
        fifthPageLinkLabel = new JLabel();
        goToFirstPageLinkLabel = new JLabel();
        goToPreviousPageLabel = new JLabel();
        goToNextPageLinkLabel = new JLabel();
        goToLastPageLinkLabel = new JLabel();
        gotoPageLabel = new JLabel();
        gotoPageTextField = new JTextField();
        goButtonLabel = new JLabel();

        ImageIcon image = null;
        
        setLayout(new GridBagLayout());

        topNavigationPanel.setMinimumSize(new Dimension(100, 30));
        topNavigationPanel.setPreferredSize(new Dimension(710, 30));
        topNavigationPanel.setLayout(new GridBagLayout());

        previousPageLabel.setFont(new Font("Tahoma", 1, 11)); 
        previousPageLabel.setForeground(new Color(0, 0, 255));
        previousPageLabel.setText("");
        previousPageLabel.setToolTipText("Go to Previous Page");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "previousPage_normal.png"));
        previousPageLabel.setIcon(image);
        previousPageLabel.addMouseListener(this);
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 2, 0, 0);
        topNavigationPanel.add(previousPageLabel, gridBagConstraints);

        rowsPerPageLabel.setText("Rows per Page:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 20, 0, 2);
        topNavigationPanel.add(rowsPerPageLabel, gridBagConstraints);

        rowsPerPageTextField.setFont(new Font("Tahoma", 1, 11));
        rowsPerPageTextField.setForeground(new Color(0, 0, 255));
        rowsPerPageTextField.setHorizontalAlignment(JTextField.CENTER);
        rowsPerPageTextField.setText("" + MIN_RECORDS_PER_PAGE);
        rowsPerPageTextField.setMinimumSize(new Dimension(60, 20));
        rowsPerPageTextField.setPreferredSize(new Dimension(60, 20));
        rowsPerPageTextField.addKeyListener(this);
        rowsPerPageTextField.addFocusListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        topNavigationPanel.add(rowsPerPageTextField, gridBagConstraints);

        refreshButton.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "reload_green.png"));
		refreshButton.setIcon(image);
		refreshButton.addActionListener(this);
		refreshButton.setFocusable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        topNavigationPanel.add(refreshButton, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        topNavigationPanel.add(hiddenLabel_01, gridBagConstraints);

        showActionsToolbarCheckBox.setText("Show Actions");
        showActionsToolbarCheckBox.setSelected(true);
        showActionsToolbarCheckBox.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 1, 0, 20);
        topNavigationPanel.add(showActionsToolbarCheckBox, gridBagConstraints);

        nextPageLabel.setFont(new Font("Tahoma", 1, 11));
        nextPageLabel.setForeground(new Color(0, 0, 255));
        nextPageLabel.setText("");
        nextPageLabel.setToolTipText("Go to Next Page");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "nextPage_normal.png"));
        nextPageLabel.setIcon(image);
        nextPageLabel.addMouseListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(0, 0, 0, 2);
        topNavigationPanel.add(nextPageLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        add(topNavigationPanel, gridBagConstraints);

        tablePanel.setLayout(new GridBagLayout());

        actionsToolBar.setFloatable(false);
        actionsToolBar.setRollover(true);

        addNewRecordButton.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "add_plus.png"));
        addNewRecordButton.setIcon(image);
        addNewRecordButton.addActionListener(this);
        addNewRecordButton.setFocusable(false);
        addNewRecordButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addNewRecordButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        actionsToolBar.add(addNewRecordButton);

        editRecordButton.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "editor_area.gif"));
        editRecordButton.setIcon(image);
        editRecordButton.addActionListener(this);
        editRecordButton.setFocusable(false);
        editRecordButton.setHorizontalTextPosition(SwingConstants.CENTER);
        editRecordButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        actionsToolBar.add(editRecordButton);

        deleteRecordButton.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "delete_edit.gif"));
        deleteRecordButton.setIcon(image);
        deleteRecordButton.addActionListener(this);
        deleteRecordButton.setFocusable(false);
        deleteRecordButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteRecordButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        actionsToolBar.add(deleteRecordButton);
        actionsToolBar.add(jSeparator1);

        exportAsLabel.setText("Export As ... ");
        actionsToolBar.add(exportAsLabel);

        exportTypeComboBox.setModel(
    		new DefaultComboBoxModel(
    			new String[] { 
    				TableDataExportTypeEnum.INSERT_STATEMENT.getDescription(),
    				TableDataExportTypeEnum.SQL_LOADER.getDescription(),
    				TableDataExportTypeEnum.TEXT.getDescription(),
    				TableDataExportTypeEnum.CSV.getDescription(),
    				TableDataExportTypeEnum.HTML.getDescription(),
    				TableDataExportTypeEnum.EXCEL.getDescription(),
    				TableDataExportTypeEnum.XML.getDescription()
    			}
    		)
    	);
        exportTypeComboBox.setMaximumSize(new Dimension(150, 18));
        exportTypeComboBox.setMinimumSize(new Dimension(150, 18));
        exportTypeComboBox.setPreferredSize(new Dimension(150, 18));
        actionsToolBar.add(exportTypeComboBox);

        exportButton.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "export_wiz.gif"));
        exportButton.setIcon(image);
        exportButton.addActionListener(this);
        exportButton.setFocusable(false);
        exportButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exportButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        actionsToolBar.add(exportButton);
        actionsToolBar.add(jSeparator2);

        filterButton.setText("Filter");
        filterButton.addActionListener(this);
        filterButton.setFocusable(false);
        filterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        filterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        actionsToolBar.add(filterButton);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        tablePanel.add(actionsToolBar, gridBagConstraints);

        targetTable.setAutoCreateRowSorter(true);
        targetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        targetTable.setAutoscrolls(true);
        targetTableScrollPane.setViewportView(targetTable);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        tablePanel.add(targetTableScrollPane, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tablePanel, gridBagConstraints);

        bottomNavigationPanel.setMinimumSize(new Dimension(100, 30));
        bottomNavigationPanel.setPreferredSize(new Dimension(710, 30));
        bottomNavigationPanel.setLayout(new GridBagLayout());

        totaPageslLabel.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        bottomNavigationPanel.add(totaPageslLabel, gridBagConstraints);

        pagerPanel.setLayout(new GridBagLayout());

        firstPageLinkLabel.setText("");
        firstPageLinkLabel.addMouseListener(this);
        firstPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 4);
        pagerPanel.add(firstPageLinkLabel, gridBagConstraints);

        secondPageLinkLabel.setText("");
        secondPageLinkLabel.addMouseListener(this);
        secondPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 4);
        pagerPanel.add(secondPageLinkLabel, gridBagConstraints);

        thirdPageLinkLabel.setText("");
        thirdPageLinkLabel.addMouseListener(this);
        thirdPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 4);
        pagerPanel.add(thirdPageLinkLabel, gridBagConstraints);

        fourthPageLinkLabel.setText("");
        fourthPageLinkLabel.addMouseListener(this);
        fourthPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 4);
        pagerPanel.add(fourthPageLinkLabel, gridBagConstraints);

        fifthPageLinkLabel.setText("");
        fifthPageLinkLabel.addMouseListener(this);
        fifthPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 4, 0, 4);
        pagerPanel.add(fifthPageLinkLabel, gridBagConstraints);

        goToFirstPageLinkLabel.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "first_page.png"));
        goToFirstPageLinkLabel.setIcon(image);
        goToFirstPageLinkLabel.addMouseListener(this);
        goToFirstPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 2, 0, 6);
        pagerPanel.add(goToFirstPageLinkLabel, gridBagConstraints);

        goToPreviousPageLabel.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "previous_page.png"));
        goToPreviousPageLabel.setIcon(image);
        goToPreviousPageLabel.setFont(new Font("Tahoma", 1, 11));
        goToPreviousPageLabel.addMouseListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 2, 0, 6);
        pagerPanel.add(goToPreviousPageLabel, gridBagConstraints);

        goToNextPageLinkLabel.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "next_page.png"));
        goToNextPageLinkLabel.setIcon(image);
        goToNextPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        goToNextPageLinkLabel.addMouseListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 6, 0, 2);
        pagerPanel.add(goToNextPageLinkLabel, gridBagConstraints);

        goToLastPageLinkLabel.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "last_page.png"));
        goToLastPageLinkLabel.setIcon(image);
        goToLastPageLinkLabel.setFont(new Font("Tahoma", 1, 11));
        goToLastPageLinkLabel.addMouseListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 6, 0, 2);
        pagerPanel.add(goToLastPageLinkLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        bottomNavigationPanel.add(pagerPanel, gridBagConstraints);

        gotoPageLabel.setText("Go To ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        bottomNavigationPanel.add(gotoPageLabel, gridBagConstraints);

        gotoPageTextField.setHorizontalAlignment(JTextField.RIGHT);
        gotoPageTextField.setMinimumSize(new Dimension(60, 20));
        gotoPageTextField.setPreferredSize(new Dimension(60, 20));
        gotoPageTextField.addKeyListener(this);
        gotoPageTextField.addFocusListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        bottomNavigationPanel.add(gotoPageTextField, gridBagConstraints);

        goButtonLabel.setText("");
        image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "next.png"));
        goButtonLabel.setIcon(image);
        goButtonLabel.addMouseListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        bottomNavigationPanel.add(goButtonLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(bottomNavigationPanel, gridBagConstraints);
    }


    // Variables declaration
    private JToolBar actionsToolBar;
    private JButton addNewRecordButton;
    private JPanel bottomNavigationPanel;
    private JButton deleteRecordButton;
    private JButton editRecordButton;
    private JLabel exportAsLabel;
    private JButton exportButton;
    private JComboBox exportTypeComboBox;
    private JLabel fifthPageLinkLabel;
    private JLabel firstPageLinkLabel;
    private JLabel fourthPageLinkLabel;
    private JLabel goButtonLabel;
    private JLabel goToFirstPageLinkLabel;
    private JLabel goToLastPageLinkLabel;
    private JLabel goToNextPageLinkLabel;
    private JLabel goToPreviousPageLabel;
    private JLabel gotoPageLabel;
    private JTextField gotoPageTextField;
    private JLabel hiddenLabel_01;
    private JButton filterButton;
    private JToolBar.Separator jSeparator1;
    private JToolBar.Separator jSeparator2;
    private JLabel nextPageLabel;
    private JPanel pagerPanel;
    private JLabel previousPageLabel;
    private JButton refreshButton;
    private JLabel rowsPerPageLabel;
    private JTextField rowsPerPageTextField;
    private JLabel secondPageLinkLabel;
    private JCheckBox showActionsToolbarCheckBox;
    private JPanel tablePanel;
    private JTable targetTable;
    private JScrollPane targetTableScrollPane;
    private JLabel thirdPageLinkLabel;
    private JPanel topNavigationPanel;
    private JLabel totaPageslLabel;
    // End of variables declaration

	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(showActionsToolbarCheckBox)){
			if(showActionsToolbarCheckBox.isSelected()){
				actionsToolBar.setVisible(true);
			}else{
				actionsToolBar.setVisible(false);
			}
		}
		else if(e.getSource().equals(refreshButton)){
			refreshPage();
		}
		else if(e.getSource().equals(filterButton)){
			applyFilter();
		}
		else if(e.getSource().equals(editRecordButton)){
			editRecord();
		}
		else if(e.getSource().equals(exportButton)){
			TableDataExportTypeEnum dataExportTypeEnum = TableDataExportTypeEnum.getTypeEnumByDescription(
					exportTypeComboBox.getSelectedItem().toString());
			String fileName = getExportOutptFileName(dataExportTypeEnum);
			if(!StringUtil.hasValidContent(fileName)){
				return ;
			}
			StringBuffer queryBuffer = new StringBuffer(getQueryString());
			queryBuffer.replace(queryBuffer.indexOf("?"), queryBuffer.indexOf("?")+1, ""+ paginationResult.getStartRow())
				.replace(queryBuffer.lastIndexOf("?"), queryBuffer.lastIndexOf("?")+1, ""+ paginationResult.getEndRow());
			if(TableDataExportTypeEnum.INSERT_STATEMENT.equals(dataExportTypeEnum) 
					|| TableDataExportTypeEnum.SQL_LOADER.equals(dataExportTypeEnum)){
				if(databaseTable == null){
		        	return;
		        }
				/*try {
					dataExportService.exportData(
							databaseTable.getSchemaName(), databaseTable.getModelName(), 
							dataExportTypeEnum, fileName, queryBuffer.toString());
				} catch (ApplicationException e1) {
					e1.printStackTrace();
				}*/
	        	return;
			}
			/*try {
				dataExportService.exportData(
						(databaseTable != null) ? databaseTable.getSchemaName() : "", 
								(databaseTable != null) ? databaseTable.getModelName() : "",
										dataExportTypeEnum, fileName, queryBuffer.toString());
			} catch (ApplicationException e1) {
				e1.printStackTrace();
			}*/
		}
	}
	
	private String getExportOutptFileName(TableDataExportTypeEnum dataExportTypeEnum){
		ExtensionFileFilter filter = new ExtensionFileFilter(new String[] { dataExportTypeEnum.getExtension()}, 
				dataExportTypeEnum.getDescription());
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int opt = chooser.showOpenDialog(getParentFrame());
		if(JFileChooser.APPROVE_OPTION == opt){
			File selectedFile = chooser.getSelectedFile();
			if(selectedFile != null){
				String fileName = selectedFile.getAbsolutePath();
				if(!fileName.endsWith(dataExportTypeEnum.getExtension())){
					fileName += dataExportTypeEnum.getExtension();
				}
				return fileName;
			}
		}
		return null;
	}

	private void refreshPage(int pageNumber) {
		int recordCount = MIN_RECORDS_PER_PAGE;
		try{
			recordCount = Integer.parseInt(rowsPerPageTextField.getText());
			if(recordCount < MIN_RECORDS_PER_PAGE){
				recordCount = MIN_RECORDS_PER_PAGE;
			} else if(recordCount > MAX_RECORDS_PER_PAGE){
				recordCount = MAX_RECORDS_PER_PAGE;
			}
		}catch(NumberFormatException nfe){
			DisplayUtils.displayMessage(getParentFrame(), "Invalid Number : " + rowsPerPageTextField.getText()
					+ "\nChanging the page size to default [ " + MIN_RECORDS_PER_PAGE + " ].", DisplayTypeEnum.WARN);
			rowsPerPageTextField.setText("" + MIN_RECORDS_PER_PAGE);
		}catch(Exception ex){
			DisplayUtils.displayMessage(getParentFrame(), ex.getMessage(), DisplayTypeEnum.ERROR);
		}
		paginationResult.setRowsPerPage(recordCount);
		gotoPage(pageNumber);
	}
	
	private void refreshPage() {
		refreshPage(1);
	}

	
	public void mouseClicked(MouseEvent e) {
		if(MouseEvent.BUTTON1 == e.getButton()){
			if(e.getClickCount() == 1){
				if(e.getSource().equals(previousPageLabel) || e.getSource().equals(goToPreviousPageLabel)){
					gotoPreviousPage();
				}
				else if(e.getSource().equals(nextPageLabel) || e.getSource().equals(goToNextPageLinkLabel)){
					gotoNextPage();
				}
				else if(e.getSource().equals(goToFirstPageLinkLabel)){
					gotoPage(1);
				}
				else if(e.getSource().equals(goToLastPageLinkLabel)){
					gotoPage(paginationResult.getTotalPages());
				}
				else if(e.getSource().equals(firstPageLinkLabel)){
					int page = Integer.parseInt(firstPageLinkLabel.getText());
					gotoPage(page);
				}
				else if(e.getSource().equals(secondPageLinkLabel)){
					int page = Integer.parseInt(secondPageLinkLabel.getText());
					gotoPage(page);
				}
				else if(e.getSource().equals(thirdPageLinkLabel)){
					int page = Integer.parseInt(thirdPageLinkLabel.getText());
					gotoPage(page);
				}
				else if(e.getSource().equals(fourthPageLinkLabel)){
					int page = Integer.parseInt(fourthPageLinkLabel.getText());
					gotoPage(page);
				}
				else if(e.getSource().equals(fifthPageLinkLabel)){
					int page = Integer.parseInt(fifthPageLinkLabel.getText());
					gotoPage(page);
				}
				else if(e.getSource().equals(goButtonLabel)){
					gotoThePage();
				}
			} else if(e.getClickCount() == 2){
				if(e.getSource().equals(targetTable)){
					if(getDatabaseTable() == null)
						return;
					
					QuickEditVO vo = new QuickEditVO();
					vo.setTableName(getDatabaseTable().getModelName());
					vo.setSchemaName(getDatabaseTable().getSchemaName());
					
					int columnIndex = targetTable.getSelectedColumn();
					int rowIndex = targetTable.getSelectedRow();
					int columnCount = targetTable.getColumnCount();
					String q = "SELECT ROWID, ORA_ROWSCN FROM " + vo.getSchemaName() + "." + vo.getTableName() + " WHERE ";
					StringBuffer qbuf = new StringBuffer("SELECT ROWID, ORA_ROWSCN FROM ");
					qbuf.append(vo.getSchemaName())
						.append(".")
						.append(vo.getTableName())
						.append(" WHERE ");
						
					for (int i = 0; i < columnCount; i++) {
						Object value = targetTable.getModel().getValueAt(rowIndex, i);
						
						if(value == null){
							q += targetTable.getModel().getColumnName(i) + " IS NULL ";
							if(i != columnCount-1){
								q += "AND ";
							}
							continue;
						}
						Class clazz = targetTable.getColumnClass(i);
						if(clazz.getCanonicalName().equalsIgnoreCase("java.util.Date") 
								|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Date")
								|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Timestamp")
								|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Time")){
							SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.INSERT_DATE_FORMAT);
							if(value instanceof java.util.Date){
								java.util.Date utilDate = (java.util.Date) value;
								value = ApplicationConstants.SQL_DATE_FUNCTION + "('" +
									dateFormat.format(utilDate) + "', " + ApplicationConstants.SQL_DATE_FORMAT + ")";
							}
							q += targetTable.getModel().getColumnName(i) + " = "
								+ (
								(value != null) ? value.toString() : "") + " ";
							if(i != columnCount-1){
								q += "AND ";
							}
							continue;
						}
							
						q += targetTable.getModel().getColumnName(i) + " = '"
							+ (
							(value != null) ? value.toString() : "") + "' ";
						if(i != columnCount-1){
							q += "AND ";
						}
					}
					Connection con = null;
					try{
						con = getConnectionProperties().getDataSource().getConnection();
						Statement stmt = con.prepareStatement(q);
						ResultSet rs = stmt.executeQuery(q);
						if(rs == null)
							return;
						while(rs.next()){
							ROWID rid = (ROWID) rs.getObject("ROWID");
							if(rid != null){
								vo.setRowid(rid.stringValue());
							}
							String x = rs.getString("ORA_ROWSCN");
							if(x != null){
								vo.setOraRowscn(x);
							}
						}
					}catch(Exception ex){
						logger.error("Cannot execute query [ : " + q + "\n\tReasone: " + ex.getMessage());
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
					vo.setCurrentColumnName(targetTable.getModel().getColumnName(columnIndex));
					Object value = targetTable.getModel().getValueAt(rowIndex, columnIndex);
					vo.setCurrentColumnValue((value != null) ? value.toString() : "");
					vo.setConnectionProperties(getConnectionProperties());
					openQuickEditDialog(vo);
				}
			}
		
		
		}
	}
	
	public void gotoThePage(){
		int page = paginationResult.getCurrentPage();
		try{
			page = Integer.parseInt(gotoPageTextField.getText());
			if(page <= 0 || page > paginationResult.getTotalPages()){
				DisplayUtils.displayMessage(getParentFrame(), "Page not exists : " + gotoPageTextField.getText(), DisplayTypeEnum.ERROR);
			}else{
				gotoPage(page);
			}
		}catch(NumberFormatException nfe){
			DisplayUtils.displayMessage(getParentFrame(), "Invalid Number : " + gotoPageTextField.getText(), DisplayTypeEnum.ERROR);
			gotoPageTextField.setText("");
		}catch(Exception ex){
			DisplayUtils.displayMessage(getParentFrame(), ex.getMessage(), DisplayTypeEnum.ERROR);
		}
	}
	
	public void openQuickEditDialog(QuickEditVO vo) {
		QuickEditDialog editDialog = new QuickEditDialog(getParentFrame(), vo);
		int opt = editDialog.showDialog();
		if(opt == ApplicationConstants.APPLY_OPTION){
			refreshPage(paginationResult.getCurrentPage());
		}
	}


	public void mouseEntered(MouseEvent e) {
		if(e.getSource().equals(previousPageLabel)){
			previousPageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if(previousPageLabel.isEnabled()){
				ImageIcon image = new ImageIcon(getClass()
						.getResource(ApplicationConstants.IMAGE_PATH
								+ "previousPage_over.png"));
		        previousPageLabel.setIcon(image);
			}
		}
		else if(e.getSource().equals(nextPageLabel)){
			nextPageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if(nextPageLabel.isEnabled()){
				ImageIcon image = new ImageIcon(getClass()
						.getResource(ApplicationConstants.IMAGE_PATH
								+ "nextPage_over.png"));
				nextPageLabel.setIcon(image);
			}
		}
		else if(e.getSource().equals(goToFirstPageLinkLabel)){
			goToFirstPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(goToPreviousPageLabel)){
			goToPreviousPageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(firstPageLinkLabel)){
			firstPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(secondPageLinkLabel)){
			secondPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(thirdPageLinkLabel)){
			thirdPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(fourthPageLinkLabel)){
			fourthPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(fifthPageLinkLabel)){
			fifthPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(goToNextPageLinkLabel)){
			goToNextPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(goToLastPageLinkLabel)){
			goToLastPageLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if(e.getSource().equals(goButtonLabel)){
			goButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}



	public void mouseExited(MouseEvent e) {
		if(e.getSource().equals(previousPageLabel)){
			previousPageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ImageIcon image = new ImageIcon(getClass()
					.getResource(ApplicationConstants.IMAGE_PATH
							+ "previousPage_normal.png"));
	        previousPageLabel.setIcon(image);
		}
		else if(e.getSource().equals(nextPageLabel)){
			nextPageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ImageIcon image = new ImageIcon(getClass()
					.getResource(ApplicationConstants.IMAGE_PATH
							+ "nextPage_normal.png"));
			nextPageLabel.setIcon(image);
		}
		else if(e.getSource().equals(goToFirstPageLinkLabel)){
			goToFirstPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(goToPreviousPageLabel)){
			goToPreviousPageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(firstPageLinkLabel)){
			firstPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(secondPageLinkLabel)){
			secondPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(thirdPageLinkLabel)){
			thirdPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(fourthPageLinkLabel)){
			fourthPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(fifthPageLinkLabel)){
			fifthPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(goToNextPageLinkLabel)){
			goToNextPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(goToLastPageLinkLabel)){
			goToLastPageLinkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(e.getSource().equals(goButtonLabel)){
			goButtonLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	public void keyPressed(KeyEvent e) {
		
	}

	
	public void keyReleased(KeyEvent e) {
		if(KeyEvent.VK_ENTER == e.getKeyCode()){
			if(e.getSource().equals(gotoPageTextField)){
				gotoThePage();
			} else if(e.getSource().equals(rowsPerPageTextField)){
				refreshPage();
			}
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent e) {
		
	}


	private void applyFilter() {
		ResultFilterDialog filterDialog = new ResultFilterDialog(getParentFrame(), true, connectionProperties.getConnectionName());
		filterDialog.setFilterQuery(currentFilter);
		filterDialog.setAlwaysOnTop(true);
		filterDialog.setLocation(100, 100);
		int opt = filterDialog.showFilterDialog();
		if(opt == ApplicationConstants.APPLY_OPTION){
			currentFilter  = filterDialog.getFilterQuery();
			showTableData(currentFilter);
		}
	}


	public void editRecord(){
		TableDataEditorDialog dataEditorDialog = new TableDataEditorDialog(getParentFrame(), targetTable);
		dataEditorDialog.setSchemaName(databaseTable.getSchemaName());
		dataEditorDialog.setTableName(databaseTable.getModelName());
		dataEditorDialog.setConnectionProperties(connectionProperties);
		dataEditorDialog.setLocation(100, 100);
		int opt = dataEditorDialog.showEditorDialog();
		if(opt == ApplicationConstants.APPLY_OPTION){
			
		}
	}


}
