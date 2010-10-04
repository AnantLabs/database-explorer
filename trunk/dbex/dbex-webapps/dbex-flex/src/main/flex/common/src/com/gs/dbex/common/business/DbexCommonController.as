package com.gs.dbex.common.business
{
	import com.gs.dbex.common.command.DbexUserCommand;
	import com.gs.dbex.common.event.LoginEvent;
	
	public class DbexCommonController extends FrontController
	{
		public function DbexCommonController()
		{
			super();
			initialiseCommands();
		}
		
		public function initialiseCommands() : void{
			addCommand( LoginEvent.LOGIN_EVENT, DbexUserCommand );
		}
		
	}
}