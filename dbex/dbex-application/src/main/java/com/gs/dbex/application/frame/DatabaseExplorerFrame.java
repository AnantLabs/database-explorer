/**
 * 
 */
package com.gs.dbex.application.frame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.fife.plaf.Office2003.Office2003LookAndFeel;
import org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.iframe.DatabaseViewerInternalFrame;
import com.gs.dbex.application.menu.MenuBarItems;
import com.gs.dbex.application.panel.StatusBar;
import com.gs.dbex.application.toolbar.ToolbarButtons;
import com.gs.utils.swing.window.WindowUtil;

import de.muntjak.tinylookandfeel.TinyLookAndFeel;

/**
 * @author sabuj.das
 *
 */
public class DatabaseExplorerFrame extends JFrame implements WindowListener{

	/**
	 *  Generated serialVersionUID
	 */
	private static final long serialVersionUID = -2179986000548903441L;
	private static final Logger logger = Logger.getLogger(DatabaseExplorerFrame.class);
	
	private JDesktopPane mainDesktopPane;
    private JMenuBar mainMenuBar;
    private JPanel mainPanel;
    private JToolBar mainToolBar;
    private ToolbarButtons toolbarButtons;
    private MenuBarItems menuBarItems;
    
    private JComboBox dbNamesComboBox;
    
    private StatusBar statusBar;

	public DatabaseExplorerFrame() {
		try {
			UIManager.setLookAndFeel(VisualStudio2005LookAndFeel.class.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Starting application.");
		initComponents();
		setInitialProperties();
		addWindowListener(this);
	}
	
	private void setInitialProperties(){
		setSize(800, 600);
		setTitle("Database Explorer");
		setIconImage((new ImageIcon(DatabaseExplorerFrame.class
				.getResource(ApplicationConstants.IMAGE_PATH + "dbex_24x24.png")))
				.getImage());
		WindowUtil.bringToCenter(this);
	}
	
	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        mainToolBar = new JToolBar();
        mainDesktopPane = new JDesktopPane();
        mainMenuBar = new JMenuBar();
        dbNamesComboBox = new JComboBox();
        
        statusBar = new StatusBar();
        toolbarButtons = new ToolbarButtons(this);
        menuBarItems = new MenuBarItems(this);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("OracleGuiMainFrame"); 

        mainPanel.setName("mainPanel"); 
        mainPanel.setLayout(new GridBagLayout());

        dbNamesComboBox.setMaximumSize(dbNamesComboBox.getPreferredSize());
        mainToolBar.setRollover(true);
        mainToolBar.setName("mainToolBar"); 
        mainToolBar.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        addToolbarComponents();
        
        mainPanel.add(mainToolBar, gridBagConstraints);

        mainDesktopPane.setName("jDesktopPane1");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainPanel.add(mainDesktopPane, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        mainPanel.add(statusBar, gridBagConstraints);
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        mainMenuBar.setName("mainMenuBar"); 
        addMenubarComponents();
        
        setJMenuBar(mainMenuBar);

        pack();
    }

	private void addMenubarComponents() {
		//mainMenuBar.add(menuBarItems.getMenu(MenuBarItems.FILE_MENU_NAME));
		menuBarItems.addMenusToMenuBar(mainMenuBar);
	}

	private void addToolbarComponents() {
		mainToolBar.add(toolbarButtons.getButton(ToolbarButtons.NEW_CONNECTION_TOOLBAR_BUTTON));
		mainToolBar.add(toolbarButtons.getButton(ToolbarButtons.REFRESH_DATABASE_TOOLBAR_BUTTON));
		mainToolBar.add(dbNamesComboBox);
		mainToolBar.addSeparator();
		mainToolBar.add(toolbarButtons.getButton(ToolbarButtons.BACKUP_DATABSE_TOOLBAR_BUTTON));
		mainToolBar.add(toolbarButtons.getButton(ToolbarButtons.EXECUITE_CURRENT_SQL_TOOLBAR_BUTTON));
		mainToolBar.add(toolbarButtons.getButton(ToolbarButtons.DB_SYNC_TOOLBAR_BUTTON));
	}

	/**
	 * @return the mainDesktopPane
	 */
	public JDesktopPane getMainDesktopPane() {
		return mainDesktopPane;
	}

	/**
	 * @param mainDesktopPane the mainDesktopPane to set
	 */
	public void setMainDesktopPane(JDesktopPane mainDesktopPane) {
		this.mainDesktopPane = mainDesktopPane;
	}

	/**
	 * @return the mainMenuBar
	 */
	public JMenuBar getMainMenuBar() {
		return mainMenuBar;
	}

	/**
	 * @param mainMenuBar the mainMenuBar to set
	 */
	public void setMainMenuBar(JMenuBar mainMenuBar) {
		this.mainMenuBar = mainMenuBar;
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the mainToolBar
	 */
	public JToolBar getMainToolBar() {
		return mainToolBar;
	}

	/**
	 * @param mainToolBar the mainToolBar to set
	 */
	public void setMainToolBar(JToolBar mainToolBar) {
		this.mainToolBar = mainToolBar;
	}

	/**
	 * @return the toolbarButtons
	 */
	public ToolbarButtons getToolbarButtons() {
		return toolbarButtons;
	}

	/**
	 * @param toolbarButtons the toolbarButtons to set
	 */
	public void setToolbarButtons(ToolbarButtons toolbarButtons) {
		this.toolbarButtons = toolbarButtons;
	}

	/**
	 * @return the menuBarItems
	 */
	public MenuBarItems getMenuBarItems() {
		return menuBarItems;
	}

	/**
	 * @param menuBarItems the menuBarItems to set
	 */
	public void setMenuBarItems(MenuBarItems menuBarItems) {
		this.menuBarItems = menuBarItems;
	}

	/**
	 * @return the dbNamesComboBox
	 */
	public JComboBox getDbNamesComboBox() {
		return dbNamesComboBox;
	}

	/**
	 * @param dbNamesComboBox the dbNamesComboBox to set
	 */
	public void setDbNamesComboBox(JComboBox dbNamesComboBox) {
		this.dbNamesComboBox = dbNamesComboBox;
	}

	/**
	 * @return the statusBar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @param statusBar the statusBar to set
	 */
	public void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		logger.info("Closing application.");
		JInternalFrame[] iFrames = mainDesktopPane.getAllFrames();
		for (JInternalFrame internalFrame : iFrames) {
			if(internalFrame == null){
				continue;
			}
			if(internalFrame instanceof DatabaseViewerInternalFrame){
				((DatabaseViewerInternalFrame) internalFrame).closeWindow();
			}
		}
		logger.info("Application Closed.");
		System.exit(0);
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
