/**
 * 
 */
package com.gs.dbex.application.table;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.gs.dbex.application.button.BlobButton;

/**
 * @author sabuj.das
 *
 */
public class BlobTableCell extends JPanel {

	String text;
    BlobButton button;
    
    public BlobTableCell(String text_) {
        text = text_;
        setLayout(new GridBagLayout());
        button = new BlobButton();
        button.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("cell button is clicked");
            }

            public void mouseEntered(MouseEvent e) {                    
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                System.out.println("cell button is pressed");                    
            }

            public void mouseReleased(MouseEvent e) {
            }
        });
        add(button, new GridBagConstraints(0, 0, 1, 1, 0, 100.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, 
            new Insets(10, 5, 10, 5), 20, 10));
    }
}
