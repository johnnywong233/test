package com.johnny.test;

import com.johnny.common.utils.URLUtils;

import java.io.IOException;

public class TestImageExists {
    public static void main(String[] args) throws IOException {
        System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/#image"));
        System.out.println("==========");
        System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795"));
        System.out.println("==========");
        System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/"));
        System.out.println("==========");
        System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/#6"));
    }
}