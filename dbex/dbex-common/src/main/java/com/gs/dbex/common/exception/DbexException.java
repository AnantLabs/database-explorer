/**
 * 
 */
package com.gs.dbex.common.exception;

/**
 * @author sabuj.das
 *
 */
public class DbexException extends Exception {

	private String exceptionCode;
	private String exceptionMessage;
	
	public DbexException() {
		
	}
	
	public DbexException(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public DbexException(String exceptionCode, String exceptionMessage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	
	
}
