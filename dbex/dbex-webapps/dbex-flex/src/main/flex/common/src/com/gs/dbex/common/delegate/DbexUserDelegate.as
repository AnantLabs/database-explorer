package com.gs.dbex.common.delegate
{
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.business.CommonServiceLocator;
	
	import mx.rpc.AsyncToken;
	
	public class DbexUserDelegate
	{
		private var _responder : BaseResponder;
		private var service : Object;
		
		public function DbexUserDelegate(responder : BaseResponder){
			service = CommonServiceLocator.getInstance().getRemoteObject("dbexUserDelegate");
			_responder = responder;
		}
		
		public function get responder():BaseResponder{
			return _responder;
		}
		
		public function login(userName:String, password:String):void{
			var token:AsyncToken = service.login(userName, password); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
	}
}