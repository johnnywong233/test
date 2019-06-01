
### WebSecurityConfig.java 有 bean 配置，故而 CustomUserService.java 不能添加类注解 Configuration 
```
@Bean
UserDetailsService customUserService() {
    return new CustomUserService();
}
```

### 启动报错
```
Caused by: org.springframework.context.ApplicationContextException: Unable to start ServletWebServerApplicationContext due to multiple ServletWebServerFactory beans : containerCustomizer,servletContainerFactory
```
错误原因：
HttpsConfig 有两个两个配置 ServletWebServerFactory bean: containerCustomizer, servletContainerFactory
解决方法：
合并为一个方法。