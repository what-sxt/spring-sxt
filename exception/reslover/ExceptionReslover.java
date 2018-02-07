package com.api.common.exception.reslover;

import com.api.common.exception.ExceptionContext;

/**
 * 
 * @author sxt
 */ 
public interface ExceptionReslover {
	 
	public ExceptionContext reslove(Exception ex) ;
	
}
