package com.gs.dbex.util
{
	public class StringUtil
	{
		public static function hasValidContent(str:String):Boolean
		{
			if(null == str || "" == str){
				return false;
			}
			return true;
		}

	}
}