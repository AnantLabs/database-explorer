package com.gs.dbex.layout
{
	import flash.geom.Point;
	
	public class Dimensions
	{
		public var coordinates:Point;
		public var height:Number;
		public var width:Number;
		
		public function Dimensions(x:Number, y:Number, height:Number, width:Number){
			coordinates = new Point();
			coordinates.x = x;
			coordinates.y = y;
			this.height = height;
			this.width = width;
		}
	}
}