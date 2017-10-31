## FreeMarker other setting
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

## Controller请求的FreeMarker页面报错404
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

## replace  default tomcat with undertow
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
 
 ## spring rest doc
 [使用Asciidoc代替Markdown和Word撰写开发文档](https://my.oschina.net/gudaoxuri/blog/524132)
 [乱谈AsciiDoc的书籍编写](http://houqp.github.io/wbwa/wbwa.html)
 [SpringBoot项目生成RESTfull API的文档](http://www.jianshu.com/p/af7a6f29bf4f)
 [Spring REST Docs RESTful 服务文档](http://www.phperz.com/topics/20537.html)
 
 ## spring boot连接多个mybatis数据源
 在config下面定义两个数据源配置类，DataSource1Config，DataSource2Config：先创建DataSource，再创建SqlSessionFactory，再创建事务，最后包装到SqlSessionTemplate中。  
 同时注意DataSource1Config中所有定义的Bean都需要添加注解@Primary，意思是把数据源1作为主数据源/主库。  
 测试类User1MapperTest，User2MapperTest分别对两个数据库进行简单的验证性测试，增删查改。  
 同时添加Controller方法和类，更多功能测试。
 [参考](www.ityouknow.com/springboot/2016/11/25/springboot(七)-springboot+mybatis多数据源最简解决方案.html) 
 
 ## BindingException: Invalid bound statement (not found)
 为什么会出现这个问题？
 这个demo一开始的目的是学习spring boot集成FreeMarker，真没有什么难度；网上多半的demo程序也是这样的，简简单单的一个小功能；工作中要用到FreeMarker时，再去学习其语法；  
 那么如何提高demo的难度和复杂度呢？  
 一个方法就是再杂糅其他技术栈进来，比如把内嵌的Tomcat换成undertow；添加mybatis数据库集成功能；添加多个数据库选择配置功能。  
 问题产生：一开始单一的数据库，CityMapper.xml以及CityDao工作正常；现在添加多数据库功能，那么spring boot会扫描到DataSource1Config这个主库配置类，
 但是主配置类里面写死了mapper.xml文件的位置， 这个时候，想要看cityDao的运行效果，发现找不到对应的mapper.xml文件，故而报错，
 把之前放在这里的src/main/resource/mapper/CityMapper.xml文件移到主库配置类DataSource1Config 里面配置的路径下面，问题解决。
