## controller基本用法
参加controller目录代码

## thymeleaf/bootstrap/spring EL基本使用
html文件，以及从application.properties文件取值，而不是在代码里面hard code

## HTTPS
springboot集成https，重点的逻辑代码看WebConfig.java
### How to generate test.jks?
#### method 1
NOTED: better use cmd, not git bash window(MINGW64), 出现乱码
```
keytool -genkey -alias test -keyalg RSA -keysize 1024 -keystore test.jks -validity 365
```
查看JKS中生成的证书的详细信息
```
keytool -list -v -keystore test.jks
```
导出证书为cer格式，可直接双击安装到浏览器（本机）
```
keytool -alias test -exportcert -keystore test.jks -file test.cer
```
#### method 2
//TODO

See [here](http://blog.csdn.net/y_xianjun/article/details/48002577), but failed at step 5

## JWT
springboot集成JWT，实现一个简单的rest请求登录验证；

## 自定义starter
自定义starter，在这个demo里面调用自定义starter。