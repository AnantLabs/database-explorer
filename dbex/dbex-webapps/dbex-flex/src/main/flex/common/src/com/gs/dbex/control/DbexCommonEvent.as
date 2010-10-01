package com.gs.dbex.control
{
	import flash.events.Event;

	public class DbexCommonEvent extends Event
	{
		public static var APPLY_EVENT:String = "applyEvent";
		public static var CANCEL_EVENT:String = "cancelEvent";
		
		public var data:Object;
		
		public function DbexCommonEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}