package com.gs.dbex.application.responder
{
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.model.db.ResultSetDataTable;

	public class SingleQueryRunnerResponder implements BaseResponder
	{
		public function SingleQueryRunnerResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			var obj:ResultSetDataTable = event.result as ResultSetDataTable;
			var i:Number = obj.rowCount;
		}
		
		public function onFault(event:*=null):void
		{
		}
		
	}
}