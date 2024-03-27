package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:35
 */
public class PropertiesFileUtil {
    public static String getPropValue(String keyName, String propsName) {
        ResourceBundle resource = ResourceBundle.getBundle(propsName);
        return resource.getString(keyName);
    }

    /**
     * 获取配置文件中keyName对应的value
     */
    public static String getPropValue(String keyName, String propsName, String defaultValue) {
        ResourceBundle resource = ResourceBundle.getBundle(propsName);
        String value = resource.getString(keyName);
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    public static void printProp(Properties properties) {
        System.out.println("---------（方式一）------------");
        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + "=" + properties.getProperty(key));
        }

        System.out.println("---------（方式二）------------");
        //返回属性key的集合
        Set<Object> keys = properties.keySet();
        for (Object key : keys) {
            System.out.println(key.toString() + "=" + properties.get(key));
        }

        System.out.println("---------（方式三：无序）------------");
        //返回的属性键值对实体
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        System.out.println("---------（方式四）------------");
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            System.out.println(key + "=" + value);
        }
    }

    public static void main(String[] args) {
    }
}
