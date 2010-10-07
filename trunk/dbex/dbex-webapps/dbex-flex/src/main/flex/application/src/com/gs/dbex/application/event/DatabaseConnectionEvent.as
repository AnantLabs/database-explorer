package com.gs.dbex.application.event
{
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.vo.ConnectionPropertiesVO;

	public class DatabaseConnectionEvent extends DbexBaseEvent
	{
		public static const CONNECT_EVENT:String = "CONNECT_EVENT" ;
		public static const DISCONNECT_EVENT:String = "DISCONNECT_EVENT" ;
		public static const TEST_CONNECTION_EVENT:String = "TEST_CONNECTION_EVENT" ;
		
		public var connectionProppertiesVO:ConnectionPropertiesVO;
		
		public function DatabaseConnectionEvent(type:String)
		{
			super(type, false, false);
		}
		
	}
}