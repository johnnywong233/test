package simpletest.OOMdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/9/16.
 * OOM demo on String
 */
public class Demo2 {
    private static final int MB = 1024 * 512;

    private static String createLongString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append('a');
        }
        sb.append(System.nanoTime());
        return sb.toString();
    }

    //http://www.jb51.net/article/69306.htm
    public static void main(String[] args) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String longStr = createLongString(MB);
            String subStr = longStr.substring(1, 10);
            substrings.add(subStr);
        }
    }
}