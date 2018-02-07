package com.api.common.spring.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.api.common.util.ObjectUtil;
import com.api.common.web.Result;

/**
 * api同一返回Result
 * 
 * @author sxt
 */
@ControllerAdvice
@SuppressWarnings("rawtypes")
public class ResultResponseBodyAdvice implements ResponseBodyAdvice {
	
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return returnType.getMethodAnnotation(ResponseBody.class) != null;
	}
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		// empty
		if(ObjectUtil.isEmpty(body)) {
			return Result.successResult("");
		}
		if(body instanceof Result // Result
				|| body.getClass().getSimpleName().toLowerCase().equals("result")) { // 兼容不同包中多余的Result
			return body;
		}
		return Result.successResult(body);
	}

}
