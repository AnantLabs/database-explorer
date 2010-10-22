package com.gs.dbex.application.delegate
{
	import com.gs.dbex.application.business.DbexApplicationServiceLocator;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.model.db.Table;
	import com.gs.dbex.vo.PaginationResultVO;
	
	import mx.rpc.AsyncToken;
	
	public class QueryRunnerDelegate
	{
		private var _responder : BaseResponder;
		private var service : Object;
		
		public function QueryRunnerDelegate(responder : BaseResponder)
		{
			service = DbexApplicationServiceLocator.getInstance().getRemoteObject("queryRunnerDelegate");
			_responder = responder;
		}
		
		public function get responder():BaseResponder{
			return _responder;
		}

		public function runSingleQuery(sql:String, connPropName:String):void{
			var token:AsyncToken = service.executeSingleQuery(sql, connPropName); 
			
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
		
		public function getPaginatedTableData(paginationResult:PaginationResultVO, table:Table, connPropName:String):void{
			var token:AsyncToken = service.getPaginatedTableData(paginationResult, table, connPropName); 
			
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
		
		public function getFilteredPaginatedTableData(table:Table, connPropName:String, filterSubQuery:String):void{
			var token:AsyncToken = service.getFilteredPaginatedTableData(table, connPropName, filterSubQuery); 
			
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
	}
}