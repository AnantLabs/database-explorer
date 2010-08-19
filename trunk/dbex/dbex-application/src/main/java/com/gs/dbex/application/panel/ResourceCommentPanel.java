/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 *
 */
public class ResourceCommentPanel<T> extends JPanel {

	
    public ResourceCommentPanel(T resource) {
    	String oldComment = "";
    	if(resource != null && resource instanceof BaseDbModel){
    		oldComment = ((BaseDbModel)resource).getComments();
    	}
        initComponents();
        commentTextArea.setText(oldComment);
    }



	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        commentTextArea = new JTextArea();
        clearcommentButton = new JButton();

        setLayout(new GridBagLayout());

        jLabel1.setText("Comment:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel1, gridBagConstraints);

        commentTextArea.setColumns(20);
        commentTextArea.setRows(5);
        jScrollPane1.setViewportView(commentTextArea);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jScrollPane1, gridBagConstraints);

        clearcommentButton.setText("Clear");
        clearcommentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clearcommentButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(clearcommentButton, gridBagConstraints);
    }

    private void clearcommentButtonActionPerformed(ActionEvent evt) {
        commentTextArea.setText("");
    }

    
    
    public JButton getClearcommentButton() {
		return clearcommentButton;
	}


	public JTextArea getCommentTextArea() {
		return commentTextArea;
	}



	private JButton clearcommentButton;
    private JTextArea commentTextArea;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;

}
