#### Spring MVC Validation Samples

Source code for the following articles:
* [Trigger manual validation of a form object](http://blog.codeleak.pl/2016/04/spring-mvc-manual-form-validation.html)
* [Better error messages](http://blog.codeleak.pl/2014/06/better-error-messages-with-bean.html)
* [@RequestBody validation](http://blog.codeleak.pl/2013/09/request-body-validation-in-spring-mvc-3.2.html)
* [Method-level validation](http://blog.codeleak.pl/2012/03/how-to-method-level-validation-in.html)
* [Validation Groups](http://blog.codeleak.pl/2014/08/validation-groups-in-spring-mvc.html)
* [Testing Global Errors](http://blog.codeleak.pl/2014/08/spring-mvc-test-assert-given-model-attribute-global-errors.html)

#### Validate
关于Bean validator更详细的介绍可参考博客:   http://www.cnblogs.com/beiyan/p/5946345.html
[gitee](https://gitee.com/beiyan/Validate/tree/master)


#### 报错
```
org.springframework.context.ApplicationContextException: Unable to start embedded container; nested exception is java.lang.NoClassDefFoundError: org/apache/juli/logging/LogFactory
Caused by: java.lang.NoClassDefFoundError: org/apache/juli/logging/LogFactory
	at org.apache.catalina.util.LifecycleBase.<clinit>(LifecycleBase.java:37) ~[tomcat-embed-core-8.0.8.jar:8.0.8]
Caused by: java.lang.ClassNotFoundException: org.apache.juli.logging.LogFactory
```
注意上面的版本信息：

```[tomcat-embed-core-8.0.8.jar:8.0.8]```
是通过 properties 引入的。

这种报错网上的解决方法很多，如 [here](http://blog.csdn.net/a78270528/article/details/77548779)
```xml
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-logging-juli</artifactId>
    <version>8.5.2</version>
</dependency>
```
但是出现另外一个错误：
```
Caused by: java.lang.ClassNotFoundException: org.apache.catalina.util.StandardSessionIdGenerator
org.springframework.context.ApplicationContextException: Unable to start embedded container; nested exception is org.springframework.boot.context.embedded.EmbeddedServletContainerException: Unable to start embedded Tomcat
Caused by: org.springframework.boot.context.embedded.EmbeddedServletContainerException: Unable to start embedded Tomcat
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardServer[-1]]
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardService[Tomcat]]
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat]]
Caused by: org.apache.catalina.LifecycleException: A child container failed during start
```
懵逼，一开始仅仅只是去关注下面的错误堆栈信息：```Failed to start component```
[here](http://blog.csdn.net/testcs_dn/article/details/41824497)  
知道错误的原因是：
```
Caused by: java.lang.ClassNotFoundException: org.apache.catalina.util.StandardSessionIdGenerator
```
于是又是各种百度 Google。包括查看 dependency 的包结构信息。
通过查看 spring-boot-starter-tomcat 的 pom 文件，知道这个类应该是
```xml
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-core</artifactId>
</dependency>
```
里面的类，但是并没有找到。持续懵逼。

各种摸索才知道，一开始把 springboot 的 version 从 1.3.3 改成 1.5.9，默认支持的tomcat 版本从 8.0.X 升级成 8.5.X，
如果此时还想要通过定义 properties 使用 8.0.X 版本的 tomcat，则会启动失败，抛出上面的错误堆栈。
```xml
 <properties>
    <start-class>com.johnny.validator.Application</start-class>
    <!--<tomcat.version>8.0.8</tomcat.version>-->
    <!--添加 spring-boot-starter-tomcat 依赖之后，spring boot 会自动解析这个 property-->
    <tomcat.version>8.5.3</tomcat.version>
</properties>
```
另外还要注意一个问题，groupId 为```org.apache.tomcat.embed```，下面的 artifactId，即 jar 包有6个，各个 jar 包的发布版本不太一致。