package file.cpdetector;

import info.monitorenter.cpdetector.io.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by johnny on 2016/8/20.
 * usage of cpdetector
 */
public class FileDetectorTest {

    /**
     * 利用第三方开源包cpdetector获取文件编码格式.
     * 1、cpDetector内置了一些常用的探测实现类,这些探测实现类的实例可以通过add方法加进来,如:ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector.
     * 2、detector按照“谁最先返回非空的探测结果,就以该结果为准”的原则.
     * 3、cpDetector是基于统计学原理的,不保证完全正确.
     */
    public static void main(String[] args) throws IOException {
        FileDetectorTest test = new FileDetectorTest();
        InputStream is = new FileInputStream(new File("D:\\Java_ex\\test\\src\\test\\resources\\simple-fuck.vm"));
        //TODO
        System.out.println(test.getEncoding(is, true));
    }

    /**
     * 获取流编码,不保证完全正确，设置检测策略 isFast为true为快速检测策略，false为正常检测
     * InputStream 支持mark,则会在检测后调用reset，外部可重新使用。
     * InputStream 流没有关闭。
     * <p>
     * 如果采用快速检测编码方式,最多会扫描8个字节，依次采用的{@see UnicodeDetector}，{@link ByteOrderMarkDetector}，
     * {@JChardetFacade}， {@link ASCIIDetector}检测。对于一些标准的unicode编码，适合这个方式或者对耗时敏感的。
     * <p>
     * 采用正常检测，读取指定字节数，如果没有指定，默认读取全部字节检测，依次采用的{@link ByteOrderMarkDetector}，{@link ParsingDetector}，{@link JChardetFacade}， {@link ASCIIDetector}检测。
     * 字节越多检测时间越长，正确率较高。
     */

    private static final Logger logger = Logger.getLogger(FileDetectorTest.class);

    /**
     * @param buffIn input stream
     * @param isFast a boolean to decide whether use fast encoding or not
     * @return Charset if is null, then detect fail
     */
    private Charset getEncoding(InputStream buffIn, boolean isFast) throws IOException {
        return getEncoding(buffIn, buffIn.available(), isFast);
    }

    public Charset getFastEncoding(InputStream buffIn) throws IOException {
        return getEncoding(buffIn, MAX_READBYTE_FAST, DEFAULT_DETECT_STRATEGY);
    }

    private Charset getEncoding(InputStream in, int size, boolean isFast) throws IOException {
        try {
            Charset charset;
            int tmpSize = in.available();
            size = size > tmpSize ? tmpSize : size;
            //if in support mark method,
            if (in.markSupported()) {
                if (isFast) {
                    size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
                    in.mark(size++);
                    charset = getFastDetector().detectCodepage(in, size);
                } else {
                    in.mark(size++);
                    charset = getDetector().detectCodepage(in, size);
                }
                in.reset();
            } else {
                if (isFast) {
                    size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
                    charset = getFastDetector().detectCodepage(in, size);
                } else {
                    charset = getDetector().detectCodepage(in, size);
                }
            }
            return charset;
        } catch (IllegalArgumentException | IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public Charset getEncoding(byte[] byteArr, boolean isFast) throws IOException {
        return getEncoding(byteArr, byteArr.length, isFast);
    }

    public Charset getFastEncoding(byte[] byteArr) throws IOException {
        return getEncoding(byteArr, MAX_READBYTE_FAST, DEFAULT_DETECT_STRATEGY);
    }

    private Charset getEncoding(byte[] byteArr, int size, boolean isFast) throws IOException {
        size = byteArr.length > size ? size : byteArr.length;
        if (isFast) {
            size = size > MAX_READBYTE_FAST ? MAX_READBYTE_FAST : size;
        }
        ByteArrayInputStream byteArrIn = new ByteArrayInputStream(byteArr, 0, size);
        BufferedInputStream in = new BufferedInputStream(byteArrIn);
        try {
            Charset charset;
            //another overriding method
            // charset = detector.detectCodepage(file.toURI().toURL());
            if (isFast) {
                charset = getFastDetector().detectCodepage(in, size);
            } else {
                charset = getDetector().detectCodepage(in, size);
            }
            return charset;
        } catch (IllegalArgumentException | IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private static CodepageDetectorProxy detector = null;
    private static CodepageDetectorProxy fastDetector = null;
    private static ParsingDetector parsingDetector = new ParsingDetector(false);
    private static ByteOrderMarkDetector byteOrderMarkDetector = new ByteOrderMarkDetector();

    //default strategy use fastDetector
    private static final boolean DEFAULT_DETECT_STRATEGY = true;

    private static final int MAX_READBYTE_FAST = 8;

    private static CodepageDetectorProxy getDetector() {
        if (detector == null) {
            detector = CodepageDetectorProxy.getInstance();
            //need two third-party jars: [antlr] can be indexed/imported by maven with org.antlr.antlr;
            //seems [chardet] can only be imported through class path

            // Add the implementations of info.monitorenter.cpdetector.io.ICodepageDetector:
            // This one is quick if we deal with unicode codepages:
            detector.add(byteOrderMarkDetector);
            // The first instance delegated to tries to detect the meta charset attribute in html pages.
            detector.add(parsingDetector);
            // This one does the tricks of exclusion and frequency detection, if first implementation is
            // unsuccessful:
//            detector.add(JChardetFacade.getInstance());
            detector.add(ASCIIDetector.getInstance());
            detector.add(UnicodeDetector.getInstance());
        }
        return detector;
    }

    private static CodepageDetectorProxy getFastDetector() {
        if (fastDetector == null) {
            fastDetector = CodepageDetectorProxy.getInstance();
            fastDetector.add(UnicodeDetector.getInstance());
            fastDetector.add(byteOrderMarkDetector);
//            fastDetector.add(JChardetFacade.getInstance());
            fastDetector.add(ASCIIDetector.getInstance());
        }
        return fastDetector;
    }

}