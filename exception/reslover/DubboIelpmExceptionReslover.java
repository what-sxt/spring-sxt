package com.api.common.exception.reslover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.api.common.exception.ExceptionContext;

public class DubboIelpmExceptionReslover implements ExceptionReslover {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static String IELPM_EXCEPTION_IDENTIFICATION = "ielpm";
	
	@Override
	public ExceptionContext reslove(Exception ex) {
		if(StringUtils.isBlank(ex.getMessage())) {
			return null;
		}
		BufferedReader buffer = new BufferedReader( new StringReader(ex.getMessage()));
		try {
			String msg = buffer.readLine();
			// DUBBO 冒号处理规则
			int index = msg.indexOf(":");
			if(index < 0) {
				return null;
			}
			String probablyException = msg.substring(0, index);
			if(!probablyException.toLowerCase().contains(IELPM_EXCEPTION_IDENTIFICATION)) {
				return null;
			}
			String errorMsg = msg.substring(index + 1).trim();
			String stackTraceInfo = "";
			// 跳过两行
			for(int i = 0; i < 2; i++) {
				stackTraceInfo = buffer.readLine();
			}
			buffer.close();
			stackTraceInfo = stackTraceInfo.substring(stackTraceInfo.indexOf("at") + 2, stackTraceInfo.length()).trim();
			ExceptionContext ied = new ExceptionContext(errorMsg, stackTraceInfo);
			return ied;
		} catch (IOException e) {
			logger.error("DubboIelpmExceptionReslover", e);
			return null;
		}
	}

}
