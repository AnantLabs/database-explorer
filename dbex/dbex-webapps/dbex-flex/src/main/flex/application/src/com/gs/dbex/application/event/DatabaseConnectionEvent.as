package com.gs.dbex.application.event
{
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.vo.ConnectionPropertiesVO;
	
	import mx.collections.ArrayCollection;

	public class DatabaseConnectionEvent extends DbexBaseEvent
	{
		public static const CONNECT_EVENT:String = "CONNECT_EVENT" ;
		public static const DISCONNECT_EVENT:String = "DISCONNECT_EVENT" ;
		public static const TEST_CONNECTION_EVENT:String = "TEST_CONNECTION_EVENT" ;
		
		public static const SAVE_CONN_PROPS_EVENT:String = "SAVE_CONN_PROPS_EVENT" ;
		public static const SAVE_ALL_CONN_PROPS_EVENT:String = "SAVE_ALL_CONN_PROPS_EVENT" ;
		
		public var connectionProppertiesVO:ConnectionPropertiesVO;
		public var connectionProppertiesVOList:ArrayCollection;
		
		public function DatabaseConnectionEvent(type:String)
		{
			super(type, false, false);
		}
		
	}
}