package simpletest;

/**
 * Created by johnny on 2016/10/6.
 */
public class HelloJni {
    //严格对应c文件中的函数名Java_HelloJni_sayHello
    public native void sayHello();

    public static void main(String[] args) {
        HelloJni hello = new HelloJni();
        hello.sayHello();
    }

    static {
        //严格对应libhellojni.so动态链接库文件
        System.loadLibrary("hellojni");
    }
}
