package com.gs.dbex.common.event
{
	import com.gs.dbex.vo.UserVO;
	
	public class RegisterEvent extends DbexBaseEvent
	{
		public static const REGISTER_EVENT:String = "REGISTER_EVENT" ;
		
		public var userVO:UserVO;
		
		public function RegisterEvent()
		{
			super(REGISTER_EVENT, false, false);
		}
		
	}
}