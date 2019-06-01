## 使用Spring Cloud Feign上传文件
http://blog.didispace.com/spring-cloud-starter-dalston-2-4/
Feign官方提供子项目feign-form, 实现上传所需的 Encoder。

## 报错
### 报错1
```
The bean 'ms-content.FeignClientSpecification', defined in null, could not be registered. A bean with that name has already been defined in null and overriding is disabled.
Action:
Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
```
参考：https://blog.csdn.net/u012211603/article/details/84312709

解决方法：
在 application.yml 配置文件中加入：
```yaml
spring:
  main:
    allow-bean-definition-overriding: true
```

### 报错2
单元测试类：
```
Caused by: java.lang.IllegalStateException: No Feign Client for loadBalancing defined. Did you forget to include spring-cloud-starter-netflix-ribbon?
```
解决方法：
需要在pom文件添加eureka。

疑问：
server使用端口 8080，client 使用端口 8081，为什么会报错：
```
The Tomcat connector configured to listen on port 8081 failed to start. The port may already be in use or the connector may be misconfigured.
Verify the connector's configuration, identify and stop any process that's listening on port 8081, or configure this application to listen on another port.
```

### 报错3
```com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server
```
是因为添加：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
之后需要再启动一个eureka server。也可以直接使用他人搭建的 eureka serve。
疑问：
为什么不能访问，打不开 URL。

### 报错4
```
Caused by: com.netflix.client.ClientException: Load balancer does not have available server for client: eureka-feign-upload-server
```
UploadService 这个接口类，配置类注解：
`@FeignClient(value = "eureka-feign-upload-server")`
故而需要在 springcloud_feign_upload_server module 的 application.yml 里面配置：
```yaml
spring:
  application:
    name: eureka-feign-upload-server
```

疑问：上传文件是什么概念？难道不是应该在 springcloud_feign_upload_server module下面找到一份同样的文件吗？