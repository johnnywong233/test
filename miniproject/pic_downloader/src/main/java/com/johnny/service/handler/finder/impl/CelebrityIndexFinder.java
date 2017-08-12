package com.johnny.service.handler.finder.impl;

import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;

public class CelebrityIndexFinder implements IAlbumURLFinder {
    public List<String> findAlbumURL(String url) {
        List<String> list = new ArrayList<>();
        list.add(url + "photos/");
        return list;
    }

    public String getURLRegex() {
        return "(http|https)://movie.douban.com/celebrity/\\d+/";
    }
}
