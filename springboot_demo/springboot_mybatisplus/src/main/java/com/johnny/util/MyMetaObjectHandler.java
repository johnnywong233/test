package com.johnny.util;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 12:49
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    //新增填充
    @Override
    public void insertFill(MetaObject metaObject) {
        Object lastUpdateNameId = metaObject.getValue("lastUpdateNameId");
        Object lastUpdateTime = metaObject.getValue("lastUpdateTime");
        //获取当前登录用户
        //SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        if (null == lastUpdateNameId) {
            metaObject.setValue("lastUpdateNameId", "123");
        }
        if (null == lastUpdateTime) {
            metaObject.setValue("lastUpdateTime", new Date());
        }
    }

    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
        insertFill(metaObject);
    }
}
