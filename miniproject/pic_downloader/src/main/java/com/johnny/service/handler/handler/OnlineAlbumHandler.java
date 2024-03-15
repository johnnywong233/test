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

public class OnlineAlbumHandler extends AlbumHandler {
    public static final int PAGE_SIZE_IMAGES_ONLINE = 90;
    public static final String PAGE_TAG = "start";
    public static final String IMAGE_NAME_REGEX = "p\\d+.(gif|jpg|png)";
    public static final String ALBUM_URL_REGEX = "https://www.douban.com/online/\\d+/album/\\d+/";

    @Override
    public String getURLRegex() {
        return ALBUM_URL_REGEX;
    }

    @Override
    public String getPageRegex() {
        return super.getAlbumURL() + "\\?\\w+=\\d+";
    }

    @Override
    public boolean removeURLParameter() {
        return true;
    }

    @Override
    public Integer getPageSize() {
        return PAGE_SIZE_IMAGES_ONLINE;
    }

    @Override
    public String getPageTag() {
        return PAGE_TAG;
    }

    @Override
    public String getImageNameRegex() {
        return IMAGE_NAME_REGEX;
    }

    @Override
    public String getRawURL(String imageURL) {
        return null;
    }

    @Override
    public void createBGImage(String source, String pageURL, String imageURL, Map<String, BGImage> map) {
        String imageId = imageURL.substring(imageURL.lastIndexOf("/p") + 2, imageURL.lastIndexOf("."));
        String onlineId = pageURL.substring(pageURL.indexOf("online/") + 7, pageURL.indexOf("/album"));

        String startIndexStr = "<a href=\"https://www.douban.com/online/" + onlineId + "/photo/" + imageId + "/\" title=\"";
        int descStartIndex = source.indexOf(startIndexStr);
        String desc;
        if (descStartIndex != -1) {
            int start = descStartIndex + startIndexStr.length();
            desc = source.substring(start, source.indexOf("\"", start));
        } else {
            desc = "";
        }

        String ownerStartStr = "来自 <a href=\"";
        int ownerStartIndex = source.indexOf(ownerStartStr, descStartIndex);
        String ownerA = source.substring(ownerStartIndex + 12, source.indexOf("</a>", ownerStartIndex));
        String ownerURL = "@@@@";
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
        String ownerName = ownerA.substring(ownerA.indexOf(">") + 1);

        String commentTatolStartIndexStr = "<a href=\"https://www.douban.com/online/" + onlineId + "/photo/" + imageId + "/#comments\">";
        int commentTatolStartIndex = source.indexOf(commentTatolStartIndexStr);
        Integer commentTatol = null;
        if (commentTatolStartIndex != -1) {
            String s = source.substring(commentTatolStartIndex + commentTatolStartIndexStr.length(), source.indexOf("</a>", commentTatolStartIndex));
            commentTatol = Integer.valueOf(s.replace("回应", ""));
        }

        imageURL = imageURL.replace("thumb", "photo").trim();
        desc = desc.replace("\\t\\n", "").trim();
        if (!map.containsKey(imageURL)) {
            BGImage bgImage = new BGImage(desc, imageURL, commentTatol);
            bgImage.setOwnerName(ownerName);
            bgImage.setOwnerURL(ownerURL);
            map.put(imageURL, bgImage);
        }
    }

    @Override
    public boolean checkBGImage(BGImage bgImage) {
        return !bgImage.getUrl().contains("albumicon");
    }

    @Override
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

    @Override
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

                if (!"-".equals(info[2])) {
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

    @Override
    public String getCommentURL(Album album, BGImage image) {
        return album.getUrl().substring(0, album.getUrl().indexOf("/album/")) + "/photo/" + image.getId();
    }

    @Override
    public String getAlbumDesc(String source) {
        return null;
    }
}