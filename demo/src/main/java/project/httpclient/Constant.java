package project.httpclient;

import utils.PropertiesFileUtil;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:35
 */
public class Constant {
    public static final String URL_1 = PropertiesFileUtil.getPropValue("url1", "config");
    public static final String URL_2 = PropertiesFileUtil.getPropValue("url2", "config");
}
