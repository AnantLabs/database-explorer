package 
{
	public class DbexImageConstants
	{
		private static var imageLocator:DbexImageConstants;
		
		public function DbexImageConstants()
		{
		}
		
		public static function getInstance() : DbexImageConstants {
			if( imageLocator == null){
				imageLocator = new DbexImageConstants();
		      }
			return imageLocator;		
		}
		

		// File Menu
		[Bindable]
        [Embed(source="assets/images/new_connection.gif")]
        public var newConnectionIcon:Class;

        [Bindable]
        [Embed(source="assets/images/exit.gif")]
        public var exitIcon:Class; 
		
		
	}
}