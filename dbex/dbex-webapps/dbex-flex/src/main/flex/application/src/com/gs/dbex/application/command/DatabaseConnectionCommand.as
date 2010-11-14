package com.gs.dbex.application.command
{
	import com.gs.dbex.application.delegate.DatabaseConnectionDelegate;
	import com.gs.dbex.application.event.DatabaseConnectionEvent;
	import com.gs.dbex.application.responder.ConnectResponder;
	import com.gs.dbex.application.responder.DisconnectResponder;
	import com.gs.dbex.application.responder.SaveConnectionPropertiesResponder;
	import com.gs.dbex.application.responder.TestConnectionResponder;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.command.BaseCommand;
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.common.factory.DbexResponderFactory;

	public class DatabaseConnectionCommand implements BaseCommand
	{
		
		public function DatabaseConnectionCommand()
		{
		}

		public function execute(event:DbexBaseEvent):void
		{
			var responder : BaseResponder = null;
			var databaseConnEvt:DatabaseConnectionEvent = event as DatabaseConnectionEvent;
			if(DatabaseConnectionEvent.CONNECT_EVENT == event.type){
				responder = DbexResponderFactory.getInstance().getResponder(ConnectResponder);
				var connDelegate:DatabaseConnectionDelegate = new DatabaseConnectionDelegate(responder);
				connDelegate.connect(databaseConnEvt.connectionProppertiesVO);
			} else if(DatabaseConnectionEvent.TEST_CONNECTION_EVENT == event.type){
				responder = DbexResponderFactory.getInstance().getResponder(TestConnectionResponder);
				var connDelegate:DatabaseConnectionDelegate = new DatabaseConnectionDelegate(responder);
				connDelegate.testConnection(databaseConnEvt.connectionProppertiesVO);
			} else if(DatabaseConnectionEvent.DISCONNECT_EVENT == event.type){
				responder = DbexResponderFactory.getInstance().getResponder(DisconnectResponder);
				var connDelegate:DatabaseConnectionDelegate = new DatabaseConnectionDelegate(responder);
				connDelegate.disconnect(databaseConnEvt.connectionProppertiesVO);
			} else if(DatabaseConnectionEvent.SAVE_CONN_PROPS_EVENT == event.type){
				responder = DbexResponderFactory.getInstance().getResponder(SaveConnectionPropertiesResponder);
				var connDelegate:DatabaseConnectionDelegate = new DatabaseConnectionDelegate(responder);
				connDelegate.saveConnectionProperties(databaseConnEvt.connectionProppertiesVO);
			} else if(DatabaseConnectionEvent.SAVE_ALL_CONN_PROPS_EVENT == event.type){
				responder = DbexResponderFactory.getInstance().getResponder(SaveConnectionPropertiesResponder);
				var connDelegate:DatabaseConnectionDelegate = new DatabaseConnectionDelegate(responder);
				connDelegate.saveAllConnectionProperties(databaseConnEvt.connectionProppertiesVOList);
			}
		}
		
	}
}