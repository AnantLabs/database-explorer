package com.gs.dbex.application.view
{
	import com.gs.dbex.common.view.ViewHelper;

	public class DbexApplicationViewHelper extends ViewHelper
	{
		public function DbexApplicationViewHelper()
		{
			super();
		}
		
		public function handelLoginSuccess(data:Object):void{
			if(null != data){
				var mainApp:DbexMainApp = view as DbexMainApp;
				if(null != mainApp){
					mainApp.appViewStack.selectedIndex = 1;
				}
			}
		}
	}
}