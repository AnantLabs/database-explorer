package com.gs.dbex.common.business
{
	import com.gs.dbex.application.view.DbexApplicationViewHelper;
	import com.gs.dbex.common.view.ViewLocator;
	
	import mx.controls.Alert;
	
	public class DbexUserResponder implements BaseResponder
	{
		public function DbexUserResponder()
		{
			super();
		}

		public function onResult(event:*=null):void
		{
			if(ViewLocator.getInstance().registrationExistsFor("DbexApplicationViewHelper")){
				var viewHelper:DbexApplicationViewHelper = ViewLocator.getInstance().getViewHelper("DbexApplicationViewHelper") as DbexApplicationViewHelper;
				if(null != viewHelper){
					viewHelper.handelLoginSuccess(event.result);
				}
			}
		}
		
		
		public function onFault(event:*=null):void
		{
			Alert.show("DbexUserResponder.onFault()"+event);
		}
		
	}
}