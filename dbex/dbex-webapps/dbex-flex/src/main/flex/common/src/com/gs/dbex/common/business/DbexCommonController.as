package com.gs.dbex.common.business
{
	import com.gs.dbex.application.command.DatabaseConnectionCommand;
	import com.gs.dbex.application.command.QueryRunnerCommand;
	import com.gs.dbex.application.event.DatabaseConnectionEvent;
	import com.gs.dbex.application.event.QueryRunnerEvent;
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
			
			addCommand( QueryRunnerEvent.RUN_SINGLE_SQL_EVENT, QueryRunnerCommand );
			addCommand( QueryRunnerEvent.GET_PAGINATED_TABLE_DATA_EVENT, QueryRunnerCommand );
			addCommand( QueryRunnerEvent.GET_FILTERED_PAGINATED_TABLE_DATA_EVENT, QueryRunnerCommand );
			addCommand( QueryRunnerEvent.GET_CURRENT_PAGE_EVENT, QueryRunnerCommand );
		}
		
	}
}