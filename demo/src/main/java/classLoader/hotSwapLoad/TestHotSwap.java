package classLoader.hotSwapLoad;

import java.lang.reflect.Method;

/**
 * Created by johnny on 2016/10/1.
 */
public class TestHotSwap {
    //http://www.blogjava.net/heavensay/archive/2012/11/07/389685.html
    public static void main(String[] args) throws Exception {
        //开启线程，如果class文件有修改，就热替换
        Thread t = new Thread(new MonitorHotSwap());
        t.start();
    }
}

class MonitorHotSwap implements Runnable {
    private Class hotClazz = null;

    @Override
    public void run() {
        try {
            while (true) {
                initLoad();
                Object hot = hotClazz.newInstance();
                Method m = hotClazz.getMethod("hot");
                m.invoke(hot, null); //打印出相关信息
                // 每隔秒重新加载一次
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载class
     */
    private void initLoad() throws Exception {
        HotSwapURLClassLoader hotSwapCL = HotSwapURLClassLoader.getClassLoader();
        // 如果Hot类被修改，会重新加载，hotClass也会返回新的
        String className = "classLoader.hotSwapLoad.Hot";
        hotClazz = hotSwapCL.loadClass(className);
    }
}