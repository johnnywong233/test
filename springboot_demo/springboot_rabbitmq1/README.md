##  启动失败以及测试用例warning
执行测试用例，虽然是成功的（绿色），但是有warning：
```
Caused by: org.springframework.amqp.AmqpException: No method found for class java.lang.Integer
```
运行启动类，启动不成功，一直在打印上面的warning信息的错误堆栈stack trace。

解决方案：
[这里](http://ask.csdn.net/questions/374757)  
把@RabbitListener 注解从类级别移到方法级别的。

## controller
对于Controller层代码，在没有引入：
http://localhost:8080/send1报错"无法访问此网站，localhost 拒绝了我们的连接请求，ERR_CONNECTION_REFUSED"