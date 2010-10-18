package com.tavant.pos.common.component
{
	import com.tavant.pos.common.util.CommonUtil;
	
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	
	import mx.controls.TextInput;
	import mx.formatters.NumberBaseRoundType;
	import mx.formatters.NumberFormatter;

	[Event(name="change", type="flash.events.Event")]
	[Event(name="keyUp", type="flash.events.KeyboardEvent")]
	[Event(name="keyDown", type="flash.events.KeyboardEvent")]
	[Event(name="valueChanged", type="flash.events.Event")]
	[Event(name="focusIn", type="flash.events.Event")]
	
	public class DbexNumberInput extends TextInput
	{
		private const DEFAULT_TEXT = "0.00";
		public var afterDecimal:Number = 2;
		public var beforeDecimal:Number = 9;
		public var acceptNegetive:Boolean = false;
		private var _isPercentageField:Boolean = false;
		private var _numberValue:Number;
		private var oldVal = "";
		private var _defaultValue:String = DEFAULT_TEXT;
		
		private var _numberFormatter:NumberFormatter;
		
		[Bindable] private var showThousandsSeparator:Boolean = true;
		[Bindable] private var useNumberFormatter:Boolean = true;
		
		public function DbexNumberInput(){
			super();
			_numberFormatter = new NumberFormatter();
			_numberFormatter.decimalSeparatorTo = ".";
			_numberFormatter.thousandsSeparatorTo = ",";
			_numberFormatter.rounding = NumberBaseRoundType.NONE;
			_numberFormatter.precision = afterDecimal;
			_numberFormatter.useThousandsSeparator = true;
			_numberFormatter.useNegativeSign = acceptNegetive;
			
			this.addEventListener("change", change);
			this.addEventListener("keyUp", keyUpHandler1);
			this.addEventListener("keyDown", keyDownHandler1);
			this.addEventListener("focusOut", focusOut);
			this.addEventListener("focusIn", focusIn);
			this.addEventListener("valueChanged", valueChanged);
			this.restrict = "[\\- 0-9.0-9]";
			this.setStyle("textAlign", "right");
			super.text = _defaultValue;
		}
		
		public function set isPercentageField(value:Boolean):void{
			_isPercentageField = value;
			if(_isPercentageField){
				if(super.text == null || super.text == "" || super.text == DEFAULT_TEXT){
					super.text = "";
				}
			}
		}
		
		public function get isPercentageField():Boolean{
			return _isPercentageField;
		}
	
		override public function set text(value:String):void
    	{
    		if(isPercentageField){
    			useNumberFormatter = false;
    		}
    		if(useNumberFormatter){
    			_numberFormatter.precision = afterDecimal;
				_numberFormatter.useThousandsSeparator = true;
				_numberFormatter.useNegativeSign = acceptNegetive;
    			_numberFormatter.useThousandsSeparator = showThousandsSeparator;
    			super.text = _numberFormatter.format(value);	
    		} else {
    			super.text = value;
    		}
    		
    		numberValue = Number(CommonUtil.replaceCommaFromString(value));
    	}
    	
    	override public function get text():String{
    		if(super.text == null || super.text == ""){
    			return "";
    		}
    		if (isNaN(numberValue)){
    			return "";
    		}
    		return numberValue.toString();
    	}
		
		public function set numberValue(number:Number):void{
			if(!isNaN(number)){
				_numberValue = number;
			}else{
				_numberValue = 0;
			}
			//this.text = _numberValue.toString();
		}
		
		public function set defaultValue(value:String):void{
			if(null == value)
				value = "";
			_defaultValue = value;
			if(super.text == null || super.text == "" || super.text == DEFAULT_TEXT){
				super.text = _defaultValue;
			}
		}
		
		public function get defaultValue():String{
			return _defaultValue;
		}
		
		private function removeDecimalZero(value:String):String{
			if(null == value || "" == String(value))
				return "";
			if(null != value && DEFAULT_TEXT == String(value)){
				return "";
			}
			var dotPos:int = value.lastIndexOf(".");
			if(dotPos >= 0 && value.length > dotPos){
				var allZeros:Boolean = true;
				for(var i:int=dotPos+1; i<value.length; i++){
					var ch:String = value.charAt(i);
					if(ch != '0'){
						allZeros = false;
						break;
					}
				}
				if(allZeros){
					value = value.substring(0, dotPos);
				}
			}
			return value;
		}
		
		public function get numberValue():Number{
			
			return _numberValue;
		}
		
		public function restrictionPress(bd:Number,ad:Number):void{
			var strPass = super.text;
 			if ( validateNumberField( strPass, bd, ad ) == 'true' ){
 	    		oldVal = strPass;
 			}
 			numberValue = new Number(oldVal);
     		dispatchEvent(new flash.events.Event("valueChanged"));
 		}
	
		public function restrictionUp(bd:Number,ad:Number):void{
			var strPass = super.text;
			var retVal =  validateNumberField( strPass, bd, ad);
			if (retVal == false){
	        	this.text = oldVal;
	   		}else {
	       		oldVal = strPass;
	    	}
	    	numberValue = new Number(oldVal);
     		dispatchEvent(new flash.events.Event("valueChanged"));
		}
	
		public function restrictionfocusOut(bd:Number,ad:Number):void {
			var strPass:String = super.text;
			var retVal =  validateNumberField( strPass, bd, ad)
			if (retVal == false) {
				this.text = oldVal;
			} else {
				oldVal = strPass;
			}
			if(oldVal == "."){
				oldVal = "";
				this.text = oldVal;
				
			}
			numberValue = new Number(oldVal);
     		dispatchEvent(new flash.events.Event("valueChanged"));
		}
	
		public function restrictionDown(bd:Number,ad:Number):void{
			var strPass:String = super.text;
	        var retVal =  validateNumberField( strPass, bd, ad)
	      	if (retVal == true)   {
	          oldVal = strPass;
	      	}
	      	numberValue = new Number(oldVal);
     		dispatchEvent(new flash.events.Event("valueChanged"));
		}

		function  validateNumberField( strValue, integerPart, decimalPart) {
			var REMoneyControlsString:RegExp;
		     if(acceptNegetive){
		     	if(decimalPart == 0){
		        	REMoneyControlsString  =  
		        	new RegExp("(^\\-\\d{0," + integerPart + "}$)|(^\\d{0,"+ integerPart + "}$)");     	
		     	}
		     	else if(integerPart == 0){
		     		REMoneyControlsString  =  
		        	new RegExp("((^\\-\\0\\.\\d{0," + decimalPart +"}$)|(^\\-\\d{0,"+ integerPart + "}$)|(^\\-\\.\\d{1,"+ decimalPart + "}$))"+
 						"|((^\\d{0," + integerPart + "}\\.\\d{0," + decimalPart +"}$)|(^\\d{0,"+ integerPart + "}$)|(^\\.\\d{1,"+ decimalPart + "}$))");
		     	}
		     	else{
		        	REMoneyControlsString  =  
		        	new RegExp("((^\\-\\d{0," + integerPart + "}\\.\\d{0," + decimalPart +"}$)|(^\\-\\d{0,"+ integerPart + "}$)|(^\\-\\.\\d{1,"+ decimalPart + "}$))"+
 						"|((^\\d{0," + integerPart + "}\\.\\d{0," + decimalPart +"}$)|(^\\d{0,"+ integerPart + "}$)|(^\\.\\d{1,"+ decimalPart + "}$))");     	
		     	}
		     }else{
		     	if(decimalPart == 0){
		        	REMoneyControlsString  =  new RegExp("(^\\d{0," + integerPart + "}$)|(^\\d{0,"+ integerPart + "}$)");     	
		     	}else{
		        	REMoneyControlsString  =  new RegExp("(^\\d{0," + integerPart + "}\\.\\d{0," + decimalPart +"}$)|(^\\d{0,"+ integerPart + "}$)|(^\\.\\d{1,"+ decimalPart + "}$)");     	
		     	}
		     }
     		var retVal =  REMoneyControlsString.test(strValue) || (strValue == '');
     		return retVal;
  		}
	
		
		public function change(event:Event):void {
			useNumberFormatter = false;
			showThousandsSeparator = false;
			restrictionPress(beforeDecimal,afterDecimal);
		}
		
		public function focusOut(event:Event):void {
			showThousandsSeparator = true;
			_numberFormatter.useThousandsSeparator = showThousandsSeparator;
			restrictionfocusOut(beforeDecimal,afterDecimal);
			showThousandsSeparator = true;
			useNumberFormatter = true;
			if(!isPercentageField)
				if(super.text != ""){
					this.text = _numberFormatter.format(super.text);
				}else{
					this.text = defaultValue;
				}
				
		}
		
		public function focusIn(event:Event):void {
			useNumberFormatter = false;
			showThousandsSeparator = false;
			this.text = removeDecimalZero(CommonUtil.replaceCommaFromString(super.text));
		}
		
		public function keyUpHandler1(event:Event):void {
			useNumberFormatter = false;
			showThousandsSeparator = false;
			restrictionUp(beforeDecimal,afterDecimal);
		}
		
		public function keyDownHandler1(event:Event):void {
			useNumberFormatter = false;
			showThousandsSeparator = false;
			restrictionDown(beforeDecimal,afterDecimal);
		}
	
		public function valueChanged(event:Event):void {
			showThousandsSeparator = false;
		}
		
		
		
	}
}