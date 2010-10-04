package com.gs.dbex.common.event
{
	public class LoginEvent extends DbexBaseEvent
	{
		public static const LOGIN_EVENT:String = "LOGIN_EVENT" ;
		
		public var userName:String;
		public var password:String;
		
		public function LoginEvent()
		{
			super(LOGIN_EVENT, false, false);
		}

	}
}