package com.api.common.spring.message.conver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.api.common.util.ObjectUtil;

/**
 * controller返回void处理
 * 拦截spring默认StringHttpMessageConverter
 * 
 * @author sxt
 * 
 * @since 1.1.6
 */
public class EmptyJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
	
	public EmptyJsonHttpMessageConverter() {
		super();
		List<MediaType> mediaTypes = new ArrayList<MediaType>(this.getSupportedMediaTypes());
		mediaTypes.add(new MediaType("text", "plain", this.getCharset()));
		mediaTypes.add(MediaType.ALL);
		this.setSupportedMediaTypes(mediaTypes);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return clazz.getName().toLowerCase().equals("void") || clazz == Void.class || clazz == String.class;
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if(ObjectUtil.isEmpty(obj)) {
			obj = "";
		}
		final HttpHeaders headers = outputMessage.getHeaders();
		// 统一JSON
		headers.setContentType(MediaType.APPLICATION_JSON);
		super.writeInternal(obj, outputMessage);
	}
	
}
