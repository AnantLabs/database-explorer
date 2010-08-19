/**
 * 
 */
package com.gs.dbex.application.connection.driver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

import org.apache.log4j.Logger;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.constants.ImageConstants;
import com.gs.dbex.application.context.ApplicationCommonContext;
import com.gs.dbex.common.CommonConstants;
import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.utils.io.FileRWUtil;
import com.gs.utils.jdbc.driver.JdbcDriverManagerUtil;
import com.gs.utils.swing.file.ExtensionFileFilter;
import com.gs.utils.swing.file.FileBrowserUtil;
import com.gs.utils.swing.tree.IconCellRenderer;
import com.gs.utils.swing.tree.IconData;

/**
 * @author sabuj.das
 * 
 */
public class JdbcDriverManagerDialog extends JDialog implements ActionListener,
        TreeSelectionListener, TreeExpansionListener, MouseListener,
        TreeWillExpandListener, ImageConstants, PropertyChangeListener, ListSelectionListener {

    /**
     * serialVersionUID = -2644355150992499368L;
     */
    private static final long serialVersionUID = -2644355150992499368L;
    private static Logger logger = Logger.getLogger(JdbcDriverManagerDialog.class);
    private final ApplicationCommonContext commonContext = ApplicationCommonContext.getInstance();
    public static final ImageIcon JAR_FILE_ICON = new ImageIcon(
            JdbcDriverManagerDialog.class.getResource(DRIVER_MGR_IMG_LOC
            + JAR_IMG_NAME));
    public static final ImageIcon DRIVER_ICON = new ImageIcon(
            JdbcDriverManagerDialog.class.getResource(DRIVER_MGR_IMG_LOC + DRIVER_IMG_NAME));
    
    private int selectedOption = ApplicationConstants.CANCEL_OPTION;
    private Frame parentFrame;

    public JdbcDriverManagerDialog(Frame parent, boolean modal) {
        super(parent, modal);
        parentFrame = parent;
        initComponents();
        loadSavedProperties();
        databaseNameList.setModel(new AbstractListModel() {
            String[] strings = CommonConstants.DATABASE_TYPES;
            public int getSize() {
                return strings.length;
            }
            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        databaseNameList.setSelectedIndex(0);
    }

    private void loadSavedProperties() {
    	
	}

	@SuppressWarnings("unchecked")
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        databaseNameList = new JList();
        infoTabbedPane = new JTabbedPane();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        imageLabel = new JLabel();
        jLabel4 = new JLabel();
        databaseNameTextField = new JTextField();
        jLabel5 = new JLabel();
        driverClassTextField = new JTextField();
        jLabel6 = new JLabel();
        urlPatternTextField = new JTextField();
        jSeparator1 = new JSeparator();
        defaultValuePanel = new JPanel();
        loadJarButton = new JButton();
        removeJarButton = new JButton();
        jScrollPane2 = new JScrollPane();
        driverClassTree = new JTree();
        jLabel7 = new JLabel();
        loadingProgressBar = new JProgressBar();
        copyJarCheckBox = new JCheckBox();
        jPanel3 = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Driver Manager");
        setMinimumSize(new Dimension(630, 530));
        setPreferredSize(getMinimumSize());
        
        addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				selectedOption = ApplicationConstants.CANCEL_OPTION;
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

        mainPanel.setMinimumSize(new Dimension(620, 520));
        mainPanel.setPreferredSize(mainPanel.getMinimumSize());
        mainPanel.setLayout(new GridBagLayout());

        jLabel1.setFont(jLabel1.getFont().deriveFont(
                (jLabel1.getFont().getStyle() & ~Font.ITALIC) | Font.BOLD, 16));
        jLabel1.setText("Driver Manager");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jLabel1, gridBagConstraints);

        databaseNameList.setModel(new AbstractListModel() {

            String[] strings = {
            		DatabaseTypeEnum.ORACLE.getDescription(),
            		DatabaseTypeEnum.MYSQL.getDescription(),
            		DatabaseTypeEnum.MSSQL_2005.getDescription(),
            		DatabaseTypeEnum.OTHER.getDescription()
            };

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        databaseNameList.addPropertyChangeListener(this);
        databaseNameList.addListSelectionListener(this);
        databaseNameList.setMaximumSize(new Dimension(145, 85));
        databaseNameList.setMinimumSize(new Dimension(145, 85));
        databaseNameList.setPreferredSize(new Dimension(145, 85));
        jScrollPane1.setViewportView(databaseNameList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(jScrollPane1, gridBagConstraints);

        infoTabbedPane.setMinimumSize(new Dimension(100, 100));

        jPanel2.setLayout(new GridBagLayout());

        jLabel2.setText("Driver and Connection Information");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jLabel2, gridBagConstraints);

        imageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imageLabel.setIcon(new ImageIcon(getClass().getResource(CONNECTION_MGR_IMG_LOC + CONNECTION_MGR_IMG_NAME)));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(imageLabel, gridBagConstraints);

        jLabel4.setText("Database Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(databaseNameTextField, gridBagConstraints);

        jLabel5.setText("Driver Class Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(driverClassTextField, gridBagConstraints);

        jLabel6.setText("URL Pattern");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(urlPatternTextField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jSeparator1, gridBagConstraints);

        defaultValuePanel.setBorder(BorderFactory.createTitledBorder(" Default Values "));
        defaultValuePanel.setMinimumSize(new Dimension(100, 150));
        defaultValuePanel.setPreferredSize(new Dimension(100, 150));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
       // jPanel2.add(defaultValuePanel, gridBagConstraints);

        loadJarButton.setText("Load ...");
        loadJarButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(loadJarButton, gridBagConstraints);

        removeJarButton.setText("Remove");
        removeJarButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(removeJarButton, gridBagConstraints);

        driverClassTree.setMaximumSize(new Dimension(79, 30));
        driverClassTree.setModel(new DefaultTreeModel(null));
        driverClassTree.setCellRenderer(new IconCellRenderer());
        driverClassTree.setPreferredSize(new Dimension(79, 30));
        driverClassTree.addTreeWillExpandListener(this);
        driverClassTree.addMouseListener(this);
        driverClassTree.addTreeExpansionListener(this);
        driverClassTree.addTreeSelectionListener(this);
        jScrollPane2.setViewportView(driverClassTree);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jLabel7.setText("Loading Driver(s)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(jLabel7, gridBagConstraints);

        loadingProgressBar.setStringPainted(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel2.add(loadingProgressBar, gridBagConstraints);

        copyJarCheckBox.setSelected(true);
        copyJarCheckBox.setText("Copy JAR");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel2.add(copyJarCheckBox, gridBagConstraints);

        infoTabbedPane.addTab("Driver Info", jPanel2);
        infoTabbedPane.addTab("Database Type Map", jPanel3);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(infoTabbedPane, gridBagConstraints);

        okButton.setText("Ok");
        okButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        mainPanel.add(okButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        mainPanel.add(cancelButton, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    public int showDialog(){
    	setVisible(true);
    	return selectedOption;
    }
    
    
    
    // Code for dispatching events from components to event handlers.
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == cancelButton) {
            JdbcDriverManagerDialog.this.cancelButtonActionPerformed(evt);
        } else if (evt.getSource() == loadJarButton) {
            JdbcDriverManagerDialog.this.loadJarButtonActionPerformed(evt);
        } else if (evt.getSource() == removeJarButton) {
            JdbcDriverManagerDialog.this.removeJarButtonActionPerformed(evt);
        } else if (evt.getSource() == okButton) {
            JdbcDriverManagerDialog.this.okButtonActionPerformed(evt);
        }
    }

    public void mouseClicked(MouseEvent evt) {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeMouseClicked(evt);
        }
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mousePressed(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void treeCollapsed(TreeExpansionEvent evt) {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeTreeCollapsed(evt);
        }
    }

    public void treeExpanded(TreeExpansionEvent evt) {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeTreeExpanded(evt);
        }
    }

    public void valueChanged(TreeSelectionEvent evt) {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeValueChanged(evt);
        }
    }

    public void treeWillCollapse(TreeExpansionEvent evt)
            throws ExpandVetoException {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeTreeWillCollapse(evt);
        }
    }

    public void treeWillExpand(TreeExpansionEvent evt)
            throws ExpandVetoException {
        if (evt.getSource() == driverClassTree) {
            JdbcDriverManagerDialog.this.driverClassTreeTreeWillExpand(evt);
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
    	selectedOption = ApplicationConstants.CANCEL_OPTION;
    	dispose();
    }

    private void loadJarButtonActionPerformed(ActionEvent evt) {
        ExtensionFileFilter filter = new ExtensionFileFilter(new String[]{"jar", "zip"}, "JDBC Driver File");
        File selectedFile = FileBrowserUtil.openSingleFile(getParent(), filter,
                false, commonContext.getDriverLoaderLastAccessedDirName());
        if (selectedFile != null) {
            commonContext.setDriverLoaderLastAccessedDirName(selectedFile.getPath());
            if (copyJarCheckBox.isSelected()) {
            	File dir = new File(commonContext.getJdbcDriverDir());
            	if(!dir.getAbsolutePath().equalsIgnoreCase(selectedFile.getParent())){
            		try {
                        FileRWUtil.copy(selectedFile,
                                new File(commonContext.getJdbcDriverDir() + selectedFile.getName()));
                    } catch (IOException e) {
                        logger.error(e);
                    }
            	}
            }
            loadDriver(selectedFile);
        }
    }

    private void loadDriver(File selectedFile) {
        topNode = new DefaultMutableTreeNode(
                new IconData(JAR_FILE_ICON, null, selectedFile.getAbsolutePath()));
        try {
            List<String> dl = JdbcDriverManagerUtil.readDriverList(selectedFile.getAbsolutePath());
            if (dl != null) {
                for (String d : dl) {
                    topNode.add(new DefaultMutableTreeNode(
                            new IconData(DRIVER_ICON, null, d)));
                }
            }
        } catch (MalformedURLException ex) {
            logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
        defaultTreeModel = new DefaultTreeModel(topNode);
        driverClassTree.setModel(defaultTreeModel);
    }

    private void removeJarButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void driverClassTreeValueChanged(TreeSelectionEvent evt) {
        // TODO add your handling code here:
    }

    private void driverClassTreeTreeExpanded(TreeExpansionEvent evt) {
        // TODO add your handling code here:
    }

    private void driverClassTreeTreeCollapsed(TreeExpansionEvent evt) {
        // TODO add your handling code here:
    }

    private void driverClassTreeMouseClicked(MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void driverClassTreeTreeWillExpand(TreeExpansionEvent evt)
            throws ExpandVetoException {
        // TODO add your handling code here:
    }

    private void driverClassTreeTreeWillCollapse(TreeExpansionEvent evt)
            throws ExpandVetoException {
        // TODO add your handling code here:
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        selectedOption = ApplicationConstants.APPLY_OPTION;
        dispose();
    }
    
    @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().equals(databaseNameList)){
			
		}
	}
    
    @Override
	public void valueChanged(ListSelectionEvent evt) {
    	if(evt.getSource().equals(databaseNameList)){
			if(databaseNameList.getSelectedIndex() >= 0){
				String dbType = databaseNameList.getSelectedValue().toString();
				if(DatabaseTypeEnum.ORACLE.getDescription().equals(dbType)){
					
				}
				else if(DatabaseTypeEnum.MYSQL.getDescription().equals(dbType)){
					
				}
				else if(DatabaseTypeEnum.MSSQL_2005.getDescription().equals(dbType)){
					
				}
				else {
					
				}
			}
		}
	}
    
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JdbcDriverManagerDialog dialog = new JdbcDriverManagerDialog(
                        new JFrame(), true);
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
    private JButton cancelButton;
    private JButton okButton;
    private JCheckBox copyJarCheckBox;
    private JList databaseNameList;
    private JTextField databaseNameTextField;
    private JPanel defaultValuePanel;
    private JTextField driverClassTextField;
    private JTree driverClassTree;
    private JLabel imageLabel;
    private JTabbedPane infoTabbedPane;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JPanel mainPanel;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1;
    private JButton loadJarButton;
    private JProgressBar loadingProgressBar;
    private JButton removeJarButton;
    private JTextField urlPatternTextField;
    private DefaultMutableTreeNode topNode;
    private DefaultTreeModel defaultTreeModel;
    // End of variables declaration

	

	
}
