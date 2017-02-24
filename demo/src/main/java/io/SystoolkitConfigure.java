package io;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by johnny on 2016/10/7.
 *
 */
public class SystoolkitConfigure implements Runnable {
    //TODO
    private static final String default_configure = "systoolkit_default";
    private static String user_configure = "systoolkit";
    //private int scanInterval = 1000*60*5;
    private int scanInterval = 1000*5;
    private static Properties config = new Properties();
    boolean isService = false;


    /**
     * 使用默认文件名的配置文件
     */
    public SystoolkitConfigure(){
    }

    /**
     * @param userConfig 用户自定义配置文件
     */
    public SystoolkitConfigure(String userConfig){
        user_configure = userConfig;
    }

    public void run() {
        ResourceBundle confRes = ResourceBundle.getBundle(default_configure);
        Enumeration<String> keys = confRes.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = confRes.getString(key);
            config.setProperty(key, value);
        }

        Properties sysProp = System.getProperties();
        config.putAll(sysProp);

        for(;;){
            try{
                confRes = ResourceBundle.getBundle(user_configure);
                keys = confRes.getKeys();
                while(keys.hasMoreElements()){
                    config.setProperty(keys.nextElement(), confRes.getString(keys.nextElement()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            if(isService)
                try{
                Thread.sleep(scanInterval);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            else break;
        }
    }

    /**
     * 线程方式启动
     * @param threadPool 线程池容器，可以为null
     */
    public void start(ExecutorService threadPool){
        isService = true;
        Thread t = new Thread(this,this.getClass().getSimpleName());
        if(null==threadPool){
            t.start();
        }else{
            threadPool.execute(t);
        }
    }

    public void stop(){
        this.isService = false;
    }

    /**
     * 设置配置文扫描间隔
     */
    public void setScanInterval(int interval){
        this.scanInterval = interval;
    }

    /**
     * 从配置文件中取值
     * @return 如果找不到(或这个键就没有值)，则返回""
     */
    public static String getValue(String key){
        String os = getOS().toLowerCase();
        String ret = "";
        if(os.contains("windows")){
            ret = config.getProperty(key+".windows","");
        }else if(os.contains("linux")){
            ret = config.getProperty(key+".linux","");
        }
        if("".equals(ret)){
            ret = config.getProperty(key, "");
        }
        return ret;
    }

    /**
     * 从配置文件中取值，并把${args.key}的格式替换为args.value
     */
    public static String getValue(String key, Map<String,String> args){
        String value = getValue(key);
        if("".equals(value.trim())) return "";

        Set<Map.Entry<String,String>> values = args.entrySet();
        for(Map.Entry<String,String> i: values){
            value = value .replaceAll("\\${1}\\{{1}"+i.getKey()+"\\}{1}", i.getValue());
        }
        return value;
    }

    public static String getOS(){
        return System.getProperty("os.name");
    }
}
