/**
 * 
 */
package com.gs.dbex.application.event;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.fife.plaf.Office2003.Office2003LookAndFeel;
import org.fife.plaf.OfficeXP.OfficeXPLookAndFeel;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.constants.GuiCommandConstants;
import com.gs.dbex.application.dlg.ConnectionDialog;
import com.gs.dbex.application.dlg.DbexConnectionDialog;
import com.gs.dbex.application.dlg.OpenResourceDialog;
import com.gs.dbex.application.dlg.SearchObjectDialog;
import com.gs.dbex.application.dlg.StyleConfigurationDialog;
import com.gs.dbex.application.frame.DatabaseExplorerFrame;
import com.gs.dbex.application.iframe.DatabaseViewerInternalFrame;
import com.gs.dbex.application.menu.MenuBarItems;
import com.gs.dbex.application.panel.SqlQueryPanel;
import com.gs.dbex.application.panel.TableContentPanel;
import com.gs.dbex.application.panel.TableDetailsPanel;
import com.gs.dbex.application.tab.ButtonTabComponent;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.impl.DatabaseConnectionServiceImpl;
import com.gs.utils.swing.window.WindowUtil;
import com.gs.utils.text.StringUtil;

import de.muntjak.tinylookandfeel.TinyLookAndFeel;

/**
 * @author sabuj.das
 *
 */
