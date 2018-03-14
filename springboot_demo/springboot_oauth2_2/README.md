### oauth2
启动成功，postman 请求 http://localhost:8080/house，报错：
```
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
```
如果是浏览器访问，则提示输入用户名/密码；那么现在我们需要得到用户名和密码。  
步骤：  
借助于 postman 工具，首先配置 Authorization，选择 Basic Auth，输入用户名和密码，test/123456，即 OAuth2AuthorizationConfig.java
里面 configure 方法配置的 client 和 secret。由此得到一个 header：Authorization=Basic dGVzdDoxMjM0NTY=。如果此时以为可以发送请求，则抛错：
```
{
    "error": "invalid_request",
    "error_description": "Missing grant type"
}
```
此时还需要再配置请求的 body，选择 form-data，输入三对 key-value：
```
grant_type=password
username=test-user1
password=123451
```
此处的用户名和密码是 UserServiceImpl.java 类 static 方法块里面配置的。当然应该使用数据库存储；
得到：
```
{
    "access_token": "1ca2b5a8-8288-414c-abad-96f0627f6c6c",
    "token_type": "bearer",
    "refresh_token": "ffba528d-4a9e-4199-b12c-dcd78b6f55b1",
    "expires_in": 9999,
    "scope": "read write"
}
```
此时才可以去请求 http://localhost:8080/house，配置一条 header：
```
Authorization=bearer 1ca2b5a8-8288-414c-abad-96f0627f6c6c
```
### 参考
- [oauth2理论](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
- [应用项目](http://blog.csdn.net/lightofmiracle/article/details/79151074)

### 修改 tomcat 版本
pom 文件添加：
```xml
<properties>
    <tomcat.version>9.0.0.M6</tomcat.version>
</properties>
```
一个问题，想要修改 tomcat 的默认版本，必须要添加```spring-boot-starter-tomcat```，
不要因为```spring-boot-starter-web```包含```spring-boot-starter-tomcat```，
就可以不需要添加，现在是期望更改 tomcat 的默认版本，只有直接添加这个dependency 之后，修改才可以生效。