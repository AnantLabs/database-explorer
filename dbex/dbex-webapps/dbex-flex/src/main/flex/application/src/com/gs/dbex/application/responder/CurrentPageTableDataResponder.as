package com.gs.dbex.application.responder
{
	import com.gs.dbex.application.DbexApplicationModelLocator;
	import com.gs.dbex.application.view.DatabaseViewerViewHelper;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.view.ViewLocator;
	import com.gs.dbex.vo.PaginationResultVO;

	public class CurrentPageTableDataResponder implements BaseResponder
	{
		public function CurrentPageTableDataResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			var paginationResult:PaginationResultVO = new PaginationResultVO();
			paginationResult.currentTable = DbexApplicationModelLocator.getInstance().selectedTable;
			if(null != event && null != event.result){
				paginationResult = event.result as PaginationResultVO;
				DbexApplicationModelLocator.getInstance().paginationResultVoMap[paginationResult.currentTable.modelName] = paginationResult;
			}
			if(ViewLocator.getInstance().registrationExistsFor("databaseViewerViewHelper")){
				var databaseViewerViewHelper:DatabaseViewerViewHelper = ViewLocator.getInstance().getViewHelper("databaseViewerViewHelper") as DatabaseViewerViewHelper;
				databaseViewerViewHelper.populateCurrentPageTableData(paginationResult);
			}
		}
		
		public function onFault(event:*=null):void
		{
		}
		
	}
}