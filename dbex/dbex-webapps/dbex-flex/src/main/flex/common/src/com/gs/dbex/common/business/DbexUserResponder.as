package com.gs.dbex.common.business
{
	import com.gs.dbex.DbexCommonModelLocator;
	import com.gs.dbex.application.view.DbexApplicationViewHelper;
	import com.gs.dbex.common.view.ViewLocator;
	import com.gs.dbex.vo.UserVO;
	
	import mx.controls.Alert;
	import mx.utils.ObjectUtil;
	
	public class DbexUserResponder implements BaseResponder
	{
		[Bindable]
		private var dbexCommonModelLocator:DbexCommonModelLocator = DbexCommonModelLocator.getInstance();
		
		public function DbexUserResponder()
		{
			super();
		}

		public function onResult(event:*=null):void
		{
			if(ViewLocator.getInstance().registrationExistsFor("DbexApplicationViewHelper")){
				var viewHelper:DbexApplicationViewHelper = ViewLocator.getInstance().getViewHelper("DbexApplicationViewHelper") as DbexApplicationViewHelper;
				if(null != viewHelper){
					var userVO:UserVO = UserVO(event.result);
					dbexCommonModelLocator.loggedInUserVO = userVO;
					dbexCommonModelLocator.connectionPropertiesColl = userVO.connectionPropertiesVOs;
					trace(ObjectUtil.toString(userVO));
					viewHelper.handelLoginSuccess(userVO);
				}
			}
		}
		
		
		public function onFault(event:*=null):void
		{
			Alert.show("DbexUserResponder.onFault()"+event);
		}
		
	}
}