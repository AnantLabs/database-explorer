package com.gs.dbex.application.view
{
	import com.gs.dbex.common.model.db.ResultSetDataTable;
	import com.gs.dbex.common.view.ViewHelper;
	import com.gs.dbex.visuals.DatabaseViewerWindow;
	import com.gs.dbex.visuals.TableDetailsPanel;
	import com.gs.dbex.vo.PaginationResultVO;
	
	import flexlib.containers.SuperTabNavigator;

	public class DatabaseViewerViewHelper extends ViewHelper
	{
		public function DatabaseViewerViewHelper()
		{
			super();
		}
		
		public function setDataTable(dataTable:ResultSetDataTable):void{
			view.sqlQueryBox.resultsetDataGrid.resultSetDataTable = dataTable;
		}
		
		public function populateCurrentPageTableData(paginationResult:PaginationResultVO):void{
			/* (view as DatabaseViewerWindow).rightPanelTabNavigator.getChildren();
			var win:DatabaseViewerWindow = (view as DatabaseViewerWindow);
			var rightPanelTabNavigator:SuperTabNavigator = win.rightPanelTabNavigator;
			if(null != rightPanelTabNavigator){
				var childArr:Array = rightPanelTabNavigator.getChildren();
				if(null != childArr){
					var count:int = 0;
					for each ( var child:Object in childArr){
						if(paginationResult.currentTable.modelName == child.label){
							var tableDetailsPanel:TableDetailsPanel = child as TableDetailsPanel;
							tableDetailsPanel.tableDataPanel.paginatedDataPanel.populatePaginatedResult();
							rightPanelTabNavigator.selectedIndex = count;
							break;
						}
						count ++;
					}
				}
			}
			rightPanelTabNavigator.validateNow(); */
		}
		
		public function populateTableData(paginationResult:PaginationResultVO):void{
			(view as DatabaseViewerWindow).rightPanelTabNavigator.getChildren();
			var win:DatabaseViewerWindow = (view as DatabaseViewerWindow);
			var rightPanelTabNavigator:SuperTabNavigator = win.rightPanelTabNavigator;
			if(null != rightPanelTabNavigator){
				var childArr:Array = rightPanelTabNavigator.getChildren();
				if(null != childArr){
					var count:int = 0;
					for each ( var child:Object in childArr){
						if(paginationResult.currentTable.modelName == child.label){
							var tableDetailsPanel:TableDetailsPanel = child as TableDetailsPanel;
							tableDetailsPanel.tableDataPanel.paginatedDataPanel.populateTableData(paginationResult);
							rightPanelTabNavigator.selectedIndex = count;
							break;
						}
						count ++;
					}
				}
			}
			rightPanelTabNavigator.validateNow();
		}
	}
}