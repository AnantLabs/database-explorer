package com.gs.dbex.application.view
{
	import com.gs.dbex.common.model.db.ResultSetDataTable;
	import com.gs.dbex.common.view.ViewHelper;
	import com.gs.dbex.visuals.DatabaseViewerWindow;

	public class DatabaseViewerViewHelper extends ViewHelper
	{
		public function DatabaseViewerViewHelper()
		{
			super();
		}
		
		public function setDataTable(dataTable:ResultSetDataTable):void{
			view.resultsetDataGrid.resultSetDataTable = dataTable;
		}
	}
}