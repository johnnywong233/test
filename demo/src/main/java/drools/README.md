

## 异常排查
一开始报错：
```
java.lang.NoSuchMethodError: org.eclipse.jdt.internal.compiler.CompilationResult.getProblems()[Lorg/eclipse/jdt/core/compiler/CategorizedProblem;
```
这种错误已经遇到无数次，解决方法就是全局搜索类 CompilationResult，全局搜索的意思是包括哪些引入的第三方 jar(废话)。然后发现hadoop-core-1.2.1.pom 文件里面有
```xml
<dependency>
  <groupId>org.eclipse.jdt</groupId>
  <artifactId>core</artifactId>
  <version>3.1.1</version>
</dependency>
```
把这个 exclude 掉。又报错：
```
Exception in thread "main" java.lang.RuntimeException: wrong class format
Caused by: org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException
```
很是懵逼，网上搜索发现[here](https://blog.csdn.net/zhangjikuan/article/details/79356901)
于是得到解决方法：
添加
```xml
<dependency>
    <groupId>org.eclipse.jdt</groupId>
    <artifactId>org.eclipse.jdt.core</artifactId>
    <version>3.13.101</version>
</dependency>
```
同时
```xml
<dependency>
    <groupId>org.drools</groupId>
    <artifactId>drools-compiler</artifactId>
    <version>5.0.1</version>
    <exclusions>
        <exclusion>
            <artifactId>core</artifactId>
            <groupId>org.eclipse.jdt</groupId>
        </exclusion>
    </exclusions>
</dependency>
```
