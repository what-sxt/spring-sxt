package com.api.common.exception.reslover;

import com.api.common.exception.ExceptionContext; 

public class InJvmExceptionReslover implements ExceptionReslover {
	
	@Override
	public ExceptionContext reslove(Exception ex) {
		if(!ex.getClass().getName().toLowerCase().contains("ielpmexception")) {
			return null;
		}
		ExceptionContext context = new ExceptionContext(ex.getMessage(), ex.getStackTrace()[0].toString());
		return context;
	}

}
