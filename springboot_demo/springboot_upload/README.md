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

