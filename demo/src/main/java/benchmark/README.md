### JMH
[jmh](https://mp.weixin.qq.com/s?__biz=MzIwMzY1OTU1NQ==&mid=2247484196&idx=1&sn=bb30731def7dcce3a78fb7d8e7c3607a)
the Java Microbenchmark Harness，它被作为Java9的一部分来发布;简化测试，它能够照看好JVM的预热、代码优化.

所有的方法几乎都可以用注解来实现, 小测试用main方法; 大型的测试，测试时间比较久、线程数比较多，加上测试的服务器需要，一般要放在Linux服务器里去执行, 可以使用生成jar包的方式来执行。
方法： 
```xml
<!--benchmark 测试插件-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>2.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <finalName>benchmarks</finalName>
                <transformers>
                    <transformer
                            implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>org.openjdk.jmh.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```
执行maven的命令:   
```
mvn clean install
java -jar target/benchmarks.jar DateBenchMark
```
jar的执行命令后面可以加上 -h 来提示可选的命令行参数，用来替换main方法中的方法。
idea jmh插件，还有jmeter插件；插件的主要功能：
- 一、帮助你创建@Benchmark方法，可以右键点击 Generate... 来触发，也可以使用快捷键 ctrl+N。
- 二、可以让你像Junit一样方便的来进行基准测试，不需要写main方法。点击某个@Benchmark方法名右键run就只会进行光标所在方法的基准测试，而如果光标在类名上，右键run的就是整个类的所有基准测试。