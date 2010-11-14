package com.gs.dbex.application.delegate
{
	import com.gs.dbex.application.business.DbexApplicationServiceLocator;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.vo.ConnectionPropertiesVO;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.AsyncToken;
	
	public class DatabaseConnectionDelegate
	{
		private var _responder : BaseResponder;
		private var service : Object;
		
		public function DatabaseConnectionDelegate(responder : BaseResponder){
			service = DbexApplicationServiceLocator.getInstance().getRemoteObject("databaseConnectionDelegate");
			_responder = responder;
		}
		
		public function get responder():BaseResponder{
			return _responder;
		}
		
		public function connect(props:ConnectionPropertiesVO):void{
			var token:AsyncToken = service.connect(props); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
		
		public function testConnection(props:ConnectionPropertiesVO):void{
			var token:AsyncToken = service.testConnection(testConnection); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
		
		public function disconnect(props:ConnectionPropertiesVO):void{
			var token:AsyncToken = service.disconnect(props); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}

		public function saveConnectionProperties(props:ConnectionPropertiesVO):void{
			var token:AsyncToken = service.saveConnectionProperties(props); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
		
		public function saveAllConnectionProperties(propsList:ArrayCollection):void{
			var token:AsyncToken = service.saveAllConnectionProperties(propsList); 
			token.resultHandler = responder.onResult;
			token.faultHandler = responder.onFault;	
		}
	}
}