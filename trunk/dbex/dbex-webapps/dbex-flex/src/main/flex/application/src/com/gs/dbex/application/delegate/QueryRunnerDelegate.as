package com.gs.dbex.application.delegate
{
	import com.gs.dbex.application.business.DbexApplicationServiceLocator;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.vo.ConnectionPropertiesVO;
	
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
	}
}