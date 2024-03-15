package simpletest.OOMdemo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/9/16.
 * OOM demo on String
 */
@Slf4j
public class Demo2 {
    private static final int MB = 1024 * 512;

    private static String createLongString(int length) {
        return "a".repeat(length) + System.nanoTime();
    }

    //http://www.jb51.net/article/69306.htm
    public static void main(String[] args) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String longStr = createLongString(MB);
            String subStr = longStr.substring(1, 10);
            substrings.add(subStr);
        }
        log.info("size:{}", substrings.size());
    }
}