package com.gs.dbex.application.responder
{
	import com.gs.dbex.application.view.DatabaseViewerViewHelper;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.model.db.ResultSetDataTable;
	import com.gs.dbex.common.view.ViewLocator;

	public class SingleQueryRunnerResponder implements BaseResponder
	{
		public function SingleQueryRunnerResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			var dataTable:ResultSetDataTable = event.result as ResultSetDataTable;
			if(null != dataTable){
				if(ViewLocator.getInstance().registrationExistsFor("databaseViewerViewHelper")){
					var helper:DatabaseViewerViewHelper = ViewLocator.getInstance().getViewHelper("databaseViewerViewHelper") as DatabaseViewerViewHelper;
					helper.setDataTable(dataTable);
				}
			}
		}
		
		public function onFault(event:*=null):void
		{
		}
		
	}
}