package com.johnny.service.handler.finder.impl;

import com.johnny.common.Console;
import com.johnny.common.utils.URLUtils;
import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumDouListFinder
        implements IAlbumURLFinder {
    private static final int PAGE_SIZE_ALBUM = 25;

    public List<String> findAlbumURL(String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        Console.print("扫描相册豆列首页：" + url);

        List<String> pageURLList = new ArrayList<>();
        String source = URLUtils.readSource(url);
        String regex = url + "\\?start=\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);

        int maxStartNum = 0;
        while (m.find()) {
            String u = m.group();
            int num = Integer.parseInt(u.substring(u.lastIndexOf("=") + 1));
            maxStartNum = num > maxStartNum ? num : maxStartNum;
        }

        for (int i = 0; i <= maxStartNum; i += PAGE_SIZE_ALBUM) {
            String u = url + "?start=" + i;
            pageURLList.add(u);
            Console.print("获取相册分页地址：" + u);
        }

        Set<String> albumURLSet = new TreeSet<>();
        for (Object aPageURLList : pageURLList) {
            source = URLUtils.readSource((String) aPageURLList);
            String albumRegex = "(http|https)://www.douban.com/photos/album/\\d+";
            Pattern pattern = Pattern.compile(albumRegex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                String u = matcher.group();
                if (!u.endsWith("/")) {
                    u = u + "/";
                }
                albumURLSet.add(u);
            }
        }
        return new ArrayList<>(albumURLSet);
    }

    public String getURLRegex() {
        return "(http|https)://www.douban.com/doulist/\\d+/";
    }
}
