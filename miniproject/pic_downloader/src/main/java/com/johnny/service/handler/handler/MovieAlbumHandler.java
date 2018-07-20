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

    private Pattern p = Pattern.compile("type=\\w+");

    @Override
    public String getURLRegex() {
        return ALBUM_URL_REGEX;
    }

    @Override
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

    @Override
    public String getAlbumURL() {
        if (this.albumURL.endsWith("/")) {
            return this.albumURL.substring(0, this.albumURL.length() - 1);
        }
        return this.albumURL;
    }

    @Override
    public void setAlbumURL(String albumURL) {
        super.setAlbumURL(albumURL);
        Matcher m = p.matcher(albumURL);
        if (m.find()) {
            this.albumURL = (this.albumURL + "?" + m.group());
        }
    }

    @Override
    public boolean removeURLParameter() {
        return true;
    }

    @Override
    public Integer getPageSize() {
        return PAGE_SIZE_IMAGES_MOVIE;
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
        return imageURL.replace("photo/photo", "photo/raw").trim();
    }

    @Override
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

    @Override
    public boolean checkBGImage(BGImage bgImage) {
        return !bgImage.getUrl().contains("albumicon");
    }

    @Override
    public String getCommentURL(Album album, BGImage image) {
        return "http://movie.douban.com/photos/photo/" + image.getId();
    }

    @Override
    public String getAlbumDesc(String source) {
        return null;
    }
}