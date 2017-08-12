package com.johnny.service.handler.handler;

import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;

import java.util.Map;

public class SiteAlbumHandler extends AlbumHandler {
    public static final int PAGE_SIZE_IMAGES_SITE = 30;
    public static final String PAGE_TAG = "start";
    public static final String IMAGE_NAME_REGEX = "p\\d+.(gif|jpg|png)";
    public static final String ALBUM_URL_REGEX = "(http|https)://site.douban.com/.+/widget/photos/\\d+/";

    public String getURLRegex() {
        return ALBUM_URL_REGEX;
    }

    public String getPageRegex() {
        return "/widget/photos/\\d+/\\?\\w+=\\d+";
    }

    public boolean removeURLParameter() {
        return true;
    }

    public Integer getPageSize() {
        return PAGE_SIZE_IMAGES_SITE;
    }

    public String getPageTag() {
        return PAGE_TAG;
    }

    public String getImageNameRegex() {
        return IMAGE_NAME_REGEX;
    }

    public boolean hasRaw() {
        return true;
    }

    public String getRawURL(String imageURL) {
        return imageURL.replace("photo/photo", "photo/raw").trim();
    }

    public void createBGImage(String source, String pageURL, String imageURL, Map<String, BGImage> map) {
        String imageId = imageURL.substring(imageURL.lastIndexOf("/p") + 2, imageURL.lastIndexOf("."));
        String siteAlbumId = pageURL.substring(pageURL.indexOf("photos/") + 7, pageURL.lastIndexOf("/"));

        String startIndexStr = pageURL.substring(0, pageURL.indexOf(siteAlbumId)) + siteAlbumId + "/photo/" + imageId + "/\" title=\"";
        int descStartIndex = source.indexOf(startIndexStr);
        String desc;
        if (descStartIndex != -1) {
            int start = descStartIndex + startIndexStr.length();
            desc = source.substring(start, source.indexOf("\"", start));
        } else {
            desc = "";
        }

        String commentTatolStartIndexStr = pageURL.substring(0, pageURL.indexOf(siteAlbumId)) + siteAlbumId + "/photo/" + imageId + "/#comments\">";
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
        return null;
    }
}