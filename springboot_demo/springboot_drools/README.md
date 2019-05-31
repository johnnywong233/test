1. [blog](https://www.baeldung.com/spring-boot-access-h2-database-multiple-apps)
2. [code](https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-boot-persistence-h2)

Spring Boot多应用访问相同的H2内存数据库
第一个应用就是 DroolsApplication 启动的 server 应用，核心方法：inMemoryH2DatabaseServer；

第二个应用就是 ClientH2Application 启动的 client 应用，核心代码：
`System.setProperty("spring.datasource.url","jdbc:h2:tcp://localhost:9090/mem:mydb");`
通过TCP访问第一个应用程序的嵌入式H2实例

两个应用共享一个配置文件 application.yml