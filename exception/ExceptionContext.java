package com.api.common.exception;

public class ExceptionContext { 
	
	private String errorMsg;
	
	private String stackTraceInfo;
	
	private Exception ex;
	
	public ExceptionContext(String errorMsg, String stackTraceInfo) {
		this.errorMsg = errorMsg;
		this.stackTraceInfo = stackTraceInfo;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the stackTraceInfo
	 */
	public String getStackTraceInfo() {
		return stackTraceInfo;
	}

	/**
	 * @param stackTraceInfo the stackTraceInfo to set
	 */
	public void setStackTraceInfo(String stackTraceInfo) {
		this.stackTraceInfo = stackTraceInfo;
	}

	/**
	 * @return the ex
	 */
	public Exception getEx() {
		return ex;
	}

	/**
	 * @param ex the ex to set
	 */
	public void setEx(Exception ex) {
		this.ex = ex;
	}
	
	
	
}
