package com.johnny.service.handler.finder.impl;

import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;

public class MovieIndexFinder implements IAlbumURLFinder {
    public static void main(String[] args) {
        System.out.println(new MovieAlbumListFinder().findAlbumURL("(http|https)://movie.douban.com/subject/3652297/"));
    }

    @Override
    public List<String> findAlbumURL(String url) {
        List<String> list = new ArrayList<>();
        list.add(url + "photos?type=S");
        list.add(url + "photos?type=R");
        list.add(url + "photos?type=W");
        return list;
    }

    @Override
    public String getURLRegex() {
        return "(http|https)://movie.douban.com/subject/\\d+/";
    }
}