public class ApplicationEventHandler implements ActionListener,
		GuiCommandConstants {

	private static final Logger logger = Logger.getLogger(ApplicationEventHandler.class);
	
	private Component parent, sourceForm;
	private Object data;
	private DatabaseConnectionService connectionService;
	
	public ApplicationEventHandler() {
		connectionService = new DatabaseConnectionServiceImpl();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e != null){
			String cmd = e.getActionCommand();
			if(NEW_CONNECTION_ACT_CMD.equals(cmd)){
				//ConnectionDialog dialog = new ConnectionDialog((JFrame)parent, true);
				DbexConnectionDialog dialog = new DbexConnectionDialog((JFrame)parent, true);
				WindowUtil.bringCenterTo(dialog, parent);
				dialog.setVisible(true);
			}else if(CREATE_CONNECTION_ACT_CMD.equals(cmd)){
				Runnable connRun = new Runnable(){
					public void run() {
						final DbexConnectionDialog dlg = (DbexConnectionDialog) sourceForm;
						//dlg.disableButtons(true);
						DatabaseConnectionService connectionService = DbexServiceBeanFactory.getBeanFactory().getDatabaseConnectionService();
						boolean connected = false;
						if(connectionService != null){
							try {
								ConnectionProperties properties = (ConnectionProperties)getData();
								connected = connectionService.connectToDatabase(properties);
							} catch (DbexException e) {
								DisplayUtils.displayMessage(e.getExceptionMessage());
							}
						}
						final DatabaseExplorerFrame frame = (DatabaseExplorerFrame) parent;
						frame.getStatusBar().getCurrentStatusLabel().setIcon(new ImageIcon(this.getClass()
								.getResource(ApplicationConstants.IMAGE_PATH + "loading.gif")));
						frame.getStatusBar().getCurrentStatusLabel().setText("Connecting to Database. Please wait...");
						if(connected){
							logger.info("Connection Successful !!!");
							DatabaseViewerInternalFrame iFrame = new DatabaseViewerInternalFrame(frame, (ConnectionProperties) data);
							iFrame.setVisible(true);
							frame.getMainDesktopPane().add(iFrame);
							((DbexConnectionDialog)getSourceForm()).dispose();
						} else {
							logger.info("Connection Failed !!!");
						}
						frame.getStatusBar().getCurrentStatusLabel().setIcon(null);
						frame.getStatusBar().getCurrentStatusLabel().setText("");
					}
				};
				new Thread(connRun).start();
			}else if(TEST_CONNECTION_ACT_CMD.equals(cmd)){
				Runnable testConnRun = new Runnable(){
					
					public void run() {
						ConnectionDialog dlg = (ConnectionDialog) sourceForm;
						//dlg.disableButtons(true);
						DatabaseExplorerFrame frame = (DatabaseExplorerFrame) parent;
						frame.getStatusBar().getCurrentStatusLabel().setIcon(new ImageIcon(this.getClass()
								.getResource(ApplicationConstants.IMAGE_PATH + "loading.gif")));
						frame.getStatusBar().getCurrentStatusLabel().setText("Connecting to Database. Please wait...");
						
						/*if(data != null){
							if(data instanceof ConnectionProperties){
								try{
									boolean b = connectionService.testConnection((ConnectionProperties) data);
									String success = "SUCCESSFUL";
									if(!b){
										success = "FAILED";
									}
									DisplayUtils.displayMessage(parent, success, DisplayTypeEnum.INFO);
								}catch(ApplicationException ex){
									
								}
							}
						}
						dlg.disableButtons(false);*/
						frame.getStatusBar().getCurrentStatusLabel().setIcon(null);
						frame.getStatusBar().getCurrentStatusLabel().setText("");
					}
				};
				new Thread(testConnRun).start();
			} else if(VIEW_TABLE_DETAILS_ACT_CMD.equals(cmd)){
				
			} else if(SHOW_TOOLBAR_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				if(f.getMenuBarItems().getMenu(MenuBarItems.VIEW_MENU_NAME).getItem(0).isSelected()){
					f.getMainToolBar().setVisible(true);
				}else{
					f.getMainToolBar().setVisible(false);
				}
			} else if(SHOW_STATBAR_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				if(f.getMenuBarItems().getMenu(MenuBarItems.VIEW_MENU_NAME).getItem(1).isSelected()){
					f.getStatusBar().setVisible(true);
				}else{
					f.getStatusBar().setVisible(false);
				}
			} else if(SYS_LnF_ACT_CMD.equals(cmd)){
				updateLnF(UIManager.getSystemLookAndFeelClassName());
			} else if(METAL_ACT_CMD.equals(cmd)){
				updateLnF(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if(FOREST_ACT_CMD.equals(cmd)){
				updateLnF(TinyLookAndFeel.class.getCanonicalName());
			} else if(OFFICE_2003_ACT_CMD.equals(cmd)){
				updateLnF(Office2003LookAndFeel.class.getCanonicalName());
			}
			else if(OFFICE_XP_ACT_CMD.equals(cmd)){
				updateLnF(OfficeXPLookAndFeel.class.getCanonicalName());
			}
			else if(CASCADE_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane != null){
					cascadeWindows(desktopPane);
				}
			} else if(TILES_2003_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane != null){
					tileWindows(desktopPane);
				}
			} else if(OPEN_RESS_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
				openResource((JFrame) getParent(), dbIframe, false);
			} else if(TABLE_CONTENT_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
				openResource((JFrame) getParent(), dbIframe, true);
			} else if(TABLE_COLUMN_CONTENT_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
				openResource((JFrame) getParent(), dbIframe, true);
			} else if(NEW_QUERY_TAB_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
				SqlQueryPanel queryPanel = new SqlQueryPanel(null, dbIframe.getConnectionProperties());
				dbIframe.getDbDetailsTabbedPane().addTab("SQL", queryPanel);
				int n = dbIframe.getDbDetailsTabbedPane().getTabCount();
				dbIframe.getDbDetailsTabbedPane().setTabComponentAt(n - 1,
		                new ButtonTabComponent(dbIframe.getDbDetailsTabbedPane(), new ImageIcon(this.getClass()
		        				.getResource(ApplicationConstants.IMAGE_PATH + "executesql.gif"))));
				dbIframe.getDbDetailsTabbedPane().setSelectedIndex(n - 1);
			} else if(STYLE_CFG_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				/*JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}*/
				// TODO:
				StyleConfigurationDialog configurationDialog = new StyleConfigurationDialog(f, true);
				WindowUtil.bringCenterTo(configurationDialog, f);
				configurationDialog.setVisible(true);
			} else if(SEARCH_ITEM_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
				SearchObjectDialog dialog = new SearchObjectDialog(f, false,
						dbIframe.getConnectionProperties(), dbIframe);
				dialog.setVisible(true);
			} else if(SEARCH_TABLE_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
			} else if(SEARCH_COLUMN_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
			} else if(SEARCH_DATA_ACT_CMD.equals(cmd)){
				DatabaseExplorerFrame f = (DatabaseExplorerFrame) getParent();
				JDesktopPane desktopPane = f.getMainDesktopPane();
				if(desktopPane == null){
					return;
				}
				JInternalFrame iFrame = desktopPane.getSelectedFrame();
				if(iFrame == null){
					return;
				}
				DatabaseViewerInternalFrame dbIframe = null;
				if(iFrame instanceof DatabaseViewerInternalFrame){
					dbIframe = (DatabaseViewerInternalFrame) iFrame;
				}
				if(dbIframe == null){
					return;
				}
			}
		}
	}
	
	
	private void showContent(DatabaseViewerInternalFrame dbIframe, Table table) {
		DatabaseViewerInternalFrame iFrame = dbIframe;
		if(iFrame != null){
			boolean tableOpened = false;
			int selectedTabIndex = -1;
			int tabCount = iFrame.getDbDetailsTabbedPane().getTabCount();
			for (int i = 0; i < tabCount; i++) {
				Component tabComponent = iFrame.getDbDetailsTabbedPane().getComponentAt(i);
				if(tabComponent instanceof TableDetailsPanel){
					TableDetailsPanel ti = (TableDetailsPanel) tabComponent;
					if(ti != null){
						if(table.getModelName().equals(ti.getTableName())){
							tableOpened = true;
							selectedTabIndex = i;
							break;
						}
					}
				}
			}
			if(!tableOpened){
				TableContentPanel contentPanel = new TableContentPanel(
						table.getSchemaName(), table.getModelName(), dbIframe.getConnectionProperties(), table	
					);
					contentPanel.setParentFrame(dbIframe.getParentFrame());
					iFrame.getDbDetailsTabbedPane().addTab(ApplicationConstants.CONTENT_TEXT + table.getModelName(), contentPanel);
					int n = iFrame.getDbDetailsTabbedPane().getTabCount();
					iFrame.getDbDetailsTabbedPane().setTabComponentAt(n - 1,
			                new ButtonTabComponent(iFrame.getDbDetailsTabbedPane(), new ImageIcon(getClass()
			        				.getResource(ApplicationConstants.IMAGE_PATH + "sampleContents.gif"))));
					iFrame.getDbDetailsTabbedPane().setSelectedIndex(n-1);
					iFrame.getDbDetailsTabbedPane().updateUI();
			}else{
				// call the refresh method of this tab
			}
			if(selectedTabIndex > -1){
				iFrame.getDbDetailsTabbedPane().setSelectedIndex(selectedTabIndex);
				iFrame.getDbDetailsTabbedPane().updateUI();
			}
		}
	}
	
	private void openResource(JFrame parent, DatabaseViewerInternalFrame dbIframe, boolean isTableContent) {
		OpenResourceDialog openResourceDialog = new OpenResourceDialog(
				parent, dbIframe.getSchemaNameList(), dbIframe.getTableNameList(), dbIframe
			);
		if(isTableContent)
			openResourceDialog.setTitle("Open Content");
		else
			openResourceDialog.setTitle("Open Resource");
		
		int opt = openResourceDialog.showOpenDialog();
		if(ApplicationConstants.APPLY_OPTION == opt){
			String schemaName = openResourceDialog.getSelectedSchemaName();
			String tableName =  openResourceDialog.getSelectedTableName();
			if(StringUtil.hasValidContent(schemaName) && StringUtil.hasValidContent(tableName)){
				OracleDbGrabber dbGrabber = new OracleDbGrabber();
				Connection con = null;
				try {
					con = dbIframe.getConnectionProperties().getDataSource().getConnection();
					Table table = dbGrabber.grabTable(con, schemaName, tableName, ReadDepthEnum.DEEP);
					if(table != null){
						if(!isTableContent)
							openTableDetails(dbIframe, table);
						else
							showContent(dbIframe, table);
					}
				} catch (SQLException e) {
					DisplayUtils.displayMessage(parent, e.getMessage(), DisplayTypeEnum.ERROR);
				}
				finally{
					if(con != null){
						try {
							con.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void openTableDetails(DatabaseViewerInternalFrame dbIframe, Table table) {
		DatabaseViewerInternalFrame iFrame = dbIframe;
		if(iFrame != null){
			boolean tableOpened = false;
			int selectedTabIndex = -1;
			int tabCount = iFrame.getDbDetailsTabbedPane().getTabCount();
			for (int i = 0; i < tabCount; i++) {
				Component tabComponent = iFrame.getDbDetailsTabbedPane().getComponentAt(i);
				if(tabComponent instanceof TableDetailsPanel){
					TableDetailsPanel ti = (TableDetailsPanel) tabComponent;
					if(ti != null){
						if(table.getModelName().equals(ti.getTableName())){
							tableOpened = true;
							selectedTabIndex = i;
							break;
						}
					}
				}
			}
			if(!tableOpened){
				TableDetailsPanel panel = new TableDetailsPanel((JFrame) getParent(), 
						table, iFrame.getConnectionProperties());
				iFrame.getDbDetailsTabbedPane().addTab(table.getModelName(), panel);
				int n = iFrame.getDbDetailsTabbedPane().getTabCount();
				iFrame.getDbDetailsTabbedPane().setTabComponentAt(n - 1,
		                new ButtonTabComponent(iFrame.getDbDetailsTabbedPane(), 
		                new ImageIcon(DatabaseViewerInternalFrame.class
		        				.getResource(ApplicationConstants.IMAGE_PATH + "table.gif"))));
				selectedTabIndex = iFrame.getDbDetailsTabbedPane().getTabCount() - 1;
			}else{
				// call the refresh method of this tab
			}
			if(selectedTabIndex > -1){
				iFrame.getDbDetailsTabbedPane().setSelectedIndex(selectedTabIndex);
				iFrame.getDbDetailsTabbedPane().updateUI();
			}
		}
	}
	
	
	public void updateLnF(String lnfName){
		try {
			UIManager.setLookAndFeel(lnfName);
        } catch (Exception ex) {
        	try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
        }
        
        SwingUtilities.updateComponentTreeUI((JFrame)getParent());
	}
	
	
	public void cascadeWindows( JDesktopPane desktopPane, int layer ) {
	    JInternalFrame[] frames = desktopPane.getAllFramesInLayer( layer );
	    if ( frames.length == 0) return;
	 
	    cascadeWindows( frames, desktopPane.getBounds(), 24 );
	}
	public void cascadeWindows( JDesktopPane desktopPane ) {
	    JInternalFrame[] frames = desktopPane.getAllFrames();
	    if ( frames.length == 0) return;
	 
	    cascadeWindows( frames, desktopPane.getBounds(), 24 );
	}
	private void cascadeWindows( JInternalFrame[] frames, Rectangle dBounds, int separation ) {
	    int margin = frames.length*separation + separation;
	    int width = dBounds.width - margin;
	    int height = dBounds.height - margin;
	    for ( int i = 0; i < frames.length; i++) {
	        frames[i].setBounds( separation + dBounds.x + i*separation,
	                             separation + dBounds.y + i*separation,
	                             width, height );
	    }
	}

	public void tileWindows(JDesktopPane desktopPane) {
        
        // How many frames do we have?
        JInternalFrame[] allframes = desktopPane.getAllFrames();
        int count = allframes.length;
        if (count == 0) return;
        
        // Determine the necessary grid size
        int sqrt = (int)Math.sqrt(count);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < count) {
            cols++;
            if (rows * cols < count) {
                rows++;
            }
        }
        
        // Define some initial values for size & location.
        Dimension size = desktopPane.getSize();
        
        int w = size.width / cols;
        int h = size.height / rows;
        int x = 0;
        int y = 0;
        
        // Iterate over the frames, deiconifying any iconified frames and then
        // relocating & resizing each.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                JInternalFrame f = allframes[(i * cols) + j];
                
                if (!f.isClosed() && f.isIcon()) {
                    try {
                        f.setIcon(false);
                    } catch (PropertyVetoException ignored) {}
                }
                
                desktopPane.getDesktopManager().resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // start the next row
            x = 0;
        }
    }

	
	/**
	 * @return the parent
	 */
	public Component getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Component parent) {
		this.parent = parent;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public Component getSourceForm() {
		return sourceForm;
	}

	public void setSourceForm(Component sourceForm) {
		this.sourceForm = sourceForm;
	}

	public DatabaseConnectionService getConnectionService() {
		return connectionService;
	}

	public void setConnectionService(DatabaseConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	

}
