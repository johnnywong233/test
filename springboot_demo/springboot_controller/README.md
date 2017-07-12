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





