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
		
		[Bindable]
        [Embed(source="assets/images/dbex_24x24.png")]
        public var dbex24x24Icon:Class;
        
        [Bindable]
        [Embed(source="assets/images/dbex_128x128.png")]
        public var dbex128x128Icon:Class;

		// Login
		[Bindable]
        [Embed(source="assets/images/guest_user.png")]
        public var dbexGuestUserIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/locked_user.png")]
        public var dbexLockedUserIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/user-group-new.png")]
        public var dbexRegisterUserIcon:Class;
        
		// File Menu
		[Bindable]
        [Embed(source="assets/images/new_connection.gif")]
        public var newConnectionIcon:Class;

        [Bindable]
        [Embed(source="assets/images/exit.gif")]
        public var exitIcon:Class; 
		
		// window manager
		[Bindable]
		[Embed(source='assets/icons/window/window-manager.png')]
		public var windowManagerIcon:Class;
		
		// connection dialog
		[Bindable]
        [Embed(source="assets/images/move_down.png")]
        public var moveDownIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/move_up.png")]
        public var moveUpIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/folder_download.png")]
        public var loadFileIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/save_edit.gif")]
        public var saveIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/saveall_edit.gif")]
        public var saveAllIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/edit-clear.png")]
        public var clearIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/delete_edit.gif")]
        public var deleteIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/export_wiz.gif")]
        public var exportIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/keyboard_add.png")]
        public var inputIcon:Class;
        
       [Bindable]
        [Embed(source="assets/images/LoginScreenTop.png")]
        public var loginScreenTopIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/LoginScreenBottom.png")]
        public var loginScreenBottomIcon:Class;
        
        // Database Viewer
        [Bindable]
        [Embed(source="assets/images/DB_dev_perspective.gif")]
        public var databaseIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/expandall.gif")]
        public var expandAllIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/collapseall.gif")]
        public var collaspeAllIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/reload_green.png")]
        public var refreshIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/schema.gif")]
        public var schemaIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/table.gif")]
        public var tableIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/columns.gif")]
        public var columnIcon:Class;
        
        [Bindable]
        [Embed(source="assets/images/PrimaryKeyColumn.gif")]
        public var pkColumnIcon:Class;
        
		[Bindable]
        [Embed(source="assets/images/ForeignKeyColumn.gif")]
        public var fkColumnIcon:Class;
        
        
        
	}
}