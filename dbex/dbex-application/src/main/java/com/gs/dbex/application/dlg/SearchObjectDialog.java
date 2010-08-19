/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.gs.dbex.application.iframe.DatabaseViewerInternalFrame;
import com.gs.dbex.application.panel.SearchColumnPanel;
import com.gs.dbex.application.panel.SearchTablePanel;
import com.gs.dbex.application.util.WindowUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class SearchObjectDialog extends JDialog {
	
	private JFrame parentFrame;
	private ConnectionProperties connectionProperties;
	private DatabaseViewerInternalFrame databaseViewerInternalFrame;

    public SearchObjectDialog(Frame parent, boolean modal,
    		ConnectionProperties connectionProperties, DatabaseViewerInternalFrame dbViewerInternalFrame) {
        super(parent, modal);
        this.parentFrame = (JFrame) parent;
        this.connectionProperties = connectionProperties;
        databaseViewerInternalFrame = dbViewerInternalFrame;
        initComponents();
        setSize(650, 450);
        WindowUtil.bringCenterTo(this, parent);
    }

    private void initComponents() {

        searchTabbedPane = new JTabbedPane();
        searchTablePanel = new JPanel();
        searchColumnPanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search ...");

        searchTablePanel.setLayout(new BorderLayout());
        searchTablePanel.add(new SearchTablePanel(getParentFrame(), getConnectionProperties() ), BorderLayout.CENTER);
        searchTabbedPane.addTab("Table ... ", searchTablePanel);

        searchColumnPanel.setLayout(new BorderLayout());
        searchColumnPanel.add(new SearchColumnPanel(getParentFrame(), getConnectionProperties() ), BorderLayout.CENTER);
        searchTabbedPane.addTab("Column ... ", searchColumnPanel);

        getContentPane().add(searchTabbedPane, BorderLayout.CENTER);

        pack();
    }


    private JPanel searchColumnPanel;
    private JTabbedPane searchTabbedPane;
    private JPanel searchTablePanel;

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public DatabaseViewerInternalFrame getDatabaseViewerInternalFrame() {
		return databaseViewerInternalFrame;
	}

	public void setDatabaseViewerInternalFrame(
			DatabaseViewerInternalFrame databaseViewerInternalFrame) {
		this.databaseViewerInternalFrame = databaseViewerInternalFrame;
	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public JPanel getSearchColumnPanel() {
		return searchColumnPanel;
	}

	public JTabbedPane getSearchTabbedPane() {
		return searchTabbedPane;
	}

	public JPanel getSearchTablePanel() {
		return searchTablePanel;
	}

    
}
