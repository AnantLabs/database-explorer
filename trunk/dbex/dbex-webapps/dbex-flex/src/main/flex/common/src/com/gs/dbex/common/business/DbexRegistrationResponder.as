package com.gs.dbex.common.business
{
	import com.gs.dbex.DbexCommonModelLocator;
	import com.gs.dbex.application.view.DbexApplicationViewHelper;
	import com.gs.dbex.common.event.LoginEvent;
	import com.gs.dbex.common.event.RegisterEvent;
	import com.gs.dbex.common.view.ViewLocator;
	import com.gs.dbex.vo.UserVO;
	
	import mx.controls.Alert;
	import mx.utils.ObjectUtil;
	
	public class DbexRegistrationResponder implements BaseResponder
	{
		[Bindable]
		private var dbexCommonModelLocator:DbexCommonModelLocator = DbexCommonModelLocator.getInstance();
		
		
		public function DbexRegistrationResponder()
		{
			super();
		}

		public function onResult(event:*=null):void
		{
			Alert.show("Registration successful. Please Login.");
			if(ViewLocator.getInstance().registrationExistsFor("DbexApplicationViewHelper")){
				var viewHelper:DbexApplicationViewHelper = ViewLocator.getInstance().getViewHelper("DbexApplicationViewHelper") as DbexApplicationViewHelper;
				if(null != viewHelper){
					viewHelper.moveToLoginScreen();
				}
			}
		}
		
		public function onFault(event:*=null):void
		{
			Alert.show("Some error occured !!!\n\n"
				+ event.fault.faultCode, "Error");
			if(ViewLocator.getInstance().registrationExistsFor("DbexApplicationViewHelper")){
				var viewHelper:DbexApplicationViewHelper = ViewLocator.getInstance().getViewHelper("DbexApplicationViewHelper") as DbexApplicationViewHelper;
				if(null != viewHelper){
					viewHelper.moveToLoginScreen();
				}
			}
		}
		
	}
}