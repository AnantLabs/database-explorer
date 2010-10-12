package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;
	import com.gs.dbex.common.model.IDbexObject;

	[RemoteClass(alias="com.gs.dbex.model.db.Column")]
	public class Column extends BaseDbModel implements IDbexObject
	{
		public var tableName:String;
		public var parentTable:Table;
		private var _columnID:Number;
		public var nullable:Boolean;
		private var _dataType:Number;
		public var typeName:String;
		private var _size:Number;
		private var _precision:Number;
		public var defaultValue:Object;
		public var primaryKey:Boolean;
		public var foreignKey:Boolean;
		public var privileges:String ;
	
		public function Column()
		{
			super();
		}
		
		public function get columnID():Number{
  			return _columnID;
	  	}
	  	public function set columnID(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_columnID = NaN;
	  		}else{
	  			_columnID = Number(value);	
	  		}
	  	}
	  	
	  	public function get dataType():Number{
  			return _dataType;
	  	}
	  	public function set dataType(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_dataType = NaN;
	  		}else{
	  			_dataType = Number(value);	
	  		}
	  	}
	  	
	  	public function get size():Number{
  			return _size;
	  	}
	  	public function set size(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_size = NaN;
	  		}else{
	  			_size = Number(value);	
	  		}
	  	}
	  	
	  	public function get precision():Number{
  			return _precision;
	  	}
	  	public function set precision(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_precision = NaN;
	  		}else{
	  			_precision = Number(value);	
	  		}
	  	}
		
		public override function toString():String
		{
			return super.modelName  + " [ " + typeName + ", " + ((!isNaN(size)) ? "(" + size + ") " : "")
				+ ((nullable) ? "NULL" : "NOTNULL") + " ]";
		}
		
		public override function equals(obj:Object):Boolean {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (!(obj is Column))
				return false;
			var other:Column = obj as Column;
			if (isNaN(columnID)) {
				if (!isNaN(other.columnID))
					return false;
			} else if (columnID != other.columnID)
				return false;
			if (tableName == null) {
				if (other.tableName != null)
					return false;
			} else if (tableName != other.tableName)
				return false;
			return true;
		}
	}
}