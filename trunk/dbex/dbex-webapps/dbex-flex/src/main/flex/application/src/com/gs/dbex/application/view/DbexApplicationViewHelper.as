package com.gs.dbex.application.view
{
	import com.gs.dbex.common.view.ViewHelper;
	import com.gs.dbex.vo.UserVO;

	public class DbexApplicationViewHelper extends ViewHelper
	{
		public function DbexApplicationViewHelper()
		{
			super();
		}
		
		public function handelLoginSuccess(data:UserVO):void{
			if(null != data){
				view.appViewStack.selectedIndex = 1;
				view.dbexToolbar.loggedInUserFullNameLabel.text = data.fullName;
			}
		}
		
		
		public function moveToLoginScreen():void{
			view.appViewStack.selectedIndex = 0;
			view.dbexLoginBox.switchUserStack.selectedIndex = 0;
		}
	}
}