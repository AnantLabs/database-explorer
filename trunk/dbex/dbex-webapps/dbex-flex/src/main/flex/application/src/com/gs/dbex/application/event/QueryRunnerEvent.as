package com.gs.dbex.application.event
{
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.vo.ConnectionPropertiesVO;

	public class QueryRunnerEvent extends DbexBaseEvent
	{
		
		public static const RUN_SINGLE_SQL_EVENT:String = "RUN_SINGLE_SQL_EVENT" ;
		
		public var singleSql:String;
		public var connectionProperties:ConnectionPropertiesVO;
		
		public function QueryRunnerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}