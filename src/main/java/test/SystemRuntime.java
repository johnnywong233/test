package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class SystemRuntime {
    public static void main(String ars[]) throws Exception {
        identityHashCode();
        sysTime();
        sysProp();
        getJvmInfo();
        execTest();
    }

    //鉴别两个对象在堆内存当中是否是同一个
    public static void identityHashCode() {
        //str1 str2为两个不同的String对象
        String str1 = new String("helloWorld");
        String str2 = new String("helloWorld");
        //由于String类重写了hashCode()方法 ,所以 他们的HashCode是一样的
        System.out.println(str1.hashCode() + " : " + str2.hashCode());
        //由于他们不是同一个对象，所以他们计算出来的HashCode是不同的。
        //实际上该方法使用的是最原始的HashCode计算方法即Object的HashCode计算方法
        System.out.println(System.identityHashCode(str1) + " : " + System.identityHashCode(str2));
        String str3 = "hello";
        String str4 = "hello";
        //由于他们引用的是常量池中的同一个对象 ，所以他们的HashCode是一样的
        System.out.println(System.identityHashCode(str3) + " : " + System.identityHashCode(str4));
        /*输出
			-1554135584 : -1554135584
			28705408 : 6182315
			21648882 : 21648882
		*/
    }

    public static void sysTime() {
        //获取系统当前的时间毫秒currentTimeMillis()（返回当前时刻距离UTC 1970.1.1 00:00的时间差）
        Long time = System.currentTimeMillis();
        System.out.println(time);

        Long time1 = System.nanoTime();//主要用于计算时间差单位纳秒
        Long time3 = System.currentTimeMillis();
        for (Long i = 0L; i < 999L; i++) {
        }
        Long time2 = System.nanoTime();
        Long time4 = System.currentTimeMillis();
        System.out.println(time2 - time1 + " : " + (time4 - time3));
    }

    //获取系统的环境变量信息
    public static void sysProp() throws Exception {
        Map env = System.getenv();
        //获取系统的所有环境变量
        for (Object name : env.keySet()) {
            System.out.println(name + " : " + env.get(name));
        }
        //获取系统的指定环境变量的值
        System.out.println(env.get("JAVA_HOME"));

        //获取系统的所有属性
        Properties prop = System.getProperties();
        //将系统的属性保存到配置文件中去
        prop.store(new FileOutputStream("Prop.properties"), "System properties");
        //输出特定的系统属性
        System.out.println(System.getProperty("os.name"));
    }

    public static void getJvmInfo() {
        //获取Java运行时相关的运行时对象
        Runtime rt = Runtime.getRuntime();
        System.out.println("处理器数量：" + rt.availableProcessors() + " byte");
        System.out.println("Jvm总内存数 ：" + rt.totalMemory() + " byte");
        System.out.println("Jvm空闲内存数： " + rt.freeMemory() + " byte");
        System.out.println("Jvm可用最大内存数： " + rt.maxMemory() + " byte");
    }

    public static void execTest() throws IOException {
        Runtime rt = Runtime.getRuntime();
        //在单独的进程中执行指定的字符串命令。
        rt.exec("mspaint C:\\Users\\wajian\\Documents\\Test\\t.png");
    }
}
