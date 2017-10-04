## 内存数据库hsqldb
尝试使用内存数据库，不必去在本地安装MySql或者Postgres，然后创建数据库，数据表等。  
注意需要编写一个sql脚本用于内存数据库的初始化，并写一个持久化数据的配置类PersistenceConfig，其中处理读取sql脚本的逻辑。

## 测试用例
测试用例类StudentServiceIntegrationTest和启动类AngularApplication共同使用默认的8080端口。

## springboot集成angular
添加webapp文件夹，添加index.html, app.js等资源文件，配置IntelliJ webapp目录，对于这种web工程如果报错404，一般解法是增加配置
```<packaging>war</packaging>```。然后执行```mvn install```，忽略报错信息；执行命令
```java -jar springboot_angular-0.0.1-SNAPSHOT.war```，访问页面即可。

## JWT
集成JWT，登录验证，否则资源不予显示；