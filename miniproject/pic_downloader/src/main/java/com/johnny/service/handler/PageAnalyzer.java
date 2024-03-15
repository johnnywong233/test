package com.johnny.service.handler;

import com.johnny.common.Console;
import com.johnny.common.utils.HTMLUtils;
import com.johnny.common.utils.URLUtils;
import com.johnny.model.Album;
import com.johnny.model.BGImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageAnalyzer {
    private static final String DEFAULT_ALBUM_NAME = "NO-NAME";
    private static String source;

    public static String findAlbumName() {
        String name;
        if (source.contains("<title>")) {
            name = source.substring(source.indexOf("<title>") + 7, source.indexOf("</title>")).replace("\\t\\n", "").trim();
        } else {
            return DEFAULT_ALBUM_NAME;
        }
        if (name.length() != 0) {
            name = HTMLUtils.htmlToText(name);

            while (name.endsWith(".")) {
                name = name.substring(0, name.length() - 1);
            }
            return name.replaceAll("[\\\\ /:*?<>|]", " ");
        }
        return DEFAULT_ALBUM_NAME;
    }

    public static String findAlbumDesc(AlbumHandler albumHandler) {
        String desc = albumHandler.getAlbumDesc(source);
        if (desc != null) {
            return HTMLUtils.htmlToText(desc);
        }
        return null;
    }

    public static List<String> findPageURL(AlbumHandler albumHandler) {
        String albumURL = albumHandler.getAlbumURL();

        List<String> pageURLList = new ArrayList<>();
        source = URLUtils.readSource(albumURL);
        String regex = albumHandler.getPageRegex();
        if (regex != null) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(source);

            int maxStartNum = 0;
            while (m.find()) {
                String url = m.group();
                int num = 0;
                Pattern tp = Pattern.compile(albumHandler.getPageTag() + "=\\d+");
                Matcher tm = tp.matcher(url);
                if (tm.find()) {
                    String pageTagStr = tm.group();
                    num = Integer.parseInt(pageTagStr.substring(pageTagStr.lastIndexOf("=") + 1));
                }

                maxStartNum = Math.max(num, maxStartNum);
            }

            int size = albumHandler.getPageSize();
            for (int i = 0; i <= maxStartNum; i += size) {
                String pageURL = albumURL;
                if (!albumURL.contains("?")) {
                    pageURL = pageURL + "?";
                } else {
                    pageURL = pageURL + "&";
                }
                pageURL = pageURL + albumHandler.getPageTag() + "=" + i;
                pageURLList.add(pageURL);
            }
        } else {
            pageURLList.add(albumURL);
        }

        for (int i = 0; i < pageURLList.size(); i++) {
            Console.print("获取图片地址-页面(" + (i + 1) + "/" + pageURLList.size() + ")：" + pageURLList.get(i));
        }
        return pageURLList;
    }

    public static Map<String, BGImage> findImageURLAndDesc(Album album, String pageURL) {
        Map<String, BGImage> result = new HashMap<>();
        String source = URLUtils.readSource(pageURL);

        String regex = "https://(\\w|\\s|\\.|-|_|/)+[.](gif|jpg|png)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        AlbumHandler albumHandler = album.getAlbumHandler();
        String imageNameRegex;
        while (m.find()) {
            String imageURL = m.group().trim();

            String imageName = imageURL.substring(imageURL.lastIndexOf("/") + 1);

            imageNameRegex = albumHandler.getImageNameRegex();
            if (imageNameRegex == null || imageName.matches(imageNameRegex)) {
                albumHandler.createBGImage(source, pageURL, imageURL, result);
            }

        }

        List<String> removeList = new ArrayList<>();
        for (Entry<String, BGImage> element : result.entrySet()) {
            if (!albumHandler.checkBGImage(element.getValue())) {
                removeList.add(element.getKey());
            }
        }
        for (String key : removeList) {
            result.remove(key);
        }
        return result;
    }
}