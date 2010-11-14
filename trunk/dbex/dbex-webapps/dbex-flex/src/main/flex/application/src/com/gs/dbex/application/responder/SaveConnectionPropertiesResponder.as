package com.gs.dbex.application.responder
{
	import com.gs.dbex.common.business.BaseResponder;
	
	import mx.controls.Alert;

	public class SaveConnectionPropertiesResponder implements BaseResponder
	{
		public function SaveConnectionPropertiesResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			Alert.show("Save successful.");
		}
		
		public function onFault(event:*=null):void
		{
			Alert.show("Save Failed.");
		}
		
	}
}