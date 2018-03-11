package com.johnny.common;

import com.johnny.common.utils.URLUtils;

public class VersionChecker {
    public static boolean haveNewVersion() {
        String note = URLUtils.readSource("https://www.douban.com/note/206320326/");
        int begin = note.indexOf("最新版本：");
        int end = note.indexOf("<br>", begin);
        String version = note.substring(begin + 5, end);
        return !Common.VERSION.equals(version);
    }

    public static void main(String[] args) {
        System.out.println(haveNewVersion());
    }
}