package com.johnny.service.handler.handler;

import com.johnny.common.Console;
import com.johnny.common.utils.URLUtils;
import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.image.ImageListComparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitePublicAlbumHandler extends AlbumHandler {
    public static final int PAGE_SIZE_IMAGES_SITE = 30;
    public static final String PAGE_TAG = "start";
    public static final String IMAGE_NAME_REGEX = "p\\d+.(gif|jpg|png)";
    public static final String ALBUM_URL_REGEX = "(http|https)://site.douban.com/\\d+/widget/public_album/\\d+/";

    public String getURLRegex() {
        return "(http|https)://site.douban.com/\\d+/widget/public_album/\\d+/";
    }

    public String getPageRegex() {
        return "/widget/public_album/\\d+/\\?\\w+=\\d+";
    }

    public boolean removeURLParameter() {
        return true;
    }

    public Integer getPageSize() {
        return 30;
    }

    public String getPageTag() {
        return "start";
    }

    public String getImageNameRegex() {
        return "p\\d+.(gif|jpg|png)";
    }

    public boolean hasRaw() {
        return false;
    }

    public String getRawURL(String imageURL) {
        return null;
    }

    public void createBGImage(String source, String pageURL, String imageURL, Map<String, BGImage> map) {
        String imageId = imageURL.substring(imageURL.lastIndexOf("/p") + 2, imageURL.lastIndexOf("."));
        String siteAlbumId = pageURL.substring(pageURL.indexOf("public_album/") + 13, pageURL.lastIndexOf("/"));

        String startIndexStr = pageURL.substring(0, pageURL.indexOf(siteAlbumId)) + siteAlbumId + "/photo/" + imageId + "/\" title=\"";
        int descStartIndex = source.indexOf(startIndexStr);
        String desc;
        if (descStartIndex != -1) {
            int start = descStartIndex + startIndexStr.length();
            desc = source.substring(start, source.indexOf("\"", start));
        } else {
            desc = "";
        }

        String commentTotalStartIndexStr = pageURL.substring(0, pageURL.indexOf(siteAlbumId)) + siteAlbumId + "/photo/" + imageId + "/#comments\">";
        int commentTotalStartIndex = source.indexOf(commentTotalStartIndexStr);
        Integer commentTotal = null;
        if (commentTotalStartIndex != -1) {
            String s = source.substring(commentTotalStartIndex + commentTotalStartIndexStr.length(), source.indexOf("</a>", commentTotalStartIndex));
            commentTotal = Integer.valueOf(s.replace("回应", ""));
        }

        String ownerURL = null;
        String ownerName = null;
        if (descStartIndex != -1) {
            String ownerStartStr = "来自 <a href=\"";
            int ownerStartIndex = source.indexOf(ownerStartStr, descStartIndex);
            String ownerA = source.substring(ownerStartIndex + 12, source.indexOf("</a>", ownerStartIndex));
            try {
                ownerURL = ownerA.substring(0, ownerA.indexOf("/\">"));
            } catch (Exception e) {
                System.out.println("=====================");
                System.out.println(ownerA);
                System.out.println(ownerA.indexOf("<a href=\"") + 9);
                System.out.println(ownerA.indexOf("/\">"));
                System.out.println("=====================");
                e.printStackTrace();
            }
            ownerName = ownerA.substring(ownerA.indexOf(">") + 1);
        }

        imageURL = imageURL.replace("thumb", "photo").trim();
        desc = desc.replace("\\t\\n", "").trim();
        if ((!map.containsKey(imageURL)) || ((map.containsKey(imageURL)) && (((BGImage) map.get(imageURL)).getOwnerURL() == null) && (ownerURL != null))) {
            BGImage bgImage = new BGImage(desc, imageURL, commentTotal);
            bgImage.setOwnerURL(ownerURL);
            bgImage.setOwnerName(ownerName);
            map.put(imageURL, bgImage);
        }
    }

    public boolean checkBGImage(BGImage bgImage) {
        return !bgImage.getUrl().contains("albumicon");
    }

    public String getCommentURL(Album album, BGImage image) {
        return album.getUrl() + "photo/" + image.getId();
    }

    public String getAlbumDesc(String source) {
        String startTag = "data-desc=\"";
        if (source.contains(startTag)) {
            int startIndex = source.indexOf(startTag) + startTag.length();
            String desc = source.substring(startIndex, source.indexOf("\"", startIndex)).replace("\\t\\n", "").trim();
            if (desc.contains("【")) {
                desc = desc.substring(desc.lastIndexOf("】") + 1);
            }
            return desc;
        }
        return null;
    }

    public void createDescDoc(Album album) {
        List<BGImage> imageList = album.getPhotosList();
        Map<String, BGImage> map = new HashMap<>();
        for (BGImage bgImage : imageList) {
            map.put(bgImage.getUrl(), bgImage);
        }
        List<String> keyList = new ArrayList<>(map.keySet());

        keyList.sort(new ImageListComparator());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(album.getPath() + "/" + "描述.txt"));

            bw.write(URLUtils.charset + " " + album.getUrl() + " " + album.getDate().getTime() + " -");
            bw.newLine();

            for (int i = 0; i < keyList.size(); i++) {
                BGImage bgImage = (BGImage) map.get(keyList.get(i));
                Integer commentTotal = bgImage.getCommentTotal();
                String commentTotalStr = commentTotal == null ? "-" : String.valueOf(commentTotal);
                bw.write(i + 1 + " " + (String) keyList.get(i) + " " + commentTotalStr + " " + bgImage.getDesc());
                bw.newLine();

                bw.write(bgImage.getOwnerURL() + " " + bgImage.getOwnerName());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            Console.print("生成描述文档：成功");
        } catch (IOException e) {
            Console.print("生成描述文档：失败");
            e.printStackTrace();
        }
    }

    public List<BGImage> getBGImageFromDescDoc(File descFile)
            throws IOException {
        List<BGImage> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(descFile));
        BGImage tempBGImage = null;
        int line = 0;
        String str;
        while ((str = reader.readLine()) != null) {
            if (line == 0) {
                line++;
            } else if (line % 2 == 1) {
                String[] info = str.split(" ", 4);

                tempBGImage = new BGImage(info[0], info[1], info[3]);

                if (!info[2].equals("-")) {
                    tempBGImage.setCommentTotal(Integer.valueOf(info[2]));
                }
                line++;
            } else {
                String[] info = str.split(" ", 2);

                tempBGImage.setOwnerURL(info[0]);
                tempBGImage.setOwnerName(info[1]);
                list.add(tempBGImage);
                line++;
            }
        }
        reader.close();
        return list;
    }
}