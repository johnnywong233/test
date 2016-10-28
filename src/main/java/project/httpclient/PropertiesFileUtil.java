package project.httpclient;

import org.apache.commons.lang3.StringUtils;

import java.util.ResourceBundle;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:35
 */
public class PropertiesFileUtil {
    public static String getPropValue(String keyName, String propsName) {

        ResourceBundle resource = ResourceBundle.getBundle(propsName);
        String value = resource.getString(keyName);
        return value;
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
}
