package com.gs.dbex.common.business
{

	import mx.controls.Alert;
	import mx.core.Application;
	import mx.managers.CursorManager;
	import mx.rpc.events.FaultEvent;
	
	public class ResponseHandler
	{
      	public static function faultHandler(event:FaultEvent):void {
			event.token.faultHandler( event );
      	}
      	
        private static function handleSyncResponse():void {                 
              CursorManager.removeBusyCursor();
              Application.application.enabled=true;
        }
      	
	}
}