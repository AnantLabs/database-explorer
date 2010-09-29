package com.gs.dbex.control
{
	import flash.events.Event;
	
	public class DbexResizeEvent extends Event
	{
		
		public var height:Number;
		public var width:Number;
		public var windowName:String; 
		
		public function DbexResizeEvent(type:String,height:Number,width:Number,name:String)
		{
			super(type)
		 	this.windowName=name; 
			this.height=height;
			this.width=width;
		
		}
	}
}