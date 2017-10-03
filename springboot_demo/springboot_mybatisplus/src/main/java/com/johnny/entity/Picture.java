package com.johnny.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:11
 */
public class Picture extends Model<Picture> {
    private static final long serialVersionUID = 1L;

    /**
     * 每张图片的地址
     */
    private String id;
    @TableField("pictures_id")
    private String picturesId;
    private String url;
    @TableField("last_update_date")
    private Date lastUpdateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicturesId() {
        return picturesId;
    }

    public void setPicturesId(String picturesId) {
        this.picturesId = picturesId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
