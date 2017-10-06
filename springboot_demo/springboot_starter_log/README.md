##如何自定义一个starter
- 1.最紧要的是要有一个资源文件META-INF/spring.factories，里面的内容也有要求：
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
log.autoconfigure.LogAutoConfiguration
```
- 2.配置类*Properties.java，带上类注解@ConfigurationProperties(prefix = "\**")

- 3.自动配置类*AutoConfiguration.java，带上类注解：
```
@Configuration
@Conditional()
@...
@EnableConfigurationProperties(**Properties.class)
```
其中默认加载enable的properties是刚才定义的配置类。


总结：
1. 如果自动化配置类需要在程序启动的时候就加载，可以在META-INF/spring.factories文件中定义。  
如果本次加载还需要其他一些lib的话，可以使用ConditionalOnClass注解协助。  
2. 如果自动化配置类要在使用自定义注解后才加载，可以使用自定义注解+@Import注解或@ImportSelector注解完成

自定义starter写好之后，就可以像其他任何starter一样通过pom导入dependency加载使用。