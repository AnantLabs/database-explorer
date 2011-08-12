package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.gs.dbex.application.task.DatabaseConnectionTask;
import com.gs.dbex.application.task.QueryExecutionTask;
import com.gs.dbex.application.task.WorkerTaskConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.swing.window.WindowUtil;

public class DbexConnectionStatusDialog extends JDialog implements ActionListener, PropertyChangeListener{

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6574746860889904870L;
	
	private boolean exitOnSuccess = false;
	
	private Frame parentFrame;
	
	private JButton closeButton;
    private JLabel databaseNameLabel;
    private JPanel detailedMessagePanel;
    private JLabel shortMessageLabel;
    private JButton showDetailsButton;
    private JProgressBar statusProgressBar;
    private JButton stopButton;
    private JLabel titleLabel;
    private JTextArea detailedMessageTextArea;
    private JScrollPane jScrollPane1;
    
    private DatabaseConnectionTask databaseConnectionTask;
	
	public DbexConnectionStatusDialog(Frame parentFrame) {
		this.parentFrame = parentFrame;
		setModal(true);
		setTitle("Please Wait ...");
		createComponents();
		
		detailedMessagePanel.setVisible(false);
		showDetailsButton.setVisible(false);
		stopButton.setVisible(false);
		setSize(400, 120);
		
		WindowUtil.bringCenterTo(this, parentFrame);
		
	}
	
	
	public void showDialog(ConnectionProperties connectionProperties){
		if(null != connectionProperties){
			
			databaseNameLabel.setText(connectionProperties.getConnectionName());
			
			synchronized (this) {
				databaseConnectionTask = new DatabaseConnectionTask(connectionProperties);
				databaseConnectionTask.addPropertyChangeListener(this);
				databaseConnectionTask.execute();
			}
		}
	}
	
	
	public boolean isExitOnSuccess() {
		return exitOnSuccess;
	}


	public void setExitOnSuccess(boolean exitOnSuccess) {
		this.exitOnSuccess = exitOnSuccess;
	}


	private void createComponents() {
		GridBagConstraints gridBagConstraints;

        titleLabel = new JLabel();
        databaseNameLabel = new JLabel();
        statusProgressBar = new JProgressBar();
        stopButton = new JButton();
        stopButton.addActionListener(this);
        detailedMessagePanel = new JPanel();
        closeButton = new JButton();
        closeButton.addActionListener(this);
        showDetailsButton = new JButton();
        showDetailsButton.addActionListener(this);
        shortMessageLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        detailedMessageTextArea = new JTextArea();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        titleLabel.setText("Connecting to: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(titleLabel, gridBagConstraints);

        databaseNameLabel.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        databaseNameLabel.setForeground(new Color(0, 0, 204));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(databaseNameLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(statusProgressBar, gridBagConstraints);

        stopButton.setText("Stop");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(stopButton, gridBagConstraints);

        detailedMessagePanel.setBorder(BorderFactory.createTitledBorder(" Message "));

        detailedMessagePanel.setLayout(new BorderLayout());
        
        detailedMessageTextArea.setColumns(20);
        detailedMessageTextArea.setRows(5);
        jScrollPane1.setViewportView(detailedMessageTextArea);
        detailedMessagePanel.add(jScrollPane1, BorderLayout.CENTER);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(detailedMessagePanel, gridBagConstraints);

        closeButton.setText("Close");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(closeButton, gridBagConstraints);

        showDetailsButton.setText("Show Details");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(showDetailsButton, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(shortMessageLabel, gridBagConstraints);

        pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(showDetailsButton.equals(e.getSource())){
			setSize(400, 300);
			detailedMessagePanel.setVisible(true);
			showDetailsButton.setVisible(false);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (WorkerTaskConstants.TASK_STATUS_START.equals(propertyName)) {
			statusProgressBar.setIndeterminate(true);
			shortMessageLabel.setText("Please Wait ...");
		}
		if (WorkerTaskConstants.PROPERTY_PROGRESS.equals(propertyName)) {
			shortMessageLabel.setText("Please Wait ...");
			statusProgressBar.setIndeterminate(true);
		}
		if (WorkerTaskConstants.TASK_STATUS_FAILED.equals(propertyName)) {
			statusProgressBar.setIndeterminate(false);
			//statusProgressBar.setVisible(false);
			
			Object[] val = (Object[]) evt.getNewValue();
			
			shortMessageLabel.setText(val[0].toString());
			if(null != val[1]){
				Exception ex = (Exception) val[1];
				detailedMessageTextArea.setText(getStackTrace(ex));
				showDetailsButton.setVisible(true);
				setSize(400, 150);
			} else {
				showDetailsButton.setVisible(false);
			}
			
			
			
		}
		if (WorkerTaskConstants.TASK_STATUS_ABORT.equals(propertyName)) {
			statusProgressBar.setIndeterminate(false);
			shortMessageLabel.setText("Task Cancelled ...");
		}
		if (WorkerTaskConstants.TASK_STATUS_DONE.equals(propertyName)) {
			statusProgressBar.setIndeterminate(false);
		}
	}
	
	private String getStackTrace(Exception ex){
		StringWriter sw = new StringWriter();  
		ex.printStackTrace(new PrintWriter(sw));  
		return sw.toString(); 
	}
	
	public Frame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(Frame parentFrame) {
		this.parentFrame = parentFrame;
	}
	
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DbexConnectionStatusDialog dialog = new DbexConnectionStatusDialog(null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
	
	
	 
	
}
