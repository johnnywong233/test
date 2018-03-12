### step
1. create Mysql DB: test
2. config application.yml set db url,username,password
3. run Junit: MysqlTest to create default user
4. run Spring Boot: WebApplicaton
5. http://localhost
6. login with username: user, password: user
--------------------------------------------
grant all privileges on *.* to 'root'@'localhost'  identified by '12345678' with grant option;

### problem
#### 1.
```
Caused by: org.hibernate.exception.GenericJDBCException: could not execute statement
Caused by: java.sql.SQLException: Incorrect string value: '\xE5\xBC\x80\xE5\x8F\x91...' for column 'name' at row 1
```
原因是中文乱码：
```
department.setName("开发部");
```
#### 2.
```
org.apache.catalina.LifecycleException: Failed to start component [Connector[HTTP/1.1-80]]
Caused by: org.apache.catalina.LifecycleException: service.getName(): "Tomcat";  Protocol handler start failed
Caused by: java.net.SocketException: Permission denied
The Tomcat connector configured to listen on port 80 failed to start. The port may already be in use or the connector may be misconfigured.
```
Mac 平台不能使用80端口？