package com.gs.dbex.common.business
{
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
		}
		
	}
}