package com.johnny.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}