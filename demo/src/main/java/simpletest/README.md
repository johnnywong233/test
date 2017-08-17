注意事项:
- 在Windows或者Linux系统上执行**```javah HelloJni```**命令时(不是```javah HelloJni.class```,也不是```javah HelloJni.java```)，请务必删除第一行的package信息;
否则会报错:```Error: Could not find class file for 'HelloJni'.```
- 需要gcc编译工具, 即需要在Linux系统完成效果演示;
- 生成.so文件的命令:```gcc -fPIC -D_REENTRANT -I/root/JDK/zulu8.21.0.1-jdk8.0.131-linux_x64/include/ -I/root/JDK/zulu8.21.0.1-jdk8.0.131-linux_x64/include/linux -shared -o hellojni.so HelloJni.c```
其中```/root/JDK/zulu8.21.0.1-jdk8.0.131-linux_x64/```是JDK安装目录, 即JAVA_HOME.
- 需要把```hellojni.so```动态库文件拷贝至```java.library.path```路径,否则报错
```Exception in thread "main" java.lang.UnsatisfiedLinkError: no hellojni in java.library.path
           at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1864)
           at java.lang.Runtime.loadLibrary0(Runtime.java:870)
           at java.lang.System.loadLibrary(System.java:1122)
           at HelloJni.<clinit>(HelloJni.java:17)
```
- 需要把```hellojni.so```重命名为```libhellojni.so```
- 以下程序打印输出```java.library.path```, 可是我配置的JDK的输出项只有:
```/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
```
并没有上面提到的JAVA_HOME路径下面的```java.library.path```, 把so文件copy至```usr/lib64/```.
```java
public class TestLibraryPath{
    public static void main(String[] args){
        String path = System.getProperty("java.library.path");
        System.out.println(path);
    }
}
```
- 以下错误信息是由于不一致的native方法名, 大小写敏感:
```Exception in thread "main" java.lang.UnsatisfiedLinkError: HelloJni.displayHelloJni()V
           at HelloJni.displayHelloJni(Native Method)
           at HelloJni.main(HelloJni.java:13)
```