run的方式不行，需要以
```java -classpath aspectjtools.jar:aspectjrt.jar:spring-core-4.3.9.RELEASE.jar org.aspectj.tools.ajc.Main
 -d bin -source 1.8 aspectJ/*$java -classpath bin:aspectjrt:spring-core-4.3.9.RELEASE.jar:aspectjweaver.j
ar aspectJ.AspectJTestClient
```
这种方式运行，但是由于文章太老旧，jar包升级，不知道那个版本的jar才能运行，一直报错：
```Error: Could not find or load main class org.aspectj.tools.ajc.Main```