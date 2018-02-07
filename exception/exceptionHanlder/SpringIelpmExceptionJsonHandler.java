package com.api.common.exception.exceptionHanlder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.api.common.exception.ExceptionContext;
import com.api.common.exception.reslover.DubboIelpmExceptionReslover;
import com.api.common.exception.reslover.ExceptionReslover;
import com.api.common.exception.reslover.InJvmExceptionReslover;
import com.api.common.web.Result;

/**
 * 异常包装json返回 <BR/>
 * TODO 日志打印使用的是log4j, 暂未支持sl4j
 * 
 * @author sxt
 */
public class SpringIelpmExceptionJsonHandler extends AbstractHandlerExceptionResolver implements InitializingBean {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private final static String LOG_PREFIX = ">>>>> ";
	
	private List<ExceptionReslover> resloverList = new ArrayList<ExceptionReslover>();
    
	@Override
	public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		HandlerMethod method = (HandlerMethod) handler;
		ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
		if(body == null) {
			this.callAppenders(method.getBean(), Level.ERROR, this.getBusinessDesc(method), ex, null);
			return new ModelAndView();
		}
		// JSON 处理
		return this.jsonModelAndView(method, ex);
	}
	
	/**
	 * JSON VIEW
	 * 
	 * @param method
	 * @param ex
	 * @return
	 */
	private ModelAndView jsonModelAndView(HandlerMethod method, Exception ex) {
		try {
			// 业务描述
			String businessDesc = this.getBusinessDesc(method);
			ExceptionContext ec = this.tryCallReslovers(ex);
			// 日志信息
			String warnMsg = ec != null? this.buildInfoWarnMsg(businessDesc, ec.getErrorMsg()): this.buildErrorWarnMsg(businessDesc);
			String showMsg = ec != null? ec.getErrorMsg(): Result.DEF_ERROR_MSG;
			// 日志级别
			Level loggerLevel = ec != null? Level.INFO: Level.ERROR;
			// 打印日志
			this.callAppenders(method.getBean(), loggerLevel, warnMsg, ex, ec);
			// 响应内容
			return this.resloverModelAndView(showMsg);
		} catch (Exception e) {
			logger.error("resolveException", e);
			return this.resloverModelAndView(Result.DEF_ERROR_MSG);
		}
	}
	
	/**
	 * 尝试处理自定义异常
	 * 
	 * @param ex
	 * @return
	 */
	private ExceptionContext tryCallReslovers(Exception ex) {
		List<ExceptionContext> es = new LinkedList<ExceptionContext>();
		for(ExceptionReslover reslover : this.resloverList) {
			ExceptionContext context = null;
			if((context = reslover.reslove(ex)) != null) {
				es.add(context);
			}
		}
		if(es.size() == 0) {
			return null;
		}
		if(es.size() > 1) {
			// 什么鬼
			logger.info("相同解析方式ExceptionReslover");
		}
		return es.get(0);
	}
	
	/**
	 * 打印日志
	 * 
	 * @param bean
	 * @param loggerLevel
	 * @param warnMsg
	 * @param ex
	 */
	private void callAppenders(Object bean, Level loggerLevel, String warnMsg, Exception ex, ExceptionContext ec) {
		logger.callAppenders(new IelpmLoggingEvent(bean.getClass().getName(), //
				logger, loggerLevel, loggerLevel == Level.INFO? this.resloveInfoMsg(warnMsg, ex, ec): warnMsg, null, loggerLevel == Level.INFO? null: ex));
	}
	
	private String resloveInfoMsg(String warnMsg, Exception ex, ExceptionContext ec) {
		return warnMsg + System.lineSeparator() + " at " + ec.getStackTraceInfo();
	}
	
	/**
	 * 响应内容
	 * 
	 * @param ex
	 * @return
	 */
	private ModelAndView resloverModelAndView(String showMsg) {
		ModelAndView mv = new ModelAndView();
		mv.setView(this.buildErrView(showMsg));
		return mv;
	}
	
	/**
	 * INFO WRAN MSG
	 * 
	 * @param busindessDesc
	 * @param msg
	 * @return
	 */
	private String buildInfoWarnMsg(String busindessDesc, String msg) {
		return LOG_PREFIX + busindessDesc + " : " + msg;
	}
	
	/**
	 * ERROR WARN MSG
	 * 
	 * @param busindessDesc
	 * @return
	 */
	private String buildErrorWarnMsg(String busindessDesc) {
		return LOG_PREFIX + busindessDesc;
	}
	
	/**
	 * 获得业务描述
	 * 
	 * @param method
	 * @return
	 */
	private String getBusinessDesc(HandlerMethod method) {
		RequestMapping mappingAnn = method.getMethodAnnotation(RequestMapping.class);
		if(mappingAnn == null) {
			return "";
		}
		String actionDesc = mappingAnn.name();
		if(StringUtils.isNotBlank(actionDesc)) {
			return actionDesc;
		}
		// 默认使用方法名
		return method.getBean().getClass().getName() + "." + method.getMethod().getName();
	}
	
	/**
	 * 创建APP JSON内容
	 * 
	 * @param msg
	 * @return
	 */
	private View buildErrView(String msg) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.addStaticAttribute("code", Result.FAIL_CODE);
		view.addStaticAttribute("result", "");
		view.addStaticAttribute("message", msg);
		return view;
	}
	
	public void setResloverList(List<ExceptionReslover> resloverList) {
		this.resloverList = resloverList;
	}

	private class IelpmLoggingEvent extends LoggingEvent {
		
		private static final long serialVersionUID = -6568168774445413231L;

		private StackTraceElement ele;
		
		public IelpmLoggingEvent(String fqnOfCategoryClass, Category logger, Priority level, Object message,
				StackTraceElement ele,
				Throwable throwable) {
			super(fqnOfCategoryClass, logger, level, message, throwable);
			this.ele = ele;
		}
		
		public LocationInfo getLocationInformation() {
			LocationInfo locationInfo = new LocationInfo(null, null);
			// 设置日志信息
			locationInfo.fullInfo = this.buildLog(ele);
			return locationInfo;
		}
		
		private String buildLog(StackTraceElement ele) {
			if(ele == null) { return "";}
			 return ele.toString();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 默认添加ExceptionReslover
		this.resloverList.add(new InJvmExceptionReslover());
		this.resloverList.add(new DubboIelpmExceptionReslover());
		// TODO 从spring 容器获得ExceptionReslover 加载
	}

}
