package com.api.common.exception;

public class IelpmException extends RuntimeException {

	private static final long serialVersionUID = 6401592364022805815L;

	private String errCode="";
	
	public IelpmException() {
		super();
	}

	public IelpmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IelpmException(String message, Throwable cause) {
		super(message, cause);
	}

	public IelpmException(String message) {
		super(message);
	}

	public IelpmException(Throwable cause) {
		super(cause);
	}
	
	public IelpmException(String message, String errCode) {
		super(message);
		setErrCode(errCode);
	}
	
	public String getErrCode() {
		return errCode;
	}
	
	public void setErrCode(String errCode){
		this.errCode = errCode;
	}
	
}
