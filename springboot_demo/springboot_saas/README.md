## RUN
To run the applicaiton, simply run this command:

    mvn install
or 
    
    gradlew.bat bootRun

Then go to:
* http://localhost:8080/b1/tenant_inventories/ to show inventories belongs to tenant b1
* http://localhost:8080/root_inventories/ to match root resource (not belongs to any tenant)
* http://localhost:8080/b1/root_inventories/ show the usage of @TenantResource annotation which can be used at method level to override the class level annotation
* http://localhost:8080/b1/tenant_inventories/root show the usage of @RootResource annotation which is opposite to @TenantResource

## startup failded
报错信息：
```2017-10-06 20:43:49.493 ERROR 16316 --- [ost-startStop-1] o.s.b.c.embedded.tomcat.TomcatStarter: Error starting Tomcat context. 
Exception: org.springframework.beans.factory.UnsatisfiedDependencyException. Message: Error creating bean with name 'webConfig': 
Unsatisfied dependency expressed through field 'tenantRepository'; nested exception is org.springframework.beans.factory.
BeanCreationException: Error creating bean with name 'tenantRepository': Cannot create inner bean '(inner bean)#738c1a93' of type 
[org.springframework.orm.jpa.SharedEntityManagerCreator] while setting bean property 'entityManager'; 
nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name '(inner bean)#738c1a93': 
Cannot resolve reference to bean 'entityManagerFactory' while setting constructor argument; 
nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'flywayInitializer' 
defined in class path resource [org/springframework/boot/autoconfigure/flyway/FlywayAutoConfiguration$FlywayConfiguration.class]: 
Invocation of init method failed; nested exception is org.flywaydb.core.api.FlywayException: Unable to obtain Jdbc connection from DataSource
```

