package com.gs.dbex.application.event
{
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.common.model.db.Table;
	import com.gs.dbex.vo.ConnectionPropertiesVO;
	import com.gs.dbex.vo.PaginationResultVO;

	public class QueryRunnerEvent extends DbexBaseEvent
	{
		
		public static const RUN_SINGLE_SQL_EVENT:String = "RUN_SINGLE_SQL_EVENT" ;
		public static const GET_PAGINATED_TABLE_DATA_EVENT:String = "GET_PAGINATED_TABLE_DATA_EVENT" ;
		public static const GET_FILTERED_PAGINATED_TABLE_DATA_EVENT:String = "GET_FILTERED_PAGINATED_TABLE_DATA_EVENT" ;
		
		public static const GET_CURRENT_PAGE_EVENT:String = "GET_CURRENT_PAGE_EVENT" ;
		
		public var singleSql:String;
		public var connectionProperties:ConnectionPropertiesVO;
		public var selectedTable:Table;
		public var filterSubQuery:String;
		public var paginationResult:PaginationResultVO;
		
		public function QueryRunnerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}