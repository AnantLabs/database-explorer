package com.gs.dbex.application.event.command;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.impl.DatabaseConnectionServiceImpl;

/**
 * @author Sabuj Das
 *
 *	This is an EventHandler for handling common events.
 *
 */
public class GuiEventHandler implements ActionListener, GuiCommandConstants {

	private Component parent, sourceForm;
	private Object data;
	private DatabaseConnectionService connectionService;
	
	public GuiEventHandler() {
		connectionService = new DatabaseConnectionServiceImpl();
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	
		
	/**
	 * @return the parent
	 */
	public Component getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Component parent) {
		this.parent = parent;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public Component getSourceForm() {
		return sourceForm;
	}

	public void setSourceForm(Component sourceForm) {
		this.sourceForm = sourceForm;
	}

	public DatabaseConnectionService getConnectionService() {
		return connectionService;
	}

	public void setConnectionService(DatabaseConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	
}
