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

public class AlbumListFinder
        implements IAlbumURLFinder {
    private static final int PAGE_SIZE_ALBUM = 16;

    public List<String> findAlbumURL(String albumListURL) {
        if (albumListURL.endsWith("/")) {
            albumListURL = albumListURL.substring(0, albumListURL.length() - 1);
        }
        Console.print("扫描相册列表首页：" + albumListURL);

        List pageURLList = new ArrayList();
        String source = URLUtils.readSource(albumListURL);
        String regex = albumListURL + "\\?\\w+=\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);

        int maxStartNum = 0;
        while (m.find()) {
            String url = m.group();
            int num = Integer.parseInt(url.substring(url.lastIndexOf("=") + 1));
            maxStartNum = num > maxStartNum ? num : maxStartNum;
        }

        for (int i = 0; i <= maxStartNum; i += 16) {
            String u = albumListURL + "?start=" + i;
            pageURLList.add(u);
            Console.print("获取相册分页地址：" + u);
        }

        Set albumURLSet = new TreeSet();
        for (int i = 0; i < pageURLList.size(); i++) {
            source = URLUtils.readSource((String) pageURLList.get(i));
            String albumRegex = "(http|https)://www.douban.com/photos/album/\\d+";
            Pattern pattern = Pattern.compile(albumRegex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                String url = matcher.group();
                if (!url.endsWith("/")) {
                    url = url + "/";
                }
                albumURLSet.add(url);
            }
        }
        return new ArrayList(albumURLSet);
    }

    public String getURLRegex() {
        return "(http|https)://www.douban.com/people/\\w+/photos/";
    }
}
