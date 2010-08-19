/**
 * 
 */
package com.gs.dbex.application.util;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * @author Green Moon
 *
 */
public class MenuBarUtil {

	/**
	 * 
	 * @param text
	 * @return
	 */
	public static JMenu createMenu(String text){
		return new JMenu(text);
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @return
	 */
	public static JMenuItem createMenuItem(String text, String cmdName){
		return createMenuItem(text, cmdName, null);
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @param icon
	 * @return
	 */
	public static JMenuItem createMenuItem(String text, String cmdName, ImageIcon icon){
		JMenuItem item = new JMenuItem(text);
		item.setActionCommand(cmdName);
		if(icon != null){
			item.setIcon(icon);
		}
		return item;
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @return
	 */
	public static JCheckBoxMenuItem createChkMenuItem(String text, String cmdName){
		return createChkMenuItem(text, cmdName, null);
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @param icon
	 * @return
	 */
	public static JCheckBoxMenuItem createChkMenuItem(String text, String cmdName, ImageIcon icon){
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
		item.setActionCommand(cmdName);
		if(icon != null){
			item.setIcon(icon);
		}
		return item;
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @return
	 */
	public static JRadioButtonMenuItem createRadioMenuItem(String text, String cmdName){
		return createRadioMenuItem(text, cmdName, null, null);
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @param group
	 * @return
	 */
	public static JRadioButtonMenuItem createRadioMenuItem(String text, String cmdName, ButtonGroup group){
		return createRadioMenuItem(text, cmdName, group, null);
	}
	/**
	 * 
	 * @param text
	 * @param cmdName
	 * @param group
	 * @param icon
	 * @return
	 */
	public static JRadioButtonMenuItem createRadioMenuItem(String text, String cmdName, ButtonGroup group, ImageIcon icon){
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(text);
		item.setActionCommand(cmdName);
		if(group != null){
			group.add(item);
		}
		if(icon != null){
			item.setIcon(icon);
		}
		return item;
	}
	
}
