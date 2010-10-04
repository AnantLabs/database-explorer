package com.gs.dbex.common.business
{
	import mx.utils.StringUtil;
	import mx.resources.ResourceBundle;
	
	public class RIAError extends Error
	{
//		[ResourceBundle("RIAMessages")] 
	 	private static var rb : ResourceBundle;
		
		public function RIAError( errorCode : String, ... rest )
		{
			super( formatMessage( errorCode, rest.toString() ) );
		}
		
		private function formatMessage( errorCode : String, ... rest ) : String
		{
			var message : String =
				StringUtil.substitute(
					resourceBundle.getString( errorCode ), rest );
			
			return StringUtil.substitute(
				"{0}: {1}",
				errorCode,
				message);
		}
		
		protected function get resourceBundle() : ResourceBundle
		{
			return rb;
		}
	}
}
