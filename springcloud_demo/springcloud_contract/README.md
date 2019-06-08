在 provider module 下面执行命令：  
`mvn clean install -Dmaven.test.skip=true`  
目标：生成 spring-cloud-contract-provider-0.0.1-SNAPSHOT-stubs.jar
此时，在右侧 maven 面板找到 spring-cloud-contract-provider 应该可以看到 plugins: spring-cloud-contract, 并且有如下 run 

https://github.com/importsource/spring-cloud-contract

问题
```
Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:2.1.5.RELEASE:repackage (repackage) on project springcloud_contract_common: Execution repackage of goal org.springframework.boot:spring-boot-maven-pl
ugin:2.1.5.RELEASE:repackage failed: Unable to find main class
```

SpringCloudContractApplicationTest,
```
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'provider.SingerRestController' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
```