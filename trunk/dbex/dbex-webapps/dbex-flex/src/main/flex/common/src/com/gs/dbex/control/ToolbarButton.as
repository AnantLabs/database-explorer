package com.gs.dbex.control
{
	import mx.controls.Button;

	public class ToolbarButton extends Button
	{
		
		private const BTN_WIDTH:int = 25;
		private const BTN_HEIGHT:int = 25;
		
		public function ToolbarButton()
		{
			super();
			super.width = BTN_WIDTH;
			super.height = BTN_HEIGHT;
			styleName = "ToolbarButton";
		}
		
	}
}