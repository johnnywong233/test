package com.johnny.service.handler;

import com.johnny.common.Console;
import com.johnny.common.utils.URLUtils;
import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.image.ImageListComparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AlbumHandler {
    protected String albumURL;

    public AlbumHandler() {
    }

    public AlbumHandler(String albumURL) {
        this.albumURL = albumURL;
    }

    public String getAlbumURL() {
        return this.albumURL;
    }

    public void setAlbumURL(String albumURL) {
        boolean isRemoveURLPara = removeURLParameter();
        if ((isRemoveURLPara) && (albumURL.indexOf("?") > 0))
            this.albumURL = albumURL.substring(0, albumURL.indexOf("?"));
        else
            this.albumURL = albumURL;
    }

    public abstract String getAlbumDesc(String paramString);

    public abstract Integer getPageSize();

    public abstract String getPageTag();

    public String albumNameProcess(String name) {
        return name;
    }

    public abstract String getURLRegex();

    public abstract String getPageRegex();

    public abstract String getImageNameRegex();

    public boolean hasRaw() {
        return false;
    }

    public abstract String getRawURL(String paramString);

    public abstract String getCommentURL(Album paramAlbum, BGImage paramBGImage);

    public boolean removeURLParameter() {
        return false;
    }

    public abstract void createBGImage(String paramString1, String paramString2, String paramString3, Map<String, BGImage> paramMap);

    public boolean checkBGImage(BGImage bgImage) {
        return true;
    }

    public void createDescDoc(Album album) {
        List imageList = album.getPhotosList();
        Map map = new HashMap();
        for (BGImage bgImage : imageList) {
            map.put(bgImage.getUrl(), bgImage);
        }
        List keyList = new ArrayList(map.keySet());

        Collections.sort(keyList, new ImageListComparator());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(album.getPath() + "/" + "描述.txt"));

            if (album.getDesc() == null)
                bw.write(URLUtils.charset + " " + album.getUrl() + " " + album.getDate().getTime() + " -");
            else {
                bw.write(URLUtils.charset + " " + album.getUrl() + " " + album.getDate().getTime() + " " + album.getDesc());
            }
            bw.newLine();

            for (int i = 0; i < keyList.size(); i++) {
                BGImage bgImage = (BGImage) map.get(keyList.get(i));
                Integer commentTotal = bgImage.getCommentTotal();
                String commentTotalStr = commentTotal == null ? "-" : String.valueOf(commentTotal);
                bw.write(i + 1 + " " + (String) keyList.get(i) + " " + commentTotalStr + " " + bgImage.getDesc());
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
        List list = new ArrayList();
        if (descFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(descFile));

            int line = 0;
            String str;
            while ((str = reader.readLine()) != null) {
                String str;
                if (line == 0) {
                    line++;
                } else {
                    String[] info = str.split(" ", 4);

                    BGImage bgImage = new BGImage(info[0], info[1], info[3]);

                    if (!info[2].equals("-")) {
                        bgImage.setCommentTotal(Integer.valueOf(info[2]));
                    }
                    list.add(bgImage);
                }
            }
            reader.close();
        }
        return list;
    }
}
