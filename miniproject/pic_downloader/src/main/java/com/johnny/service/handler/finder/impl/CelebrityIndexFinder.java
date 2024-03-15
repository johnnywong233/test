package com.johnny.service.handler.finder.impl;

import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;

public class CelebrityIndexFinder implements IAlbumURLFinder {
    @Override
    public List<String> findAlbumURL(String url) {
        List<String> list = new ArrayList<>();
        list.add(url + "photos/");
        return list;
    }

    @Override
    public String getURLRegex() {
        return "https://movie.douban.com/celebrity/\\d+/";
    }
}
