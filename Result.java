package com.api.common.web;

import java.io.Serializable;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

public class Result implements Serializable {

    private static final long serialVersionUID = -1651614836984397356L;

    private String code;

    private Object result;

    private String message;

    public static final String SUCCESS_CODE = "0";

    public static final String FAIL_CODE = "1";
    
    public static final String DEF_ERROR_MSG = "系统繁忙, 请稍后重试";

    private Result() {
    }

    private Result(String code, Object result, String message) {
        this.code = code;
        this.result = isEmpty(result)? Collections.emptyMap(): result;
        this.message = message;
    }
    
    private boolean isEmpty(Object obj) {
    	return obj == null || "".equals(String.valueOf(obj).trim()) || "null".equals(String.valueOf(obj));
    }

    /**
     * 成功响应
     *
     * @param result
     * @return
     */
    public static Result successResult(Object result) {
        return result(SUCCESS_CODE, result, "");
    }
    
    /**
     * 失败响应
     *
     * @param errorMsg
     * @return
     */
    public static Result failResult(String errorMsg) {
        return result(FAIL_CODE, "", errorMsg);
    }

    public static Result failResult(String code, String errorMsg) {
		code = StringUtils.isBlank(code)? FAIL_CODE : code;
		return result(code, "", errorMsg);
	}
    
    public static Result result(String code, Object result, String message) {
        return new Result(code, result, message);
    }

    public String getCode() {
        return code;
    }

    public Object getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}