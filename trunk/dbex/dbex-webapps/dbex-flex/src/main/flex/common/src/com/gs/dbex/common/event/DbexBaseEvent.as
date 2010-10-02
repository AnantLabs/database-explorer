package com.gs.dbex.common.event
{
	import flash.events.Event;

	public class DbexBaseEvent extends Event
	{
		
		public var data : *;
		public var responderName : Class;
		
		public function DbexBaseEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}