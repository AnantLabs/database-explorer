package com.gs.dbex.control
{
	import mx.controls.Button;

	public class ToolbarButton extends Button
	{
		
		private const BTN_WIDTH:int = 35;
		private const BTN_HEIGHT:int = 30;
		
		public function ToolbarButton()
		{
			super();
			super.width = BTN_WIDTH;
			super.height = BTN_HEIGHT;
		}
		
	}
}