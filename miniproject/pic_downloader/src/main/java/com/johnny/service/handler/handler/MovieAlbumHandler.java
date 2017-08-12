package com.johnny.service.handler.handler;

import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieAlbumHandler extends AlbumHandler {
    private static final int PAGE_SIZE_IMAGES_MOVIE = 40;
    private static final String PAGE_TAG = "start";
    private static final String IMAGE_NAME_REGEX = "p\\d+.(gif|jpg|png)";
    private static final String ALBUM_URL_REGEX = "(http|https)://movie.douban.com/subject/\\d+/photos\\?(\\w+=\\w+&*)+";

    public String getURLRegex() {
        return ALBUM_URL_REGEX;
    }

    public String getPageRegex() {
        String url = getAlbumURL();
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        System.out.println("==============");
        System.out.println(getAlbumURL() + "\\?(\\w+=\\w+&*(amp;)*)+");
        System.out.println(url + "\\?(\\w+=\\w+&*(amp;)*)+");
        System.out.println("==============");
        return url + "\\?(\\w+=\\w+&*(amp;)*)+";
    }

    public String getAlbumURL() {
        if (this.albumURL.endsWith("/")) {
            return this.albumURL.substring(0, this.albumURL.length() - 1);
        }
        return this.albumURL;
    }

    public void setAlbumURL(String albumURL) {
        super.setAlbumURL(albumURL);
        Pattern p = Pattern.compile("type=\\w+");
        Matcher m = p.matcher(albumURL);
        if (m.find())
            this.albumURL = (this.albumURL + "?" + m.group());
    }

    public boolean removeURLParameter() {
        return true;
    }

    public Integer getPageSize() {
        return PAGE_SIZE_IMAGES_MOVIE;
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

        String startIndexStr = "<a href=\"https://movie.douban.com/photos/photo/" + imageId + "/\">";
        int descStartIndex = source.indexOf(startIndexStr);
        String desc;
        if (descStartIndex != -1) {
            String str = "<div class=\"name\">";
            int start = source.indexOf(str, descStartIndex + startIndexStr.length());
            desc = source.substring(start + str.length(), source.indexOf("<", start + str.length()));
        } else {
            desc = "";
        }

        String commentTotalStartIndexStr = "<a href=\"https://movie.douban.com/photos/photo/" + imageId + "/#comments\">";
        int commentTotalStartIndex = source.indexOf(commentTotalStartIndexStr);
        Integer commentTotal = null;
        if (commentTotalStartIndex != -1) {
            String s = source.substring(commentTotalStartIndex + commentTotalStartIndexStr.length(), source.indexOf("</a>", commentTotalStartIndex));
            commentTotal = Integer.valueOf(s.replace("回应", ""));
        }

        imageURL = imageURL.replace("thumb", "photo").trim();
        desc = desc.replace("\\t\\n", "").trim();
        if (!map.containsKey(imageURL)) {
            BGImage bgImage = new BGImage(desc, imageURL, commentTotal);
            map.put(imageURL, bgImage);
        }
    }

    public boolean checkBGImage(BGImage bgImage) {
        return !bgImage.getUrl().contains("albumicon");
    }

    public String getCommentURL(Album album, BGImage image) {
        return "http://movie.douban.com/photos/photo/" + image.getId();
    }

    public String getAlbumDesc(String source) {
        return null;
    }
}