## instrument
JDK 的 API，从 jdk5 开始引入，尤其适用于性能监控场合，

instrument_test 测试工程，只有一个测试启动类。  
instrument1~ 是具体的应用工程类，配置 MANIFEST.MF 文件。应用工程的 pom 文件的 build 标签配置是关键代码。  
配置作用：  
mvn install 生成一个 agent.jar 文件，即 pom 文件里面配置的 finalName。
```
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/bcel/generic/Type
	at java.lang.Class.getDeclaredMethods0(Native Method)
	at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
	at java.lang.Class.getDeclaredMethod(Class.java:2128)
	at sun.instrument.InstrumentationImpl.loadClassAndStartAgent(InstrumentationImpl.java:327)
	at sun.instrument.InstrumentationImpl.loadClassAndCallPremain(InstrumentationImpl.java:401)
Caused by: java.lang.ClassNotFoundException: org.apache.bcel.generic.Type
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:338)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 5 more
FATAL ERROR in native method: processing of -javaagent failed
```
错误原因说的很清楚：缺少 class，缺少的 class 在 bcel.jar 里面，可是问题是我在 pom 文件里面有引入 dependency 啊。
原来测试工程 instrument_test 也需要添加这个依赖。

解决方法，在 instrument_test module 的 pom 文件里面添加：
```xml
<dependency>
    <groupId>org.apache.bcel</groupId>
    <artifactId>bcel</artifactId>
    <version>5.2</version>
</dependency>
```
instrument1 的 pom 文件的 build 标签配置显然更简单，也没有 MANIFEST.MF 文件。打印的信息也足够多。

如果不使用代理 agent 运行 RunEntry 方法，只会得到一行 Hello, johnny!。

运行 instrument1: ```-javaagent:/Users/wangjian/GitHub/test/miniproject/instrument_case/instrument1/target/instrument1-0.0.1-SNAPSHOT.jar```  
运行 instrument2: ```-javaagent:/Users/wangjian/GitHub/test/miniproject/instrument_case/instrument2/target/agent.jar=sayHello```

### other
javassist 是一个开源的分析、编辑和创建java字节码的类库。通过使用javassist对字节码操作可以实现动态”AOP”框架。

关于java字节码的处理，如bcel(instrument2 使用的工具包)，asm(cglib只是对asm又封装一层)。不过这些都需要直接跟虚拟机指令打交道。javassist的主要的优点，
在于简单，而且快速，直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。

### post
- [必看偏理论](https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html)
- [偏应用](http://blog.csdn.net/tterminator/article/details/54381618)
