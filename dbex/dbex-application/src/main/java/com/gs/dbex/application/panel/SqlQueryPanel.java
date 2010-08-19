/*
 * SqlQueryPanel.java
 *
 * Created on Aug 11, 2009, 3:37:45 PM
 */

package com.gs.dbex.application.panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import org.omg.CORBA.portable.ApplicationException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.event.command.GuiEventHandler;
import com.gs.dbex.application.sql.SyntaxHighlighter;
import com.gs.dbex.application.sql.processor.SqlProcessor;
import com.gs.dbex.application.sql.text.WrapEditorKit;
import com.gs.dbex.application.table.model.ResultSetTableModelFactory;
import com.gs.dbex.application.thread.QueryExecutionThread;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.application.util.MenuBarUtil;
import com.gs.dbex.application.util.SwingUtilities;
import com.gs.dbex.common.SqlQuery;
import com.gs.dbex.common.enums.QueryTypeEnum;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.utils.io.FileRWUtil;
import com.gs.utils.io.IOUtil;
import com.gs.utils.swing.file.ExtensionFileFilter;
import com.gs.utils.swing.file.FileBrowserUtil;
import com.gs.utils.text.StringUtil;

/**
 * 
 * @author Green Moon
 */
public class SqlQueryPanel extends JPanel implements ActionListener, CaretListener, FocusListener,
UndoableEditListener, HyperlinkListener {

	private JFrame parentFrame;
	public static final Font DEFAULT_TEXT_FONT =
        new Font(Font.MONOSPACED,
            Font.PLAIN, 12);

	private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
	
	private Font bitstreamFont;
	private JMenuItem runSelectionMenuItem;
	private JButton queryFontButton;
	private QueryExecutionService queryExecutionService;
	private UndoManager undoManager = new UndoManager();
	private JComponent parentComponent;
	private SqlProcessor sqlProcessor;
	
	private Runnable queryExecutionRunner;
	private Thread queryExecutionThread;
	
	QueryExecutionThread t;

	/** Creates new form SqlQueryPanel */
	public SqlQueryPanel(JComponent parentComponent, ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
		this.parentComponent = parentComponent;
		try {
			bitstreamFont = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/VeraMono.ttf"));
			bitstreamFont = new Font(bitstreamFont.getFontName(),
            Font.PLAIN, 12);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setConnectionProperties(getConnectionProperties());
		ResultSetTableModelFactory factory = null;
		try {
			factory = new ResultSetTableModelFactory(
					getConnectionProperties().getDataSource().getConnection());
		} catch (SQLException e) {
			DisplayUtils.displayMessage(getParentFrame(), e.getMessage(), DisplayTypeEnum.ERROR);
		}
		setFactory(factory);
		Connection con = null;
		try {
			con = getConnectionProperties().getDataSource().getConnection();
			sqlProcessor = new SqlProcessor();
			sqlProcessor.installServiceKeywords();//con.getMetaData(), "%", connectionProperties.getDatabaseName());
			sqlProcessor.installServiceKeywords(con.getMetaData());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
		initComponents();
		
		List<JComponent> enb = new ArrayList<JComponent>();
		List<JComponent> dsb = new ArrayList<JComponent>();
		
		enb.add(stopExecutionButton);
		enb.add(stopExecutionMenuItem);
		
		dsb.add(runQueryButton);
		dsb.add(runQueryMenuItem);
		
//		t = new QueryExecutionThread(getParentFrame(),
//				getConnectionProperties(), 
//				getFactory(), 
//				queryResultTable, 
//				queryResultTabbedPane, 
//				queryLogTextArea, enb, dsb);
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		fileMenu = new JMenu("File");
		queryPopupMenu = new JPopupMenu();
		runQueryMenuItem = new JMenuItem();
		runAllMenuItem = new JMenuItem();
		runSelectionMenuItem = new JMenuItem();
		jSeparator4 = new JSeparator();
		commitMenuItem = new JMenuItem();
		rollbackMenuItem = new JMenuItem();
		jSeparator5 = new JSeparator();
		openMenuItem = new JMenuItem();
		saveMenuItem = new JMenuItem();
		saveAsMenuItem = new JMenuItem();
		clearMenuItem = new JMenuItem();
		jSeparator6 = new JSeparator();
		wrapCheckBoxMenuItem = new JCheckBoxMenuItem();
		sqlQuerySplitPane = new JSplitPane();
		queryPanel = new JPanel();
		queryToolBar = new JToolBar();
		runQueryButton = new JButton();
		runAllButton = new JButton();
		jSeparator1 = new JToolBar.Separator();
		commitButton = new JButton();
		rollbackButton = new JButton();
		jSeparator2 = new JToolBar.Separator();
		openButton = new JButton();
		saveButton = new JButton();
		saveAsButton = new JButton();
		clearButton = new JButton();
		jSeparator3 = new JToolBar.Separator();
		wrapToggleButton = new JToggleButton();
		jScrollPane1 = new JScrollPane();
		//queryTextArea = new JTextArea();
		schemaNameLabel = new JLabel("SCHEMA : ");
		schemaNamesComboBox = new JComboBox();
		fileNameLabel = new JLabel("File : ");
		fileNameTextField = new JTextField("", 8);
		
		queryResultPanel = new JPanel();
		queryResultTabbedPane = new JTabbedPane();
		queryResultTabPanel = new JPanel();
		messagePanel = new JPanel();
		queryLogToolBar = new JToolBar();
		clearLogButton = new JButton();
		jScrollPane2 = new JScrollPane();
		queryLogTextArea = new JTextArea();
		runSelectedQueryButton = new JButton();
		queryFontButton = new  JButton();
		
		editMenu = new JMenu("Edit");
		undoMenuItem  = new JMenuItem();
		redoMenuItem = new JMenuItem();
		cutMenuItem  = new JMenuItem();
		copyMenuItem  = new JMenuItem();
		pasteMenuItem = new JMenuItem();
		findMenuItem  = new JMenuItem();
		findNextMenuItem  = new JMenuItem();
		replaceMenuItem  = new JMenuItem();
		replaceNextMenuItem = new JMenuItem();
			
		printMenu = new JMenu("Print");
		printPreviewMenuItem  = new JMenuItem();
		printMenuItem = new JMenuItem();
		
		stopExecutionButton = new JButton();
		stopExecutionMenuItem = new JMenuItem("Stop ...");
		
		Icon image = null;

		runQueryMenuItem.setText("Run Last Query");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		runQueryMenuItem.setIcon(image);
		runQueryMenuItem.addActionListener(this);
		queryPopupMenu.add(runQueryMenuItem);
		
		runSelectionMenuItem.setText("Run Selection");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "sql_execute_selection.gif"));
		runSelectionMenuItem.setIcon(image);
		runSelectionMenuItem.addActionListener(this);
		queryPopupMenu.add(runSelectionMenuItem);
		
		runAllMenuItem.setText("Run All");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "sql_execute.gif"));
		runAllMenuItem.setIcon(image);
		runAllMenuItem.addActionListener(this);
		queryPopupMenu.add(runAllMenuItem);
		
		stopExecutionMenuItem.setText("Stop ...");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "nav_stop.gif"));
		stopExecutionMenuItem.setIcon(image);
		stopExecutionMenuItem.addActionListener(this);
		stopExecutionMenuItem.setEnabled(false);
		//stopExecutionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10));
		queryPopupMenu.add(stopExecutionMenuItem);
		
		queryPopupMenu.add(jSeparator4);

		commitMenuItem.setText("Commit");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "commit.gif"));
		commitMenuItem.setIcon(image);
		commitMenuItem.addActionListener(this);
		queryPopupMenu.add(commitMenuItem);

		rollbackMenuItem.setText("Rollback Last");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "rollback.png"));
		rollbackMenuItem.setIcon(image);
		rollbackMenuItem.addActionListener(this);
		queryPopupMenu.add(rollbackMenuItem);
		queryPopupMenu.add(jSeparator5);

		
		
		openMenuItem.setText("Open");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "open.gif"));
		openMenuItem.setIcon(image);
		openMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);

		saveMenuItem.setText("Save");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "save_edit.gif"));
		saveMenuItem.setIcon(image);
		saveMenuItem.addActionListener(this);
		fileMenu.add(saveMenuItem);

		saveAsMenuItem.setText("Save As...");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "saveas_edit.gif"));
		saveAsMenuItem.setIcon(image);
		saveAsMenuItem.addActionListener(this);
		fileMenu.add(saveAsMenuItem);
		
		queryPopupMenu.add(fileMenu);
		
		undoMenuItem.setText("Undo");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "undo.gif"));
		undoMenuItem.setIcon(image);
		undoMenuItem.addActionListener(this);
		editMenu.add(undoMenuItem);
		redoMenuItem.setText("Redo");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "redo.gif"));
		redoMenuItem.setIcon(image);
		redoMenuItem.addActionListener(this);
		editMenu.add(redoMenuItem);
		editMenu.addSeparator();
		cutMenuItem.setText("Cut");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "cut.gif"));
		cutMenuItem.setIcon(image);
		cutMenuItem.addActionListener(this);
		editMenu.add(cutMenuItem);
		copyMenuItem.setText("Copy");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "copy.gif"));
		copyMenuItem.setIcon(image);
		copyMenuItem.addActionListener(this);
		editMenu.add(copyMenuItem);
		pasteMenuItem.setText("Paste");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "paste.gif"));
		pasteMenuItem.setIcon(image);
		pasteMenuItem.addActionListener(this);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		findMenuItem.setText("Find");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "find.gif"));
		findMenuItem.setIcon(image);
		findMenuItem.addActionListener(this);
		editMenu.add(findMenuItem);
		findNextMenuItem.setText("Find Next");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "findnext.gif"));
		findNextMenuItem.setIcon(image);
		findNextMenuItem.addActionListener(this);
		editMenu.add(findNextMenuItem);
		replaceMenuItem.setText("Replace");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "replace.gif"));
		replaceMenuItem.setIcon(image);
		replaceMenuItem.addActionListener(this);
		editMenu.add(replaceMenuItem);
		replaceNextMenuItem.setText("Replace Next");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "replacenext.gif"));
		replaceNextMenuItem.setIcon(image);
		replaceNextMenuItem.addActionListener(this);
		editMenu.add(replaceNextMenuItem);
		
		queryPopupMenu.add(editMenu);
		queryPopupMenu.add(jSeparator6);
		
		clearMenuItem.setText("Clear");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "clear_co.gif"));
		clearMenuItem.setIcon(image);
		clearMenuItem.addActionListener(this);
		queryPopupMenu.add(clearMenuItem);

		wrapCheckBoxMenuItem.setEnabled(false);
		wrapCheckBoxMenuItem.setSelected(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "button-word-wrap.gif"));
		wrapCheckBoxMenuItem.setIcon(image);
		wrapCheckBoxMenuItem.addActionListener(this);
		wrapCheckBoxMenuItem.setText("Wrap");
		queryPopupMenu.add(wrapCheckBoxMenuItem);
		queryPopupMenu.addSeparator();
		
		printPreviewMenuItem.setText("Print Preview");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "print.gif"));
		printPreviewMenuItem.setIcon(image);
		printPreviewMenuItem.addActionListener(this);
		printMenu.add(printPreviewMenuItem); 
		printMenuItem.setText("Print ...");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.EDITOR_IMAGE_PATH + "printpreview.gif"));
		printMenuItem.setIcon(image);
		printMenuItem.addActionListener(this);
		printMenu.add(printMenuItem); 
		queryPopupMenu.add(printMenu);
		
		setLayout(new BorderLayout());

		sqlQuerySplitPane.setDividerLocation(250);
		sqlQuerySplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		queryPanel.setLayout(new GridBagLayout());

		queryToolBar.setFloatable(false);
		queryToolBar.setRollover(true);

		runQueryButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		runQueryButton.setIcon(image);
		runQueryButton.addActionListener(this);
		runQueryButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		runQueryButton
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		runQueryButton.setToolTipText("Run the last query. [F9]");
		queryToolBar.add(runQueryButton);

		runSelectedQueryButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "sql_execute_selection.gif"));
		runSelectedQueryButton.setIcon(image);
		runSelectedQueryButton.addActionListener(this);
		runSelectedQueryButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		runSelectedQueryButton
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		runSelectedQueryButton.setToolTipText("Run selected query. [F10]");
		queryToolBar.add(runSelectedQueryButton);
		runAllButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "sql_execute.gif"));
		runAllButton.setIcon(image);
		runAllButton.addActionListener(this);
		runAllButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		runAllButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		runAllButton.setToolTipText("Run all the queries. [F5]");
		queryToolBar.add(runAllButton);
		
		stopExecutionButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "nav_stop.gif"));
		stopExecutionButton.setIcon(image);
		stopExecutionButton.addActionListener(this);
		stopExecutionButton.setHorizontalTextPosition(SwingConstants.CENTER);
		stopExecutionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		stopExecutionButton.setToolTipText("Stop ...");
		stopExecutionButton.setEnabled(false);
		queryToolBar.add(stopExecutionButton);
		
		queryToolBar.add(jSeparator1);

		commitButton.setFocusable(false);
		commitButton.setToolTipText("Commit");
		commitButton.setEnabled(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "commit.gif"));
		commitButton.setIcon(image);
		commitButton.addActionListener(this);
		commitButton.setHorizontalTextPosition(SwingConstants.CENTER);
		commitButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(commitButton);

		rollbackButton.setFocusable(false);
		rollbackButton.setToolTipText("Rollback");
		rollbackButton.setEnabled(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "rollback.png"));
		rollbackButton.setIcon(image);
		rollbackButton.addActionListener(this);
		rollbackButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		rollbackButton
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(rollbackButton);
		queryToolBar.add(jSeparator2);

		openButton.setFocusable(false);
		openButton.setToolTipText("Open Query File");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "open.gif"));
		openButton.setIcon(image);
		openButton.addActionListener(this);
		openButton.setHorizontalTextPosition(SwingConstants.CENTER);
		openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(openButton);

		saveButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "save_edit.gif"));
		saveButton.setIcon(image);
		saveButton.setToolTipText("Save");
		saveButton.addActionListener(this);
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(saveButton);

		saveAsButton.setFocusable(false);
		saveAsButton.setToolTipText("Save As...");
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "saveas_edit.gif"));
		saveAsButton.setIcon(image);
		saveAsButton.addActionListener(this);
		saveAsButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		saveAsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(saveAsButton);

		clearButton.setToolTipText("Clear");
		clearButton.setFocusable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH + "clear_co.gif"));
		clearButton.setIcon(image);
		clearButton.addActionListener(this);
		clearButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		clearButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(clearButton);
		queryToolBar.add(jSeparator3);

		wrapToggleButton.setEnabled(false);
		wrapToggleButton.setSelected(false);
		image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH + "button-word-wrap.gif"));
		wrapToggleButton.setIcon(image);
		wrapToggleButton.addActionListener(this);
		wrapToggleButton.setFocusable(false);
		wrapToggleButton
				.setHorizontalTextPosition(SwingConstants.CENTER);
		wrapToggleButton
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryToolBar.add(wrapToggleButton);
		queryToolBar.add(new JToolBar.Separator());
		
		
		queryToolBar.add(schemaNameLabel);
		schemaNamesComboBox.setModel(new DefaultComboBoxModel(RESERVED_WORDS_UTIL.getSchemaNames().toArray()));
		if(RESERVED_WORDS_UTIL.getSchemaNames().size() <= 1){
			schemaNamesComboBox.setEnabled(false);
		} else {
			schemaNamesComboBox.setEnabled(true);
		}
		queryToolBar.add(schemaNamesComboBox);
		queryToolBar.add(new JToolBar.Separator());
		queryToolBar.add(fileNameLabel);
		queryToolBar.add(fileNameTextField); 
		fileNameTextField.addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				/*if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					String fileName = fileNameTextField.getText();
					if(StringUtil.hasValidContent(fileName)){
						openQueryFile(fileName);
						//openFileButton.setEnabled(true);
					} else {
						//openFileButton.setEnabled(false);
					}
				}*/
			}
			
			public void keyReleased(KeyEvent e) {
				
				String fileName = fileNameTextField.getText();
				if(StringUtil.hasValidContent(fileName)){
					openFileButton.setEnabled(true);
					if (KeyEvent.VK_ENTER == e.getKeyCode()) {
						openQueryFile(fileName);
					}
				} else {
					openFileButton.setEnabled(false);
				}
				
			}
			
			public void keyTyped(KeyEvent e) {
				
			}

		});

		openFileButton = new JButton();
		image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH + "open_file.png"));
		openFileButton.setIcon(image);
		openFileButton.setEnabled(false);
		openFileButton.setFocusable(false);
		openFileButton.addActionListener(this);
		queryToolBar.add(openFileButton);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		queryPanel.add(queryToolBar, gridBagConstraints);

		commandEditor = new SyntaxHighlighter(200,200,sqlProcessor);
		
		//SqlDocument doc = new SqlDocument();
		//doc.setTextArea(queryTextArea);
		/*queryTextArea.setColumns(20);
		queryTextArea.setLineWrap(true);
		queryTextArea.setRows(5);*/
		
		commandEditor.setMargin(new Insets(2,5,2,5));
		commandEditor.setFont(bitstreamFont);
		initKeyMap();
		
		//queryTextArea.setDocument(doc);
		/*LineNumberedBorder lineNumberedBorder = new LineNumberedBorder(LineNumberedBorder.LEFT_SIDE,
				LineNumberedBorder.RIGHT_JUSTIFY);
		queryTextArea.setFont((null != bitstreamFont) ? bitstreamFont : DEFAULT_TEXT_FONT);
		queryTextArea.setBorder(
            BorderFactory.createCompoundBorder(
              BorderFactory.createEmptyBorder(1, 1, 1, 1),
              BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createCompoundBorder(
                    lineNumberedBorder, BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(0, 8, 0, 2)
              ))
              )
              )
              );
              */
		/*commandEditor.setBorder(
            BorderFactory.createCompoundBorder(
              BorderFactory.createEmptyBorder(1, 1, 1, 1),
              BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(0, 8, 0, 2))
              )
              )
              );*/


		
		commandEditor.setComponentPopupMenu(queryPopupMenu);
		commandEditor.addKeyListener(new KeyListener() {

			
			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_F9 == e.getKeyCode()) {
					String query = getLastQuery();
					displayQueryResults(query);
				}
			}

			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			
			public void keyTyped(KeyEvent e) {

			}

		});
		
		/*AutoTextComplete atc = new AutoTextComplete(queryTextArea);
		atc.setAlwaysOnTop(true);
		atc.setItems(ApplicationConstants.SQL_KEYWORD_LIST);*/
		jScrollPane1.setViewportView(commandEditor);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		queryPanel.add(jScrollPane1, gridBagConstraints);

		sqlQuerySplitPane.setTopComponent(queryPanel);

		queryResultPanel.setLayout(new GridBagLayout());

		queryResultTabPanel.setLayout(new BorderLayout());
		queryResultTable = new JTable();
		queryResultTable.setAutoCreateRowSorter(true);
		queryResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		queryResultTable.setAutoscrolls(true);
		queryResultTabPanel.add(new JScrollPane(queryResultTable),
				BorderLayout.CENTER);
		queryResultTabbedPane.addTab("Result", queryResultTabPanel);

		messagePanel.setLayout(new GridBagLayout());

		queryLogToolBar.setRollover(true);
		queryLogToolBar.setFloatable(false);
		clearLogButton.setToolTipText("Clear Log");
		image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH + "clear_co.gif"));
		clearLogButton.setIcon(image);
		clearLogButton.setFocusable(false);
		clearLogButton.setHorizontalTextPosition(SwingConstants.CENTER);
		clearLogButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		queryLogToolBar.add(clearLogButton);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		messagePanel.add(queryLogToolBar, gridBagConstraints);

		queryLogTextArea.setColumns(20);
		queryLogTextArea.setRows(5);
		queryLogTextArea.setEditable(false);
		queryLogTextArea.setFont((null != bitstreamFont) ? bitstreamFont : DEFAULT_TEXT_FONT);
		jScrollPane2.setViewportView(queryLogTextArea);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		messagePanel.add(jScrollPane2, gridBagConstraints);

		queryResultTabbedPane.addTab("Message", messagePanel);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		queryResultPanel.add(queryResultTabbedPane, gridBagConstraints);

		sqlQuerySplitPane.setRightComponent(queryResultPanel);

		add(sqlQuerySplitPane, BorderLayout.CENTER);
	}
	
	protected void initKeyMap() {
        Keymap kMap = commandEditor.getKeymap();
        Action a = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                insertLineBreak();
            }
        };
        kMap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.SHIFT_MASK),a);
    }
	
	protected void insertLineBreak() {
        try {
            int offs = commandEditor.getCaretPosition();
            Document doc = commandEditor.getDocument();
            SimpleAttributeSet attrs;
            if (doc instanceof StyledDocument) {
                attrs = new SimpleAttributeSet( ( (StyledDocument) doc).getCharacterElement(offs).getAttributes());
            }
            else {
                attrs = new SimpleAttributeSet();
            }
            attrs.addAttribute(WrapEditorKit.LINE_BREAK_ATTRIBUTE_NAME,Boolean.TRUE);
            doc.insertString(offs, "\r", attrs);
            commandEditor.setCaretPosition(offs+1);
        }
        catch (BadLocationException ex) {
            //should never happens
            ex.printStackTrace();
        }
    }

	private JButton clearButton;
	private JButton commitButton;
	private JButton clearLogButton;
	private JCheckBoxMenuItem wrapCheckBoxMenuItem;
	private JMenuItem runQueryMenuItem;
	private JMenuItem runAllMenuItem;
	private JMenuItem commitMenuItem;
	private JMenuItem rollbackMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem clearMenuItem;
	private JPanel messagePanel;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JToolBar.Separator jSeparator1;
	private JToolBar.Separator jSeparator2;
	private JToolBar.Separator jSeparator3;
	private JSeparator jSeparator4;
	private JSeparator jSeparator5;
	private JSeparator jSeparator6;
	private JButton openButton;
	private JTextArea queryLogTextArea;
	private JToolBar queryLogToolBar;
	private JPanel queryPanel;
	private JPopupMenu queryPopupMenu;
	private JPanel queryResultPanel;
	private JPanel queryResultTabPanel;
	private JTabbedPane queryResultTabbedPane;
	//private JTextArea queryTextArea;
	private SyntaxHighlighter commandEditor;
	private JToolBar queryToolBar;
	private JButton rollbackButton;
	private JButton runAllButton;
	private JButton runQueryButton;
	private JButton runSelectedQueryButton;
	private JButton saveAsButton;
	private JButton saveButton;
	private JLabel schemaNameLabel;
	private JComboBox schemaNamesComboBox;
	private JLabel fileNameLabel;
	private JTextField fileNameTextField;
	private JSplitPane sqlQuerySplitPane;
	private JToggleButton wrapToggleButton;
	private JButton openFileButton;
	private JTable queryResultTable;
	private ResultSetTableModelFactory factory;
	
	private JMenu editMenu, fileMenu;
	private JMenuItem undoMenuItem, redoMenuItem,
				cutMenuItem, copyMenuItem, pasteMenuItem,
				findMenuItem, findNextMenuItem, replaceMenuItem, replaceNextMenuItem;
	
	private JMenu printMenu;
	private JMenuItem printPreviewMenuItem, printMenuItem;
	
	private JMenuItem stopExecutionMenuItem;
	private JButton stopExecutionButton;
	

	private GuiEventHandler guiEventHandler = new GuiEventHandler();
	private ConnectionProperties connectionProperties;

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(clearButton)
				|| e.getSource().equals(clearMenuItem)) {
			commandEditor.setText("");
		}
		if (e.getSource().equals(runQueryButton) || e.getSource().equals(runQueryMenuItem)) {
			String query = getLastQuery();
			displayQueryResults(query);
		}
		if (e.getSource().equals(runSelectedQueryButton) || e.getSource().equals(runSelectionMenuItem)) {
			List<String> queryList = getSelectedQueryList();
			if(!queryList.isEmpty())
				displayQueryResults(queryList.get(0));
		}
		if (e.getSource().equals(wrapToggleButton) || e.getSource().equals(wrapCheckBoxMenuItem)) {
			/*if(wrapToggleButton.isSelected() && wrapCheckBoxMenuItem.isSelected()){
				queryTextArea.setLineWrap(true);
				wrapToggleButton.setToolTipText("Un-Wrap");
				wrapCheckBoxMenuItem.setText("Un-Wrap");
			}else{
				queryTextArea.setLineWrap(false);
				wrapToggleButton.setToolTipText("Wrap");
				wrapCheckBoxMenuItem.setText("Wrap");
			}*/
		}
		if (e.getSource().equals(queryFontButton)){
			
		}
		if (e.getSource().equals(saveAsButton) || e.getSource().equals(saveAsMenuItem)){
			saveQueryToFile(true);
		}
		if (e.getSource().equals(saveButton) || e.getSource().equals(saveMenuItem)){
			saveQueryToFile(false);
		}
		if (e.getSource().equals(openFileButton)){
			String fileName = fileNameTextField.getText();
			if(StringUtil.hasValidContent(fileName)){
				openQueryFile(fileName);
			}
		}
		if (e.getSource().equals(openButton) || e.getSource().equals(openMenuItem)){
			browseAndOpenFile();
		}
		if (e.getSource().equals(stopExecutionButton) || e.getSource().equals(stopExecutionMenuItem)){
			stopQueryExecution();
		}
	}

	

	@SuppressWarnings("deprecation")
	private void stopQueryExecution() {
		if(queryExecutionThread != null){
			System.out.println(queryExecutionThread.getState().name());
			if(!queryExecutionThread.getState().equals(State.NEW)
					&& !queryExecutionThread.getState().equals(State.TERMINATED)){
				queryExecutionThread.stop();
				stopExecutionButton.setEnabled(false);
				stopExecutionMenuItem.setEnabled(false);
				
				runQueryButton.setEnabled(true);
				runQueryMenuItem.setEnabled(true);
				runAllButton.setEnabled(true);
				runAllMenuItem.setEnabled(true);
				runSelectedQueryButton.setEnabled(true);
				runSelectionMenuItem.setEnabled(true);
			}
			System.out.println(queryExecutionThread.getState().name());
		}
		
	}

	private void saveQueryToFile(boolean saveAs) {
		File target = null;
		if(!saveAs){ // save is clicked..
			String fileName = fileNameTextField.getText();
			if(!StringUtil.hasValidContent(fileName)){
				target = FileBrowserUtil.browseToSaveFile(parentComponent, ".", 
						new ExtensionFileFilter(new String[]{"sql"}, "SQL Query files (.sql)"), Boolean.FALSE);
			} else {
				target = new File(fileName);
			}
		} else {
			target = FileBrowserUtil.browseToSaveFile(parentComponent, ".", 
					new ExtensionFileFilter(new String[]{"sql"}, "SQL Query files (.sql)"), Boolean.FALSE);
		}
		if(target != null){
			String path = target.getAbsolutePath();
			if(!path.endsWith(".sql")){
				path = path + ".sql";
			}
			target = new File(path);
			if(!target.exists()){
				//IOUtil.mkfile(path);
			} else {
				int opt = DisplayUtils.confirmOkCancel(parentFrame, "File already exists.\nDo you want to overwrite?", 
						DisplayTypeEnum.WARN);
				if(JOptionPane.CANCEL_OPTION == opt){
					return;
				}
			}
			FileRWUtil.writeAsText(target.getAbsolutePath(), commandEditor.getText());
			fileNameTextField.setText(path);
			openFileButton.setEnabled(true);
		}
	}

	public void displayQueryResults(final String q) {
		final SqlQuery sqlQuery = new SqlQuery(q);
		
		/*t.setSqlQuery(sqlQuery);
		
		dsb.add();
		dsb.add();
		dsb.add();
		dsb.add();
		*/
		synchronized (this) {
			
		
		queryExecutionRunner = new Runnable() {
			
			public void run() {
				stopExecutionButton.setEnabled(true);
				stopExecutionMenuItem.setEnabled(true);
				
				runQueryButton.setEnabled(false);
				runQueryMenuItem.setEnabled(false);
				runAllButton.setEnabled(false);
				runAllMenuItem.setEnabled(false);
				runSelectedQueryButton.setEnabled(false);
				runSelectionMenuItem.setEnabled(false);
				
				if(QueryTypeEnum.SELECT.equals(sqlQuery.getQueryType())){
					/*EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								queryResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								queryResultTable.setCellSelectionEnabled(true);
								queryResultTable.setModel(factory.getResultSetTableModel(q));
								queryResultTabbedPane.setSelectedIndex(0);
							} catch (SQLException ex) {
								JOptionPane.showMessageDialog(getParentFrame(), new String[] {
								ex.getClass().getName() + ": ", ex.getMessage() });
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(getParentFrame(), "Error in query.");
							}
						}
					});*/
					try {
						queryResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						queryResultTable.setCellSelectionEnabled(true);
						queryResultTable.setModel(factory.getResultSetTableModel(q));
						queryResultTabbedPane.setSelectedIndex(0);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(getParentFrame(), new String[] {
						ex.getClass().getName() + ": ", ex.getMessage() });
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getParentFrame(), "Error in query.");
					}
				}
				else {
					queryExecutionService = DbexServiceBeanFactory.getBeanFactory().getQueryExecutionService();
					/*try {
						int row = queryExecutionService.executeNonQuery(sqlQuery);
						queryLogTextArea.append("[ " + row + " ] rows updated.\n");
						queryResultTabbedPane.setSelectedIndex(1);
					} catch (ApplicationException ex) {
						JOptionPane.showMessageDialog(getParentFrame(), ex.getMessage());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getParentFrame(), "Error in query.");
					}*/
				}
				
				stopExecutionButton.setEnabled(false);
				stopExecutionMenuItem.setEnabled(false);
				
				runQueryButton.setEnabled(true);
				runQueryMenuItem.setEnabled(true);
				runAllButton.setEnabled(true);
				runAllMenuItem.setEnabled(true);
				runSelectedQueryButton.setEnabled(true);
				runSelectionMenuItem.setEnabled(true);
			}
		};
		
		
		queryExecutionThread = new Thread(queryExecutionRunner);
		queryExecutionThread.start();
		}
	}
	
	private void openQueryFile(String fileName) {
		File queryFile = new File(fileName);
		commandEditor.setText("");//FileRWUtil.readAsText(queryFile.getAbsolutePath()));
	}
	
	private void browseAndOpenFile() {
		File queryFile = FileBrowserUtil.openSingleFile(parentFrame, 
				new ExtensionFileFilter(new String[]{"sql"}, "SQL Query files (.sql)"), Boolean.FALSE);
		if(queryFile != null){
			fileNameTextField.setText(queryFile.getAbsolutePath());
			openFileButton.setEnabled(true);
			commandEditor.setText("");//IOUtil.readAsText(queryFile));
		}
	}
	/*public JTextArea getQueryTextArea() {
		return queryTextArea;
	}*/

	public void setQueryResultTable(JTable queryResultTable) {
		this.queryResultTable = queryResultTable;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(
			ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public ResultSetTableModelFactory getFactory() {
		return factory;
	}

	public void setFactory(ResultSetTableModelFactory factory) {
		this.factory = factory;
	}

	public List<String> getQueryList() {
		return getQueryList(commandEditor.getText());
	}

	public List<String> getQueryList(String text) {
		if (!StringUtil.hasValidContent(text))
			return new ArrayList<String>();
		List<String> queryList = new ArrayList<String>();
		BufferedReader br = null;
		boolean hasDelem = false;
		if(text.contains(";"))
			hasDelem = true;
		try {
			br = new BufferedReader(new StringReader(text));
			char[] cBuff = new char[1];
			int count = 0;
			StringBuffer qBuff = new StringBuffer();
			while ((count = br.read(cBuff, 0, cBuff.length)) >= 0) {
				char ch = cBuff[0];
				if (';' != ch) {
					if ('\r' == ch || '\n' == ch)
						qBuff.append(' ');
					else
						qBuff.append(ch);
				} else {
					queryList.add(qBuff.toString());
					qBuff = new StringBuffer();
				}
			}
			if(!hasDelem)
				queryList.add(qBuff.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(br);
		}

		return queryList;
	}

	public String getLastQuery() {
		List<String> queryList = getSelectedQueryList(); 
		if (!queryList.isEmpty()) {
			return queryList.get(0);
		}
		queryList = getQueryList();
		if (!queryList.isEmpty()) {
			return queryList.get(queryList.size() - 1);
		}
		return "";
	}

	public List<String> getSelectedQueryList() {
		return getQueryList(commandEditor.getSelectedText());
	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	
	public void caretUpdate(CaretEvent e) {
		Object src = (e == null ? commandEditor : e.getSource());
        int dot = (e == null ? commandEditor.getCaretPosition() : e.getDot());

        int row = 0;
        int column = 0;
        if (src == commandEditor) {
            try {
                row = SwingUtilities.getCaretRow(dot, commandEditor) + 1;
            } catch (BadLocationException e1) {
                row = -1;
            }
            try {
                column = SwingUtilities.getCaretCol(dot, commandEditor) + 1;
            } catch (BadLocationException e1) {
                column = -1;
            }
            String str = " " + row + " : " + column + "[" + dot + "]";
            //labelCaretPosition.setText(str);
        }
	}

	
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void undoableEditHappened(UndoableEditEvent e) {
		if (e == null || e.getEdit() == null) {
            return;
        }
        UndoableEdit edit = e.getEdit();
        String editName = edit.getPresentationName();
        if (editName.startsWith("style")) {
            return;
        }
        undoManager.addEdit(e.getEdit());
	}

	
	public void hyperlinkUpdate(HyperlinkEvent e) {
		// TODO Auto-generated method stub
		
	}
}
