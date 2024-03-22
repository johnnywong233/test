package com.johnny.web;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.johnny.entity.BeautifulPictures;
import com.johnny.service.BeautifulPicturesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:09
 */
@Controller
public class TestController {
    @Resource
    private BeautifulPicturesService beautifulPicturesService;

    @RequestMapping("/test1")
    public String view(Model model, Page<BeautifulPictures> page) {
        Page<BeautifulPictures> pageList = beautifulPicturesService.selectPage(page);
        model.addAttribute("user", JSON.toJSONString(pageList.getRecords()));
        return "index";
    }
}
