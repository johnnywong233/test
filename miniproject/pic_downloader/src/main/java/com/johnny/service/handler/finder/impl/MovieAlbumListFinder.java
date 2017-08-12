package com.johnny.service.handler.finder.impl;

import com.johnny.service.handler.finder.IAlbumURLFinder;

import java.util.ArrayList;
import java.util.List;

public class MovieAlbumListFinder
        implements IAlbumURLFinder {
    public static void main(String[] args) {
        MovieAlbumListFinder finder = new MovieAlbumListFinder();
        String url = "(http|https)://movie.douban.com/subject/3652297/all_photos/";
        System.out.println(url.matches(finder.getURLRegex()));
        System.out.println(finder.findAlbumURL(url));
    }

    public List<String> findAlbumURL(String url) {
        url = url.substring(0, url.lastIndexOf("all_photos"));
        List<String> list = new ArrayList<>();
        list.add(url + "photos?type=S");
        list.add(url + "photos?type=R");
        list.add(url + "photos?type=W");
        return list;
    }

    public String getURLRegex() {
        return "(http|https)://movie.douban.com/subject/\\d+/all_photos(/)*";
    }
}
