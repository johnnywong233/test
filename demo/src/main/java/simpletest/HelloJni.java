package simpletest;

/**
 * Created by johnny on 2016/10/6.
 */
public class HelloJni {
    public native void displayHelloJni();

    //TODO
    //http://brandnewuser.iteye.com/blog/2117182
    public static void main(String[] args) {
        HelloJni hello = new HelloJni();
        hello.displayHelloJni();
    }

    static {
        System.loadLibrary("HelloJniImpl");
    }
}
