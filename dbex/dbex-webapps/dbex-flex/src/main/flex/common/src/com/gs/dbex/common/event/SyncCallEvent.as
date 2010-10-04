package com.gs.dbex.common.event
{
	public class SyncCallEvent extends DbexBaseEvent
	{
		public static var DISPATCH_SYNC_CALL_EVENT:String = "syncCallEvent";
		public function SyncCallEvent()
		{
			//TODO: implement function
			super(DISPATCH_SYNC_CALL_EVENT, false, false);
		}
		
	}
}
