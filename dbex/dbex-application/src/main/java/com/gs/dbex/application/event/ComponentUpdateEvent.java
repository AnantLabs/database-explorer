package com.gs.dbex.application.event;

import java.awt.AWTEvent;

public class ComponentUpdateEvent extends AWTEvent {

	public static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
	public static final String UPDATE_CONNECTION_NAME_LIST_UI = "UPDATE_CONNECTION_NAME_LIST_UI";
	
	private String type;


	public ComponentUpdateEvent(Object source, String eventType) {
		super(source, EVENT_ID);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
