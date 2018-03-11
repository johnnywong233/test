package com.johnny.model;

import com.johnny.service.download.DownloadProcessing;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.handler.PageAnalyzer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void download() {
        DownloadProcessing.downloadAlbum(this);
    }

    public void createDescDoc() {
        this.albumHandler.createDescDoc(this);
    }

    public void init() {
        setUrl(this.albumHandler.getAlbumURL());

        List<String> pageURLLsit = PageAnalyzer.findPageURL(this.albumHandler);
        setPageURLLsit(pageURLLsit);

        String name = PageAnalyzer.findAlbumName().trim();
        setName(this.albumHandler.albumNameProcess(name));

        String desc = PageAnalyzer.findAlbumDesc(this.albumHandler);
        if (desc != null) {
            setDesc(desc.trim());
        }

        setDate(new Date());
    }

}