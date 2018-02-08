# spring-sxt

#### api响应格式:
<pre>
<code>
{
  "code": "0",       // 0: 成功, 1: 失败
  "message": "",     // 错误信息
  "result": {"xxx"}  // 正确响应内容
}
</code>
</pre>

#### 代码格式:
![](https://raw.githubusercontent.com/what-sxt/spring-sxt/master/template.png)


***
#### 1. 修改spring配置:
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
#### 2. 配置后代码():
![](https://raw.githubusercontent.com/what-sxt/spring-sxt/master/cfg-template.png)


















