package com.johnny.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.johnny.dao.BeautifulPicturesMapper;
import com.johnny.entity.BeautifulPictures;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:09
 * 这个类在继承ServiceImpl之后，会有相应的增删改查以及分页的相关方法敏捷开发
 */
@Service
public class BeautifulPicturesService extends ServiceImpl<BeautifulPicturesMapper, BeautifulPictures> {
}
