package com.johnny.test;

import com.johnny.common.utils.URLUtils;

import java.io.IOException;

public class TestImageExists {
    public static void main(String[] args) throws IOException {
        boolean i = URLUtils.exists("http://img4.douban.com/view/photo/photo/public/p2112306858.jpg");
        System.out.println(i);
        System.out.println("==========");
        i = URLUtils.exists("http://img3.douban.com/view/photo/photo/public/p1215810444.jpg");
        System.out.println(i);
    }
}