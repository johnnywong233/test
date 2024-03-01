package com.johnny.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("beautiful_pictures")
public class BeautifulPictures extends Model<BeautifulPictures> {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String url;
    @TableField("picture_urls_num")
    private Integer pictureUrlsNum;
    private Integer like;
    private String tag;
    private String keywords;
    @TableField("last_update_date")
    private Date lastUpdateDate;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
