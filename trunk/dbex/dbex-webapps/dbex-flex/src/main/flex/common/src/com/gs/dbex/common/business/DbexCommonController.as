package com.gs.dbex.common.business
{
	import com.gs.dbex.application.command.DatabaseConnectionCommand;
	import com.gs.dbex.application.event.DatabaseConnectionEvent;
	import com.gs.dbex.common.command.DbexUserCommand;
	import com.gs.dbex.common.event.LoginEvent;
	import com.gs.dbex.common.event.RegisterEvent;
	
	public class DbexCommonController extends FrontController
	{
		public function DbexCommonController()
		{
			super();
			initialiseCommands();
		}
		
		public function initialiseCommands() : void{
			addCommand( LoginEvent.LOGIN_EVENT, DbexUserCommand );
			addCommand( RegisterEvent.REGISTER_EVENT, DbexUserCommand );
			
			addCommand( DatabaseConnectionEvent.CONNECT_EVENT, DatabaseConnectionCommand );
			addCommand( DatabaseConnectionEvent.TEST_CONNECTION_EVENT, DatabaseConnectionCommand );
			addCommand( DatabaseConnectionEvent.DISCONNECT_EVENT, DatabaseConnectionCommand );
		}
		
	}
}