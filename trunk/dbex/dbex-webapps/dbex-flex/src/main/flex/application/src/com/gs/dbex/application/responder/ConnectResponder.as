package com.gs.dbex.application.responder
{
	import com.gs.dbex.DbexCommonModelLocator;
	import com.gs.dbex.application.DbexApplicationModelLocator;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.model.db.Database;
	import com.gs.dbex.control.DbexWindowManager;
	import com.gs.dbex.exception.DbexException;
	import com.gs.dbex.layout.DbexMessageBox;
	import com.gs.dbex.layout.ResizableWindow;
	import com.gs.dbex.visuals.DatabaseViewerWindow;
	import com.gs.dbex.visuals.DbexConnectionDialog;
	import com.gs.dbex.visuals.SqlQueryBox;
	
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.messaging.messages.ErrorMessage;

	public class ConnectResponder implements BaseResponder
	{
		[Bindable]
		private var commonModelLocator:DbexCommonModelLocator = DbexCommonModelLocator.getInstance();
		
		public function ConnectResponder()
		{
		}

		public function onResult(event:*=null):void
		{
			var database:Database = event.result as Database;
			if(null == database){
				Alert.show("Not able to read the database.");
				//PopUpManager.centerPopUp(PopUpManager.createPopUp(null, DbexMessageBox, true));
				return;
			}
			DbexApplicationModelLocator.getInstance().databaseModel = database;
			var win:ResizableWindow = DbexWindowManager.getInstance().getWindow(DbexWindowManager.DBEX_CONNECTION_DIALOG) as ResizableWindow;
			(win as DbexConnectionDialog).closeWindow();
			if(null != commonModelLocator.selectedConnectionProps){
				DbexWindowManager.getInstance().openWindow(commonModelLocator.selectedConnectionProps.connectionName ,
					DatabaseViewerWindow);
			}
			else {
				DbexWindowManager.getInstance().openWindow(database.modelName,
					DatabaseViewerWindow);
			}
			
		}
		
		public function onFault(event:*=null):void
		{
			var errorMessage:ErrorMessage = event.message as ErrorMessage;
			var exception:DbexException = errorMessage.rootCause as DbexException;
			Alert.show("FAILED : \n" 
				+ exception.exceptionMessage);
			//PopUpManager.centerPopUp(PopUpManager.createPopUp(null, DbexMessageBox, true));
		}
		
	}
}