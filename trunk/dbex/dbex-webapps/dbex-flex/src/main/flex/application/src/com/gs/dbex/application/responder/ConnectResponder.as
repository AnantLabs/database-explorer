package com.gs.dbex.application.responder
{
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.exception.DbexException;
	
	import mx.controls.Alert;
	import mx.messaging.messages.ErrorMessage;

	public class ConnectResponder implements BaseResponder
	{
		public function ConnectResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			var obj:Object = event.result;
			Alert.show("SUCCESS");
		}
		
		public function onFault(event:*=null):void
		{
			var errorMessage:ErrorMessage = event.message as ErrorMessage;
			var exception:DbexException = errorMessage.rootCause as DbexException;
			Alert.show("FAILED : \n" 
				+ exception.exceptionMessage);
		}
		
	}
}