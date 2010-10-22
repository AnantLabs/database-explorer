package com.gs.dbex.application.view
{
	import com.gs.dbex.application.DbexApplicationModelLocator;
	import com.gs.dbex.common.view.ViewHelper;
	import com.gs.dbex.visuals.TableDataPanel;
	import com.gs.dbex.vo.PaginationResultVO;
	
	public class TableDataPanelViewHelper extends ViewHelper
	{
		[Bindable]
		private var appModelLocator:DbexApplicationModelLocator = DbexApplicationModelLocator.getInstance();
			
		public function TableDataPanelViewHelper()
		{
			super();
		}

		public function populatePaginationResult(result:PaginationResultVO):void{
			appModelLocator.paginationResultVo = result;
			(view as TableDataPanel).paginatedDataPanel.showTableData();
		}
	}
}