package cpdetector;

import info.monitorenter.cpdetector.io.*;

import java.io.File;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by johnny on 2016/8/20.
 * usage of cpdetector
 */
public class FileEncodeDemo {
    //TODO: 2016/10/19
    //http://andyny2006.iteye.com/blog/1986969
	private static Logger log = LoggerFactory.getLogger(FileEncodeDemo.class);
    public static void main(String args[]){
        getFileEncode("D:\\Java_ex\\test\\src\\test\\resources\\workbook.xls");
    }

    /**
     * 利用第三方开源包cpdetector获取文件编码格式.
     * 1、cpDetector内置了一些常用的探测实现类,这些探测实现类的实例可以通过add方法加进来,如:ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector.
     * 2、detector按照“谁最先返回非空的探测结果,就以该结果为准”的原则.
     * 3、cpDetector是基于统计学原理的,不保证完全正确.
     */
    private static String getFileEncode(String filePath) {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        //need two third-party jars: [antlr] can be indexed/imported by maven with org.antlr.antlr;
        //seems [chardet] can only be imported through class path
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        File file = new File(filePath);
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception e) {
            log.error(e.toString());
        }

        String charsetName = Const.GBK;
        if (charset != null) {
            if (charset.name().equals("US-ASCII")) {
                charsetName = Const.ISO_8859_1;
            } else if (charset.name().startsWith("UTF")) {
                charsetName = charset.name();//Such as:UTF-8,UTF-16BE.
            }
        }
        return charsetName;
    }
}
