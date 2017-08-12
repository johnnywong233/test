package com.johnny.model;

import com.johnny.service.download.DownloadProcessing;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.handler.PageAnalyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
    private String name;
    private String url;
    private Date date;
    private String path;
    private String charset;
    private String desc;
    private boolean update = false;
    private AlbumHandler albumHandler;
    private List<BGImage> photosList = new ArrayList<>();

    private List<String> pageURLLsit = new ArrayList<>();

    public AlbumHandler getAlbumHandler() {
        return this.albumHandler;
    }

    public void setAlbumHandler(AlbumHandler albumHandler) {
        this.albumHandler = albumHandler;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BGImage> getPhotosList() {
        return this.photosList;
    }

    public void setPhotosList(List<BGImage> photosList) {
        this.photosList = photosList;
    }

    public List<String> getPageURLLsit() {
        return this.pageURLLsit;
    }

    public void setPageURLLsit(List<String> pageURLLsit) {
        this.pageURLLsit = pageURLLsit;
    }

    public void download() {
        DownloadProcessing.downloadAlbum(this);
    }

    public void createDescDoc() {
        this.albumHandler.createDescDoc(this);
    }

    public void init() {
        setUrl(this.albumHandler.getAlbumURL());

        List pageURLLsit = PageAnalyzer.findPageURL(this.albumHandler);
        setPageURLLsit(pageURLLsit);

        String name = PageAnalyzer.findAlbumName().trim();
        setName(this.albumHandler.albumNameProcess(name));

        String desc = PageAnalyzer.findAlbumDesc(this.albumHandler);
        if (desc != null) {
            setDesc(desc.trim());
        }

        setDate(new Date());
    }

    public String toString() {
        return "Album [name=" + this.name + ", url=" + this.url + ", date=" + this.date +
                ", path=" + this.path + ", charset=" + this.charset +
                ", update=" + this.update + ", photosList=" + this.photosList +
                ", pageURLLsit=" + this.pageURLLsit + "]";
    }
}