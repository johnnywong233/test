package com.johnny.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
public class Picture extends Model<Picture> {
    /**
     * 每张图片的地址
     */
    private String id;
    @TableField("pictures_id")
    private String picturesId;
    private String url;
    @TableField("last_update_date")
    private Date lastUpdateDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
