/**
 * 
 */
package com.gs.dbex.application.graph;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.omg.CORBA.portable.ApplicationException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.dependency.TableDependency;

/**
 * @author sabuj.das
 * 
 */
public class TableDependencyPanel extends JPanel {

	private String schemaName, tableName;
	private ConnectionProperties connectionProperties;
	private JFrame parentFrame;
	
	public TableDependencyPanel(JFrame frame, String schemaName, String tableName, ConnectionProperties cp) {
		this.parentFrame = frame;
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.connectionProperties = cp;
		initComponents();
	}

	

	private void initComponents() {
		GridBagConstraints gridBagConstraints;
		Icon image = null;
		
		generateGraphToolBar = new JToolBar();
		generateGraphButton = new JButton();
		jSeparator1 = new JToolBar.Separator();
		saveGraphButton = new JButton();
		jSeparator2 = new JToolBar.Separator();
		clearButton = new JButton();
		showCompleteTableToggleBtn = new JToggleButton("Show Complete Table");
		imageViewToolBar = new JToolBar();
		fitToWindowButton = new JButton();
		jSeparator3 = new JToolBar.Separator();
		zoomInButton = new JButton();
		jSeparator4 = new JToolBar.Separator();
		zoomPercentTextField = new JTextField();
		jLabel2 = new JLabel();
		jSeparator5 = new JToolBar.Separator();
		zoomOutButton = new JButton();
		jSeparator6 = new JToolBar.Separator();
		actualSizeButton = new JButton();
		loadingLabel = new JLabel();
		graphHolderScrollPane = new JScrollPane();

		FormListener formListener = new FormListener();

		setLayout(new GridBagLayout());

		generateGraphToolBar.setFloatable(false);
		generateGraphToolBar.setRollover(true);

		generateGraphButton.setText("Generate Graph");
		generateGraphButton.setFocusable(false);
		generateGraphButton.setHorizontalTextPosition(SwingConstants.CENTER);
		generateGraphButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		generateGraphButton.addActionListener(formListener);
		generateGraphToolBar.add(generateGraphButton);
		generateGraphToolBar.add(jSeparator1);

		saveGraphButton.setText("Save");
		saveGraphButton.setFocusable(false);
		saveGraphButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveGraphButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveGraphButton.addActionListener(formListener);
		generateGraphToolBar.add(saveGraphButton);
		generateGraphToolBar.add(jSeparator2);

		clearButton.setText("Clear");
		clearButton.setFocusable(false);
		clearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		clearButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		clearButton.addActionListener(formListener);
		generateGraphToolBar.add(clearButton);
		
		generateGraphToolBar.add(new JToolBar.Separator());
		
		showCompleteTableToggleBtn.setFocusable(false);
		showCompleteTableToggleBtn.setSelected(true);
		showCompleteTableToggleBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		showCompleteTableToggleBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		showCompleteTableToggleBtn.addActionListener(formListener);
		generateGraphToolBar.add(showCompleteTableToggleBtn);
		

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		add(generateGraphToolBar, gridBagConstraints);

		
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(graphHolderScrollPane, gridBagConstraints);
		
		

		imageViewToolBar.setFloatable(false);
		imageViewToolBar.setRollover(true);

		fitToWindowButton.setText("Fit");
		fitToWindowButton.setFocusable(false);
		fitToWindowButton.setHorizontalTextPosition(SwingConstants.CENTER);
		fitToWindowButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		fitToWindowButton.addActionListener(formListener);
		imageViewToolBar.add(fitToWindowButton);
		imageViewToolBar.add(jSeparator3);

		zoomInButton.setText("+");
		zoomInButton.setFocusable(false);
		zoomInButton.setHorizontalTextPosition(SwingConstants.CENTER);
		zoomInButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		zoomInButton.addActionListener(formListener);
		imageViewToolBar.add(zoomInButton);
		imageViewToolBar.add(jSeparator4);

		zoomPercentTextField.setHorizontalAlignment(JTextField.CENTER);
		zoomPercentTextField.setText("100");
		zoomPercentTextField.setMinimumSize(new Dimension(50, 20));
		zoomPercentTextField.setPreferredSize(new Dimension(50, 20));
		zoomPercentTextField.addFocusListener(formListener);
		zoomPercentTextField.addKeyListener(formListener);
		imageViewToolBar.add(zoomPercentTextField);

		jLabel2.setText(" % ");
		imageViewToolBar.add(jLabel2);
		imageViewToolBar.add(jSeparator5);

		zoomOutButton.setText("-");
		zoomOutButton.setFocusable(false);
		zoomOutButton.setHorizontalTextPosition(SwingConstants.CENTER);
		zoomOutButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		zoomOutButton.addActionListener(formListener);
		imageViewToolBar.add(zoomOutButton);
		imageViewToolBar.add(jSeparator6);

		actualSizeButton.setText("Actual");
		actualSizeButton.setFocusable(false);
		actualSizeButton.setHorizontalTextPosition(SwingConstants.CENTER);
		actualSizeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		actualSizeButton.addActionListener(formListener);
		imageViewToolBar.add(actualSizeButton);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 1.0;
		add(imageViewToolBar, gridBagConstraints);

		loadingLabel.setText("Generating Dependency ...");
		image = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "loading.gif"));
		loadingLabel.setIcon(image);
		loadingLabel.setVisible(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		add(loadingLabel, gridBagConstraints);
	}

	// Code for dispatching events from components to event handlers.

	private class FormListener implements ActionListener, FocusListener,
			KeyListener, MouseMotionListener {
		FormListener() {
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == generateGraphButton) {
				TableDependencyPanel.this
						.generateGraphButtonActionPerformed(evt);
			} else if (evt.getSource() == saveGraphButton) {
				TableDependencyPanel.this.saveGraphButtonActionPerformed(evt);
			} else if (evt.getSource() == clearButton) {
				TableDependencyPanel.this.clearButtonActionPerformed(evt);
			} else if (evt.getSource() == showCompleteTableToggleBtn) {
				TableDependencyPanel.this.showCompleteTableActionPerformed(evt);
			} else if (evt.getSource() == fitToWindowButton) {
				TableDependencyPanel.this.fitToWindowButtonActionPerformed(evt);
			} else if (evt.getSource() == zoomInButton) {
				TableDependencyPanel.this.zoomInButtonActionPerformed(evt);
			} else if (evt.getSource() == zoomOutButton) {
				TableDependencyPanel.this.zoomOutButtonActionPerformed(evt);
			} else if (evt.getSource() == actualSizeButton) {
				TableDependencyPanel.this.actualSizeButtonActionPerformed(evt);
			}
		}

		public void focusGained(FocusEvent evt) {
		}

		public void focusLost(FocusEvent evt) {
			if (evt.getSource() == zoomPercentTextField) {
				TableDependencyPanel.this.zoomPercentTextFieldFocusLost(evt);
			}
		}

		public void keyPressed(KeyEvent evt) {
			if (evt.getSource() == zoomPercentTextField) {
				TableDependencyPanel.this.zoomPercentTextFieldKeyPressed(evt);
			}
			else if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelKeyPressed(evt);
            }
		}

		public void keyReleased(KeyEvent evt) {
			if (evt.getSource() == zoomPercentTextField) {
				TableDependencyPanel.this.zoomPercentTextFieldKeyReleased(evt);
			}
			else if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelKeyReleased(evt);
            }
		}

		public void keyTyped(KeyEvent evt) {
			if (evt.getSource() == zoomPercentTextField) {
				TableDependencyPanel.this.zoomPercentTextFieldKeyTyped(evt);
			}
			else if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelKeyTyped(evt);
            }
		}
		
		public void mouseClicked(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseClicked(evt);
            }
        }

        public void mouseEntered(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseEntered(evt);
            }
        }

        public void mouseExited(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseExited(evt);
            }
        }

        public void mousePressed(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMousePressed(evt);
            }
        }

        public void mouseReleased(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseReleased(evt);
            }
        }

        public void mouseDragged(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseDragged(evt);
            }
        }

        public void mouseMoved(MouseEvent evt) {
            if (evt.getSource() == graphHolderPanel) {
                TableDependencyPanel.this.graphHolderPanelMouseMoved(evt);
            }
        }
        
        
	}

	private void generateGraphButtonActionPerformed(ActionEvent evt) {
		Runnable r = new Runnable(){
			public void run() {
				loadingLabel.setVisible(true);
				//DependencyService dependencyService = new DependencyServiceImpl();
				TableDependency dependency = null;
				/*try {
					dependency = dependencyService.generateTableDependency(
							connectionProperties.getDataSource().getConnection(), 
							schemaName, tableName);
				} catch (ApplicationException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}*/
				graphHolderPanel = new DependencyGraphPanel(dependency);
				graphHolderPanel.addMouseMotionListener(new FormListener());
				graphHolderPanel.setShowCompleteTable(showCompleteTableToggleBtn.isSelected());
				graphHolderPanel.updateUI();
				graphHolderScrollPane.setViewportView(graphHolderPanel);
				loadingLabel.setVisible(false);
			}
		};
		new Thread(r).start();
	}

	private void saveGraphButtonActionPerformed(ActionEvent evt) {
		if(graphHolderPanel == null){
			return;
		}
/*		
		JFileChooser chooser = new JFileChooser(".");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int opt = chooser.showSaveDialog();
        if (opt == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            
        }*/
		
		int w = graphHolderPanel.getWidth();
		int h = graphHolderPanel.getHeight();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		graphHolderPanel.paint(g2);
        g2.dispose();
        try {
			ImageIO.write(image, "png", new File(ApplicationConstants.DATA_DIR + 
					schemaName + "." + tableName +"_dependency.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearButtonActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
	}
	
	public void showCompleteTableActionPerformed(ActionEvent evt){
    	if(showCompleteTableToggleBtn.isSelected()){
    		graphHolderPanel.setShowCompleteTable(true);
    	}else{
    		graphHolderPanel.setShowCompleteTable(false);
    	}
    	graphHolderPanel.repaint();
    	
    }

	private void fitToWindowButtonActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomInButtonActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomPercentTextFieldKeyPressed(KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomPercentTextFieldKeyReleased(KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomPercentTextFieldKeyTyped(KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomPercentTextFieldFocusLost(FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void zoomOutButtonActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void actualSizeButtonActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void graphHolderPanelMouseEntered(MouseEvent evt) {
        Point p = evt.getPoint();
        graphHolderPanel.setToolTipText("[ "+ p.getX() + ", " + p.getY() + " ]");
    }

    private void graphHolderPanelMouseExited(MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void graphHolderPanelMouseDragged(MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void graphHolderPanelMouseMoved(MouseEvent evt) {
    	Point p = evt.getPoint();
        graphHolderPanel.setToolTipText("[ "+ p.getX() + ", " + p.getY() + " ]");
    }

    private void graphHolderPanelMouseClicked(MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void graphHolderPanelMousePressed(MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void graphHolderPanelMouseReleased(MouseEvent evt) {
        // TODO add your handling code here:
    }
    
    private void graphHolderPanelKeyTyped(KeyEvent evt) {
        // TODO add your handling code here:
    }
    
    private void graphHolderPanelKeyPressed(KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void graphHolderPanelKeyReleased(KeyEvent evt) {
        // TODO add your handling code here:
    }
    
	// Variables declaration 
	private JButton actualSizeButton;
	private JButton clearButton;
	private JToggleButton showCompleteTableToggleBtn;
	private JButton fitToWindowButton;
	private JButton generateGraphButton;
	private JToolBar generateGraphToolBar;
	private DependencyGraphPanel graphHolderPanel;
	private JToolBar imageViewToolBar;
	private JLabel jLabel2;
	private JToolBar.Separator jSeparator1;
	private JToolBar.Separator jSeparator2;
	private JToolBar.Separator jSeparator3;
	private JToolBar.Separator jSeparator4;
	private JToolBar.Separator jSeparator5;
	private JToolBar.Separator jSeparator6;
	private JLabel loadingLabel;
	private JButton saveGraphButton;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JTextField zoomPercentTextField;
	private JScrollPane graphHolderScrollPane;
	// End of variables declaration

	public String getSchemaName() {
		return schemaName;
	}



	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}



	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}



	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}


	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}

	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

}
