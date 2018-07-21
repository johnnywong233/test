package utils;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;

public class TranslateUtil {

    @Test
//    http://www.jb51.net/article/46175.htm
    //用HttpClient發送一個request給http://translate.google.com
    public void test() throws Exception {
        System.out.println(translate("朱茵", "en"));
    }

    protected static final String URL_TEMPLATE = "http://translate.google.com/?langpair={0}&text={1}";
    protected static final String ID_RESULTBOX = "result_box";
    protected static final String ENCODING = "UTF-8";
    protected static final String AUTO = "auto"; //google can judge the source language
    protected static final String TAIWAN = "zh-TW"; //
    protected static final String CHINA = "zh-CN"; //simple Chinese
    protected static final String ENGLISH = "en"; //
    protected static final String JAPAN = "ja"; //Japan

    /**
     * <pre>Google translate
     * PS: let google judge the source language
     * </pre>
     *
     * @param targetLang target language
     */
    public static String translate(final String text, final String targetLang) throws Exception {
        return translate(text, AUTO, targetLang);
    }

    /**
     * <pre>Google translate</pre>
     *
     * @param srcLang    來源語系
     * @param targetLang target language
     */
    public static String translate(final String text, final String srcLang, final String targetLang)
            throws Exception {
        InputStream is = null;
        Document doc;
        Element ele;
        try {
            // create URL string
            String url = MessageFormat.format(URL_TEMPLATE,
                    URLEncoder.encode(srcLang + "|" + targetLang, ENCODING),
                    URLEncoder.encode(text, ENCODING));
            // connect & download html
            is = HttpClientUtil.downloadAsStream(url);
            // parse html by Jsoup
            doc = Jsoup.parse(is, ENCODING, "");
            ele = doc.getElementById(ID_RESULTBOX);
            return ele.text();
        } finally {
            IOUtils.closeQuietly(is);
            is = null;
            doc = null;
            ele = null;
        }
    }

    /**
     * Google translate
     */
    public static String cn2tw(final String text) throws Exception {
        return translate(text, CHINA, TAIWAN);
    }

    /**
     * <pre>Google translate</pre>
     */
    public static String tw2cn(final String text) throws Exception {
        return translate(text, TAIWAN, CHINA);
    }

    /**
     * <pre>Google translate</pre>
     */
    public static String en2tw(final String text) throws Exception {
        return translate(text, ENGLISH, TAIWAN);
    }

    /**
     * <pre>Google translate</pre>
     */
    public static String tw2en(final String text) throws Exception {
        return translate(text, TAIWAN, ENGLISH);
    }

    /**
     * <pre>Google translate</pre>
     */
    public static String jp2tw(final String text) throws Exception {
        return translate(text, JAPAN, TAIWAN);
    }

    /**
     * <pre>Google translate</pre>
     */
    public static String tw2jp(final String text) throws Exception {
        return translate(text, TAIWAN, JAPAN);
    }

}