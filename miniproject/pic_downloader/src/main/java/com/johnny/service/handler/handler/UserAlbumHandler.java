package com.johnny.service.handler.handler;

import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;

import java.util.Map;

public class UserAlbumHandler extends AlbumHandler {
    public static final int PAGE_SIZE_IMAGES = 18;
    public static final String PAGE_TAG = "start";
    public static final String IMAGE_NAME_REGEX = "p\\d+.(gif|jpg|png)";
    public static final String ALBUM_URL_REGEX = "(http|https)://www.douban.com/photos/album/\\d+/";

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
        return PAGE_SIZE_IMAGES;
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
    public boolean hasRaw() {
        return true;
    }

    @Override
    public String getRawURL(String imageURL) {
        return imageURL.replace("photo/photo", "photo/large").trim();
    }

    @Override
    public void createBGImage(String source, String pageURL, String imageURL, Map<String, BGImage> map) {
        String imageId = imageURL.substring(imageURL.lastIndexOf("/p") + 2, imageURL.lastIndexOf("."));

        String descStartIndexStr = "<a href=\"https://www.douban.com/photos/photo/" + imageId + "/\" class=\"photolst_photo\" title=\"";
        int descStartIndex = source.indexOf(descStartIndexStr);
        String desc;
        if (descStartIndex != -1) {
            desc = source.substring(descStartIndex + descStartIndexStr.length(), source.indexOf("\">", descStartIndex));
        } else {
            desc = "";
        }

        String commentTotalStartIndexStr = "<a href=\"https://www.douban.com/photos/photo/" + imageId + "/#comments\">";
        int commentTotalStartIndex = source.indexOf(commentTotalStartIndexStr);
        Integer commentTotal = null;
        if (commentTotalStartIndex != -1) {
            String s = source.substring(commentTotalStartIndex + commentTotalStartIndexStr.length(), source.indexOf("</a>", commentTotalStartIndex));
            commentTotal = Integer.valueOf(s.replace("回应", ""));
        }

        imageURL = imageURL.replace("thumb", "photo").trim();
        desc = desc.replace("\\t\\n", "").trim();
        if (!map.containsKey(imageURL)) {
            map.put(imageURL, new BGImage(desc, imageURL, commentTotal));
        } else {
            BGImage bgImage = map.get(imageURL);
            if ((bgImage.getCommentTotal() != null) && (commentTotal == null)) {
                commentTotal = bgImage.getCommentTotal();
            }
            map.put(imageURL, new BGImage("※" + bgImage.getDesc(), imageURL, commentTotal));
        }
    }

    @Override
    public boolean checkBGImage(BGImage bgImage) {
        return !bgImage.getUrl().contains("albumicon");
    }

    @Override
    public String getCommentURL(Album album, BGImage image) {
        return "http://www.douban.com/photos/photo/" + image.getId();
    }

    @Override
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
}
