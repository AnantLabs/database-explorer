/**
 * 
 */
package com.gs.dbex.application.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Sabuj Das | sabuj.das@gmail.com
 *
 */
public class DisplayUtils {

	private static final int INFO_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
	private static final int WARN_MESSAGE = JOptionPane.WARNING_MESSAGE;
	private static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
	
	public static String readString(String displayText){
		return readFromJOptPane(null, displayText);
	}
	
	public static String readString(Component parent, String displayText){
		return readFromJOptPane(parent, displayText);
	}
	
	private static String readFromJOptPane(Component parent, String displayText){
		String value = "";
		try{
			value = JOptionPane.showInputDialog(parent, displayText);
		}catch(Exception e){
			return "";
		}
		return value;
	}
	
	public static void displayMessage(String displayText){
		displayMessage(null, displayText);
	}
	
	public static void displayMessage(Component parent, String displayText){
		displayMessage(parent, displayText, DisplayTypeEnum.INFO);
	}
	
	public static void displayMessage(Component parent, String displayText, DisplayTypeEnum displayType){
		int messageType = -1;
		if(DisplayTypeEnum.INFO.equals(displayType)){
			messageType = INFO_MESSAGE;
		}else if(DisplayTypeEnum.WARN.equals(displayType)){
			messageType = WARN_MESSAGE;
		}else if(DisplayTypeEnum.ERROR.equals(displayType)){
			messageType = ERROR_MESSAGE;
		}
		if(messageType != -1){
			JOptionPane.showMessageDialog(parent, displayText, "Message...", messageType);
		}else{
			JOptionPane.showMessageDialog(parent, displayText);
		}
	}
	
	public static int confirmOkCancel(Component parent, String displayText, DisplayTypeEnum displayType){
		int messageType = -1;
		if(DisplayTypeEnum.INFO.equals(displayType)){
			messageType = INFO_MESSAGE;
		}else if(DisplayTypeEnum.WARN.equals(displayType)){
			messageType = WARN_MESSAGE;
		}else if(DisplayTypeEnum.ERROR.equals(displayType)){
			messageType = ERROR_MESSAGE;
		}
		if(messageType != -1){
			return JOptionPane.showConfirmDialog(parent, displayText, "Confirm...", JOptionPane.OK_CANCEL_OPTION, messageType);
		}
		return JOptionPane.showConfirmDialog(parent, displayText);
	}
}
