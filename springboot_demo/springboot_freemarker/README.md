### FreeMarker other setting
```
Set whether HttpServletRequest attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.allow-request-override=
Set whether HttpSession attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.allow-session-override=
Enable MVC view resolution for this technology.
spring.freemarker.enabled=
Set whether to expose a RequestContext for use by Spring's macro library, under the name "springMacroRequestContext".
spring.freemarker.expose-spring-macro-helpers=
Prefer file system access for template loading. File system access enables hot detection of template changes.
spring.freemarker.prefer-file-system-access=
spring.freemarker.prefix=# Prefix that gets prepended to view names when building a URL.
spring.freemarker.settings.*=# Well-known FreeMarker keys which will be passed to FreeMarker's Configuration.
spring.freemarker.view-names=# White list of view names that can be resolved.
```

### Controller请求的FreeMarker页面报错404
要看日志啊：
```
WARN 10500 --- [  main] o.s.b.a.f.FreeMarkerAutoConfiguration    : Cannot find template location(s): [classpath:/template/ # comma-separated list] (please add some templates, check your FreeMarker configuration, or set spring.freemarker.checkTemplateLocation=false)
```
```xml
<classpathentry excluding="**" kind="src" output="target/classes" path="src/main/resources">
    <attributes>
        <attribute name="maven.pomderived" value="true"/>
    </attributes>
</classpathentry>
```
依然无法解决这个问题。

### replace default tomcat with undertow
This freemarker demo also shows how to replace default tomcat with undertow, as undertow might be more lightweight than tomcat.

How to replace? Firstly modify the pom.xml file: 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```
 
 ### spring rest doc
 [使用Asciidoc代替Markdown和Word撰写开发文档](https://my.oschina.net/gudaoxuri/blog/524132)
 [乱谈AsciiDoc的书籍编写](http://houqp.github.io/wbwa/wbwa.html)
 [SpringBoot项目生成RESTfull API的文档](http://www.jianshu.com/p/af7a6f29bf4f)
 [Spring REST Docs RESTful 服务文档](http://www.phperz.com/topics/20537.html)

WebLayerTest.java 启动测试生成文档？报错：
 ```
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'fm.service.CityService' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)}
```

### swagger2markup
借助于 swagger2markup，可以把 swagger 生成的文档转换成，asciidoc，markdown，confluence；可以生成 html，也可以只生成一份文件。
未完成。
DemoApplicationTests.java 启动测试类报错：
```
java.lang.NoSuchFieldError: MULTIPLE_OF
at io.swagger.util.PropertyDeserializer.argsFromNode(PropertyDeserializer.java:196)
	at io.swagger.util.PropertyDeserializer.propertyFromNode(PropertyDeserializer.java:304)
	at io.swagger.util.PropertyDeserializer.deserialize(PropertyDeserializer.java:137)
	at io.swagger.util.PropertyDeserializer.deserialize(PropertyDeserializer.java:39)
	at com.fasterxml.jackson.databind.ObjectMapper._convert(ObjectMapper.java:3600)
	at com.fasterxml.jackson.databind.ObjectMapper.convertValue(ObjectMapper.java:3524)
	at io.swagger.parser.util.SwaggerDeserializer.response(SwaggerDeserializer.java:1057)
	at io.swagger.parser.util.SwaggerDeserializer.responses(SwaggerDeserializer.java:1028)
	at io.swagger.parser.util.SwaggerDeserializer.operation(SwaggerDeserializer.java:339)
	at io.swagger.parser.util.SwaggerDeserializer.path(SwaggerDeserializer.java:221)
	at io.swagger.parser.util.SwaggerDeserializer.paths(SwaggerDeserializer.java:186)
	at io.swagger.parser.util.SwaggerDeserializer.parseRoot(SwaggerDeserializer.java:107)
	at io.swagger.parser.util.SwaggerDeserializer.deserialize(SwaggerDeserializer.java:37)
	at io.swagger.parser.Swagger20Parser.convertToSwagger(Swagger20Parser.java:128)
	at io.swagger.parser.Swagger20Parser.read(Swagger20Parser.java:97)
	at io.swagger.parser.SwaggerParser.read(SwaggerParser.java:64)
	at io.swagger.parser.SwaggerParser.read(SwaggerParser.java:52)
	at io.github.swagger2markup.Swagger2MarkupConverter$Builder.readSwagger(Swagger2MarkupConverter.java:321)
	at io.github.swagger2markup.Swagger2MarkupConverter$Builder.<init>(Swagger2MarkupConverter.java:291)
	at io.github.swagger2markup.Swagger2MarkupConverter.from(Swagger2MarkupConverter.java:95)
```
GitHub 上面也有这个 [issue](https://github.com/Swagger2Markup/swagger2markup/issues/273)

 
 ### spring boot连接多个mybatis数据源
 在config下面定义两个数据源配置类，DataSource1Config，DataSource2Config：先创建DataSource，再创建SqlSessionFactory，再创建事务，最后包装到SqlSessionTemplate中。  
 同时注意DataSource1Config中所有定义的Bean都需要添加注解@Primary，意思是把数据源1作为主数据源/主库。  
 测试类User1MapperTest，User2MapperTest分别对两个数据库进行简单的验证性测试，增删查改。  
 同时添加Controller方法和类，更多功能测试。
 [参考](www.ityouknow.com/springboot/2016/11/25/springboot(七)-springboot+mybatis多数据源最简解决方案.html) 
 
 执行 User1MapperTest 测试用例，报错
 ```
 Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'city.users' doesn't exist
```
此处的目的很显然是想要测试多数据源的增删改查，数据库1 为 city，有两张表，city 和 users；数据库1 为 city2，有一张表city
问题在于，我创建了第三个数据库 user 新增表 users。

### BindingException: Invalid bound statement (not found)
 为什么会出现这个问题？
 这个demo一开始的目的是学习spring boot集成FreeMarker，真没有什么难度；网上多半的demo程序也是这样的，简简单单的一个小功能；工作中要用到FreeMarker时，再去学习其语法；  
 那么如何提高demo的难度和复杂度呢？  
 一个方法就是再杂糅其他技术栈进来，比如把内嵌的Tomcat换成undertow；添加mybatis数据库集成功能；添加多个数据库选择配置功能。  
 问题产生：一开始单一的数据库，CityMapper.xml以及CityDao工作正常；现在添加多数据库功能，那么spring boot会扫描到DataSource1Config这个主库配置类，
 但是主配置类里面写死了mapper.xml文件的位置， 这个时候，想要看cityDao的运行效果，发现找不到对应的mapper.xml文件，故而报错，
 把之前放在这里的src/main/resource/mapper/CityMapper.xml文件移到主库配置类DataSource1Config 里面配置的路径下面，问题解决。
