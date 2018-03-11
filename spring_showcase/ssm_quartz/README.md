triggerKey  ====>triggerGroupName  +"."+ triggerName
jobKey  ====>jobGroupName  +"."+ jobName

---------------------------------------------------------
Cron表达式的格式：秒 分 时 日 月 周 年(可选)。
               字段名                 允许的值                        						允许的特殊字符  
               秒                         0-59                               , - * /  
               分                         0-59                               , - * /  
               小时                     0-23                               , - * /  
               日                         1-31                               , - * ? / L W C  
               月                         1-12 or JAN-DEC         			, - * /  
               周几                     1-7 or SUN-SAT          			, - * ? / L C #  
               年 (可选字段)     empty, 1970-2099     			, - * /
 
               “?”字符：表示不确定的值
               “,”字符：指定数个值
               “-”字符：指定一个值的范围
               “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
               “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
               “W”字符：指定离给定日期最近的工作日(周一到周五)
               “#”字符：表示该月第几个周X。6#3表示该月第3个周五
               
Cron表达式范例：
                 每隔5秒执行一次：*/5 * * * * ?
                 每隔1分钟执行一次：0 */1 * * * ?
                 每天23点执行一次：0 0 23 * * ?
---------------------------------------------------------

用scheduler.getCurrentlyExecutingJobs()这个方法获取正在执行的任务，发现得到的list是空的。但是我明明有任务在执行。
研究了一下才明白，是执行任务的时间太短，所以基本得不到。写一个需要执行很长时间的任务（其实就是while(true)……）。
这个时候scheduler.getCurrentlyExecutingJobs()得到的list就不是空的了。

---------------------------------------------------------

quartz 中JobExecutionContext的使用 ：
1.JobDataMap类：
	每个JobDetail都关联了一个JobDataMap实例,基本上是提供key-value形式的数据;
当开发人员创建JobDetail的时候，可以把附加信息放到JobDataMap中，那么在execute方法中可以根据key找到需要的值。
 	JobDetail job = new JobDetail.... 
    job.getJobDataMap().put("from","snowway@vip.sina.com"); 
    ...  
   
  在execute中 
   String from = jobExecutionContext.getJobDetail().getJobDataMap().getString("from"); 
   .... 
   
   
2.假如你需要一个java.sql.Connection，用于在execute中完成某些操作，那么你可以把Connection放入Quartz的SchedulerContext中，
execute也可以访问，并且Quartz不会持久化SchedulerContext中的任何东西。

 scheduler.getContext().put("java.sql.Connection",connection);  

execute中 
   Connection con = (Connection)jobExecutionContext.getScheduler().getContext().get("java.sql.Connection");
   
#### 问题1
启动报错：
```
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'quartzScheduler' defined in class path resource [quartz.xml]: Cannot resolve reference to bean 'dataSource' while setting bean property 'dataSource'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [spring-mybatis.xml]: Instantiation of bean failed; nested exception is org.springframework.beans.BeanInstantiationException: Could not instantiate bean class [com.mchange.v2.c3p0.ComboPooledDataSource]: Constructor threw exception; nested exception is java.lang.ExceptionInInitializerError
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [spring-mybatis.xml]: Instantiation of bean failed; nested exception is org.springframework.beans.BeanInstantiationException: Could not instantiate bean class [com.mchange.v2.c3p0.ComboPooledDataSource]: Constructor threw exception; nested exception is java.lang.ExceptionInInitializerError
Caused by: org.springframework.beans.BeanInstantiationException: Could not instantiate bean class [com.mchange.v2.c3p0.ComboPooledDataSource]: Constructor threw exception; nested exception is java.lang.ExceptionInInitializerError
Caused by: java.lang.ExceptionInInitializerError
Caused by: java.lang.reflect.UndeclaredThrowableException
Caused by: java.lang.reflect.InvocationTargetException
Caused by: java.lang.NoSuchMethodError: com.mchange.v2.cfg.BasicMultiPropertiesConfig.<init>([Ljava/lang/String;Ljava/util/List;)V
```
原来是 c3p0.c3p0 与 com.mchange.c3p0冲突？
```xml
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>
```
这个 artifactId 下的最高版本是 0.9.1.2，更新 artifactId 之后：
```xml
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.2</version>
</dependency>
```
最新版本是 0.9.5.2，项目中使用的是 0.9.2.1。

启动报错是因为配置文件还是使用的之前的 artifactId 的对应的 jar 包的配置，使用新版的 artifactId 导入的 jar 包，无论使用新版的什么版本，配置情况不一样，都会启动失败。

解决方法有两种：
1. 更新配置文件，使用新版的 jar 包；
2. 使用老式的 jar，沿用老式的配置文件。

#### 问题2
idea 报错：
```
Error:java: Compilation failed: internal java compiler error
```
解决方法：
[参考](http://blog.csdn.net/u011275152/article/details/45242201)

#### 问题3
```
idea module is imported from maven any changes
```
解决方法：
[参考](https://stackoverflow.com/questions/11454822/import-maven-dependencies-in-intellij-idea)