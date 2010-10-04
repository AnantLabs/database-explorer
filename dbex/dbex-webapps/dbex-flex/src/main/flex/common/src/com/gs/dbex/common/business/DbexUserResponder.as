package com.gs.dbex.common.business
{
	import mx.controls.Alert;
	
	public class DbexUserResponder implements BaseResponder
	{
		public function DbexUserResponder()
		{
			super();
		}

		public function onResult(event:*=null):void
		{
			Alert.show("DbexUserResponder.onResult()");
		}
		
		public function onFault(event:*=null):void
		{
			Alert.show("DbexUserResponder.onFault()"+event);
		}
		
	}
}