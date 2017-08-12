package com.johnny.model;

public class BGImage {
    private String ownerName;
    private String ownerURL;
    private String desc;
    private String commentURL;
    private Integer commentTotal;
    private String url;
    private String name;
    private String number;
    private String id;

    public BGImage() {
    }

    public BGImage(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    public BGImage(String number, String url, String desc) {
        this.desc = desc;
        this.url = url;
        this.number = number;
    }

    public BGImage(String desc, String url, Integer commentTotal) {
        this.desc = desc;
        this.commentTotal = commentTotal;
        this.url = url;
    }

    public String getId() {
        String name = getName();
        if (name.matches("p\\d+.(gif|jpg|png)")) {
            return name.substring(name.indexOf("p") + 1, name.lastIndexOf("."));
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerURL() {
        return this.ownerURL;
    }

    public void setOwnerURL(String ownerURL) {
        this.ownerURL = ownerURL;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCommentURLURL() {
        return this.commentURL;
    }

    public void setCommentURLURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return this.url.substring(this.url.lastIndexOf("/") + 1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCommentURL() {
        return this.commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public Integer getCommentTotal() {
        return this.commentTotal;
    }

    public void setCommentTotal(Integer commentTotal) {
        this.commentTotal = commentTotal;
    }

    public String toString() {
        return "BGImage [ownerName=" + this.ownerName + ", ownerURL=" + this.ownerURL +
                ", desc=" + this.desc + ", commentURL=" + this.commentURL +
                ", commentTotal=" + this.commentTotal + ", url=" + this.url + ", name=" +
                this.name + ", number=" + this.number + ", id=" + this.id + "]";
    }
}