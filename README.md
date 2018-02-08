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
<pre>
<code>
<font style='font-family:Comic Sans MS'>
 &lt;!-- 注解 --&gt;
&lt;mvc:annotation-driven &gt;
  &lt;mvc:message-converters&gt;
	&lt;bean id=&quot;emptyHttpMessageConverter&quot; class=&quot;com.api.common.spring.message.conver.EmptyJsonHttpMessageConverter&quot;/&gt;
  &lt;/mvc:message-converters&gt;
&lt;/mvc:annotation-driven&gt;
	
&lt;!-- 响应结果 --&gt;
&lt;bean id=&quot;resultResponseBodyAdvice&quot; class=&quot;com.api.common.spring.advice.ResultResponseBodyAdvice&quot; /&gt;
	
&lt;!-- 异常处理 --&gt;
&lt;bean id=&quot;exceptionResolver&quot; class=&quot;com.api.common.exception.exceptionHanlder.SpringIelpmExceptionJsonHandler&quot; /&gt;
	</font>
</code></pre>


















