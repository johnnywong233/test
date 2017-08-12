package com.johnny.service.handler.handler;

import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;

import java.util.Map;

public class DefaultAlbumHandler extends AlbumHandler {
    public String getURLRegex() {
        return null;
    }

    public String getAlbumURL() {
        String url = super.getAlbumURL();
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    public String getPageRegex() {
        return null;
    }

    public Integer getPageSize() {
        return 0;
    }

    public String getPageTag() {
        return null;
    }

    public String getImageNameRegex() {
        return null;
    }

    public String getRawURL(String imageURL) {
        return null;
    }

    public void createBGImage(String source, String pageURL, String imageURL, Map<String, BGImage> map) {
        map.put(imageURL, new BGImage("", imageURL));
    }

    public String getCommentURL(Album album, BGImage image) {
        return null;
    }

    public String getAlbumDesc(String source) {
        return null;
    }
}