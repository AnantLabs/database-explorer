package com.gs.dbex.application.event;

import java.util.EventListener;

public interface ComponentUpdateEventListener extends EventListener {

	public void updateComponentUI(ComponentUpdateEvent componentUpdateEvent);
	
}
