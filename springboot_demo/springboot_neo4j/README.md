#### neo4j
Mac 安装 neo4j，下载 neo4j desktop 版本，一步步安装，新建本地数据库，会有下拉框选择 server 版本，
下载完成之后，点击 start，启动成功后，manage 按钮里面有打开浏览器的入口，即打开http://localhost:7474/webadmin，输入用户名密码，用户名是 neo4j，密码是自己设置的。


spring boot 集成 neo4j 时，使用 SDN(spring-data-neo4j)，对于 spring boot 1.5 + 版本，一个依赖足够：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```
新版本的 SDN，不需要 extends Neo4jConfiguration，类添加注解```@EnableNeo4jRepositories("*.repository")```即可。

```
java.lang.StackOverflowError
```

测试类启动报错：
```
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.transaction.PlatformTransactionManager' available
```
原因是
```
@Bean
public SessionFactory getSessionFactory() {
    return new SessionFactory(getConfiguration(), "neo4j.domain");
}
```
