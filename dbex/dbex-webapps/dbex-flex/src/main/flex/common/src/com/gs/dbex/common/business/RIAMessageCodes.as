package com.gs.dbex.common.business
{
	/**
	 * All messages/error codes must match the regular expression:
	 *
	 * C\d{4}[EWI]
	 *
	 * 1. The application prefix e.g. 'C'.
	 * 
	 * 2. A four-digit error code that must be unique.
	 * 
	 * 3. A single character severity indicator
	 *    (E: error, W: warning, I: informational).
	 */
	public class RIAMessageCodes
	{
	   public static const SINGLETON_EXCEPTION : String = "C0001E";
	   public static const NO_SERVICE_FOUND : String = "C0002E";
	   public static const COMMAND_ALREADY_REGISTERED : String = "C0003E";
	   public static const COMMAND_NOT_FOUND : String = "C0004E";
	   public static const VIEW_ALREADY_REGISTERED : String = "C0005E";
	   public static const VIEW_NOT_FOUND : String = "C0006E";		
	   public static const CONTEXT_KEY_DUPLICATE :String = "C0007E";
	}
}
