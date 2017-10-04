## springboot文件下载上传大小限制设置
- 在application.properties文件中增加如下配置：
```
spring.http.multipart.maxFileSize=10Mb
spring.http.multipart.maxRequestSize=10Mb
```
- 添加Bean配置，类注解@Configuration，详见FileUploadConfiguration

## 文件类型拦截
1. 首先可以在html文件里面通过设置accept=".json"来进行过滤设置，但是还是不能实现逻辑上的排除；
2. 后端可以实现真正意义上的拦截，通过继承HandlerInterceptorAdapter， 重写preHandle方法。

## postgres
为什么使用Postgres数据库时，建表失败，报错：
```Caused by: org.hibernate.tool.schema.spi.SchemaManagementException: Unable to execute schema management to JDBC target [create table user (id int8 not null, age int4 not null, password varchar(255) not null, user_name varchar(255) not null, primary key (id))]```

## bootstrap&thymeleaf实例
