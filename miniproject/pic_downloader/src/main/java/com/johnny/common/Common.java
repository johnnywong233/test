package com.johnny.common;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

public class Common {
    public static final String DEFAULT_DOC_NAME = "描述.txt";
    public static final String DEFAULT_HTML_NAME = "index.html";
    public static final String DEFAULT_HTML_RESOURCE_DIR = "resource";
    public static final String DEFAULT_RAW_DIR = "raw";
    public static final int IMAGE_DOWNLOAD_STATUS_EXISTS = 0;
    public static final int IMAGE_DOWNLOAD_STATUS_FINISH = 1;
    public static final int IMAGE_DOWNLOAD_STATUS_URL_NOT_EXISTS = 2;
    public static final String URL_HELP = "http://www.douban.com/note/206320326/";
    public static final String URL_DOUPIC = "http://www.douban.com/group/doupic/";
    public static final long TIME_PROCESS_MIN = 60000L;
    public static final long TIME_PROCESS_SLEEP = 60L;
    public static final int PROCESS_UNIT_SIZE = 50;
    public static final String CHARTSET_UTF8 = "utf-8";
    public static final String CHARTSET_GBK = "gbk";
    public static final String CHARTSET_GB2312 = "gb2312";
    public static final int DOWNLOAD_THREAD = 15;
    public static final String IMAGE_TYPE = "gif|jpg|png";
    public static final Integer AUTO_DOWNLOAD_FAIL_FILE = 5;
    public static final String HTML_TEMPLATE_IMAGE = "<div class=\"photos\"><div class=\"desc\">${owner}${desc}</div><div class=\"number\">${num}</div><a href=\"${commentURL}\" target=\"_blank\"><img src=\"${image}\"/></a></div>";
    public static final String HTML_TAG_IMAGES = "${images}";
    public static final String HTML_TAG_IMAGES_TOTAL = "${imagesTotal}";
    public static final String HTML_TAG_NAME = "${name}";
    public static final String HTML_TAG_URL = "${url}";
    public static final String HTML_TEMPLATE_PAGE = "";
    public static final String HTML_TAG_TITLE = "${title}";
    public static final String HTML_TAG_ALBUM_DESC = "${albumDesc}";
    public static final String HTML_TAG_DOWNLOAD_TIME = "${downloadTime}";
    public static final String HTML_TAG_CHARSET = "GBK";
    public static final String HTML_TAG_OWNER = "${owner}";
    public static final String HTML_TAG_DESC = "${desc}";
    public static final String HTML_TAG_COMMENT_URL = "${commentURL}";
    public static final String HTML_TAG_NUMBER = "${num}";
    public static final String HTML_TAG_IMAGE = "${image}";
    public static final String HTML_TAG_RAW = "${raw}";
    public static final Integer RAW_TYPE_LARGE = 1;
    public static final Integer RAW_TYPE_UNCOMPRESSED = 2;
    public static String VERSION = "v0.5.10";
    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String PATH_DOWNLOAD = "";
    public static String PATH_APP = "";
    public static boolean IS_UPDATE = false;
    public static boolean IS_DOWNLOAD_RAW = false;
    public static Map<String, String> failFileMap = new TreeMap<>();

    static {
        try {
            String jarPath = URLDecoder.decode(Common.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "utf-8");

            PATH_DOWNLOAD = jarPath.substring(1, jarPath.lastIndexOf("/"));
            PATH_APP = PATH_DOWNLOAD;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = Common.class.getResourceAsStream("/com.johnny/resources/html/Template.html");
        BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String str;
            while ((str = bw.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openURLWithBrowse(String url, JFrame frame) {
        if (Desktop.isDesktopSupported()) {
            try {
                URI uri = URI.create(url);

                Desktop desktop = Desktop.getDesktop();

                if (!desktop.isSupported(Action.BROWSE))
                    return;
                desktop.browse(uri);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                String msg = "无法获取系统默认浏览器，地址：" + url;
                JOptionPane.showMessageDialog(frame, msg, "555~", 0);
            }
        } else {
            String msg = "当前JDK版本过低，无法执行打开操作，地址：" + url;
            JOptionPane.showMessageDialog(frame, msg, "555~", 0);
        }
    }
}