/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author sabuj
 *
 */
public class TextFindReplaceDialog extends JDialog {

    /** Creates new form TextFindReplaceDialog */
    public TextFindReplaceDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * 
     */
                              
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        directionButtonGroup = new ButtonGroup();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        findKeyTextField = new JTextField();
        jLabel2 = new JLabel();
        replaceWithTextField = new JTextField();
        findDirectionPanel = new JPanel();
        forwordRadioButton = new JRadioButton();
        backwordRadioButton = new JRadioButton();
        searchOptionPanel = new JPanel();
        caseSensitiveCheckBox = new JCheckBox();
        wholeWordCheckBox = new JCheckBox();
        regularExpCheckBox = new JCheckBox();
        wrapSearchCheckBox = new JCheckBox();
        incrementalCheckBox = new JCheckBox();
        jPanel2 = new JPanel();
        findButton = new JButton();
        replaceButton = new JButton();
        findReplaceButton = new JButton();
        replaceAllButton = new JButton();
        closeButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find / Replace ...");

        jPanel1.setLayout(new GridBagLayout());

        jLabel1.setText("Find What");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        jPanel1.add(findKeyTextField, gridBagConstraints);

        jLabel2.setText("Replace With");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(6, 2, 6, 2);
        jPanel1.add(replaceWithTextField, gridBagConstraints);

        findDirectionPanel.setBorder(BorderFactory.createTitledBorder(" Direction "));
        findDirectionPanel.setLayout(new GridBagLayout());

        directionButtonGroup.add(forwordRadioButton);
        forwordRadioButton.setSelected(true);
        forwordRadioButton.setText("Forword");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        findDirectionPanel.add(forwordRadioButton, gridBagConstraints);

        directionButtonGroup.add(backwordRadioButton);
        backwordRadioButton.setText("Backword");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        findDirectionPanel.add(backwordRadioButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(8, 2, 2, 2);
        jPanel1.add(findDirectionPanel, gridBagConstraints);

        searchOptionPanel.setBorder(BorderFactory.createTitledBorder(" Options "));
        searchOptionPanel.setLayout(new GridBagLayout());

        caseSensitiveCheckBox.setText("Case Sensitive");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        searchOptionPanel.add(caseSensitiveCheckBox, gridBagConstraints);

        wholeWordCheckBox.setText("Whole Word");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        searchOptionPanel.add(wholeWordCheckBox, gridBagConstraints);

        regularExpCheckBox.setText("Regular Expressions");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        searchOptionPanel.add(regularExpCheckBox, gridBagConstraints);

        wrapSearchCheckBox.setSelected(true);
        wrapSearchCheckBox.setText("Wrap search");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        searchOptionPanel.add(wrapSearchCheckBox, gridBagConstraints);

        incrementalCheckBox.setText("Incremental");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        searchOptionPanel.add(incrementalCheckBox, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(searchOptionPanel, gridBagConstraints);

        jPanel2.setLayout(new GridBagLayout());

        findButton.setText("Find");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(findButton, gridBagConstraints);

        replaceButton.setText("Replac");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(replaceButton, gridBagConstraints);

        findReplaceButton.setText("Find/Replace");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(findReplaceButton, gridBagConstraints);

        replaceAllButton.setText("Replace All");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(replaceAllButton, gridBagConstraints);

        closeButton.setText("Close");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel2.add(closeButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        jPanel1.add(jPanel2, gridBagConstraints);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }                        

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                TextFindReplaceDialog dialog = new TextFindReplaceDialog(new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

                         
    private JRadioButton backwordRadioButton;
    private JCheckBox caseSensitiveCheckBox;
    private JButton closeButton;
    private ButtonGroup directionButtonGroup;
    private JButton findButton;
    private JPanel findDirectionPanel;
    private JTextField findKeyTextField;
    private JButton findReplaceButton;
    private JRadioButton forwordRadioButton;
    private JCheckBox incrementalCheckBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JCheckBox regularExpCheckBox;
    private JButton replaceAllButton;
    private JButton replaceButton;
    private JTextField replaceWithTextField;
    private JPanel searchOptionPanel;
    private JCheckBox wholeWordCheckBox;
    private JCheckBox wrapSearchCheckBox;
                       

}
