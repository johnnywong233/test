## MongoDB数据库实例
多数据库连接，操作；

## 集成JWT和spring-security功能
- 访问根路径 http://localhost:8080，正常显示；
- 访问路径http://localhost:8080/users，报错401，unauthorized；
```
org.springframework.security.access.AccessDeniedException: Access is denied
```
- 访问路径 http://localhost:8080/auth/register注册一个具备
其中request body**可以**是这样的，email 字段非必须：
```json
{
    "username": "johnny",
    "password":"root"
}
```
request header是```"Content-Type":"application/json"```
response body:
```json
{
    "id": "59da5417c1363a0654c62963",
    "username": "johnny",
    "password": "$2a$10$WFBfG6sD7txNa27ui8GWhuNbmEhcDJMsfaWYYXJbE9KHGm40WNlAq",
    "email": null,
    "lastPasswordResetDate": 1507480599587,
    "roles": [
        "ROLE_USER"
    ]
}
```
- 再以上述request body发送 URL: http://localhost:8080/auth
得到response body：
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2hubnkiLCJjcmVhdGVkIjoxNTA3NDgwODIyOTYzLCJleHAiOjE1MDgwODU2MjJ9.o-8_C9qzV9kSxIJrszwn5Qy5gExxYWiU_DLGj9MFZ6PPZAZKCDvyBkIBzS_4DIl8D1Ht8-j8r-9AqsZVSqlYDQ"
}
```
- 现在带着这个token 再去访问 URL：http://localhost:8080/users
报错403，其response body为：
```json
{
    "timestamp": 1507483349135,
    "status": 403,
    "error": "Forbidden",
    "exception": "org.springframework.security.access.AccessDeniedException",
    "message": "Access is denied",
    "path": "/users"
}
```
- 访问 URL：http://localhost:8080/users/?username=johnny，得到正确的response body：
```json
{
    "id": "59da5417c1363a0654c62963",
    "username": "johnny",
    "password": "$2a$10$WFBfG6sD7txNa27ui8GWhuNbmEhcDJMsfaWYYXJbE9KHGm40WNlAq",
    "email": null,
    "lastPasswordResetDate": 1507480599587,
    "roles": [
        "ROLE_USER"
    ]
}
```
可以发现这个response body正好就是之前注册的时候返回的response body。

## 一个问题：
习惯使用application.properties配置文件： 
在这一步：带着这个token 再去访问 URL：http://localhost:8080/users，一直得不到正确的403错误，而是401 Unauthorized。  
分析下来发现是JwtAuthenticationTokenFilter这个配置类没有启动应有的作用。定位到authToken不对，为啥会不对？  
最后得到的大致结论是在application.properties配置文件里面配置的"jwt.tokenHead="Bearer ""起不到想要的效果，实际上是想把Bearer 传递给定义的字段private String tokenHead;  
最后给它的确实加上双引号的"Bearer "，肯定是不行的；  
把application.properties换成application.yml则不会出现这个问题，因为yaml文件格式实际上是基于json的key-value形式的，  
"Bearer "可以实现把去掉引号的内容给到tokenHead。