/**
 * 
 */
package com.gs.dbex.application.event.action;

/**
 * @author sabuj.das
 *
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.beans.*;

public class TileAction extends AbstractAction {
    
    private JDesktopPane desktop; // the desktop to work with
    
    public TileAction(JDesktopPane desk) {
        super("Tile Frames");
        this.desktop = desk;
    }
    
    public void actionPerformed(ActionEvent ev) {
        
        // How many frames do we have?
        JInternalFrame[] allframes = desktop.getAllFrames();
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
        Dimension size = desktop.getSize();
        
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
                
                desktop.getDesktopManager().resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // start the next row
            x = 0;
        }
    }
}
