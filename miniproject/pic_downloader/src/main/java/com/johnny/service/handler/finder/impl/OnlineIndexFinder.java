package com.johnny.service.handler.finder.impl;

import com.johnny.common.utils.URLUtils;
import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlineIndexFinder implements IAlbumURLFinder {
    public List<String> findAlbumURL(String url) {
        List<String> list = new ArrayList<>();
        String source = URLUtils.readSource(url);
        String regex = url + "album/\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        if (m.find()) {
            String str = m.group();
            if (str.endsWith("/"))
                list.add(str);
            else {
                list.add(str + "/");
            }
        }
        return list;
    }

    public String getURLRegex() {
        return "(http|https)://www.douban.com/online/\\d+/";
    }
}
