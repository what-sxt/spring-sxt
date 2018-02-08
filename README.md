# spring-sxt

#### 1. api正确返回格式:

<code>
{
    "code": "0",
	"message": "", 
	"result": {"xxx"}
}
</code>

#### 2. api错误返回格式:
<code>
{
    "code": "1",
	"message": "xxx", 
	"result": {}
}
</code>

#### 3. 代码格式:
![](https://raw.githubusercontent.com/what-sxt/spring-sxt/master/template.png)

#### 4. 代码格式:
<!-- 注解 -->
<mvc:annotation-driven >
  <mvc:message-converters>
    <bean id="emptyHttpMessageConverter"class="com.api.common.spring.message.conver.EmptyJsonHttpMessageConverter"/>
  </mvc:message-converters>
</mvc:annotation-driven>

<!-- 响应结果 -->
<bean id="resultResponseBodyAdvice" class="com.api.common.spring.advice.ResultResponseBodyAdvice" />

<!-- 异常处理 -->
<bean id="exceptionResolver" class="com.api.common.exception.exceptionHanlder.SpringIelpmExceptionJsonHandler" />















