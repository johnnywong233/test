package com.johnny.test;


import com.johnny.common.utils.URLUtils;


public class TestGetSource {

    public static void main(String[] args) {
        System.out.println(URLUtils.readSource("http://www.douban.com/photos/album/51681522/"));
    }

}