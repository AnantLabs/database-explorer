package com.gs.dbex.common.command
{
	import com.gs.dbex.common.business.DbexUserResponder;
	import com.gs.dbex.common.delegate.DbexUserDelegate;
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.common.event.LoginEvent;
	import com.gs.dbex.common.factory.DbexResponderFactory;

	public class DbexUserCommand implements BaseCommand
	{
		public function DbexUserCommand()
		{
		}

		public function execute(event:DbexBaseEvent):void
		{
			var loginEvent:LoginEvent = LoginEvent(event);
			var delegate:DbexUserDelegate = new DbexUserDelegate(
				DbexResponderFactory.getInstance().getResponder(DbexUserResponder));
			delegate.login(loginEvent.userName, loginEvent.password);
		}
		
	}
}