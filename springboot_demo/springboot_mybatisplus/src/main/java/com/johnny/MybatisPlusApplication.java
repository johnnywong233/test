package com.johnny;

import com.johnny.service.BeautifulPicturesService;
import com.johnny.service.PictureService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:00
 */
@MapperScan("com.johnny.dao")
@EnableCaching(proxyTargetClass = true) // 开启缓存功能
@SpringBootApplication
public class MybatisPlusApplication extends SpringBootServletInitializer implements CommandLineRunner {

    //http://z77z.oschina.io/2017/01/23/SpringBoot+SpringMVC+MybatisPlus%E6%A1%86%E6%9E%B6%E6%95%B4%E5%90%88%E7%BB%83%E4%B9%A0%E4%B9%8B%E3%80%90%E7%BE%8E%E5%A5%B3%E5%9B%BE%E7%89%87%E3%80%91%E7%88%AC%E8%99%AB---%E5%9B%BE%E6%96%87%E8%AF%A6%E7%BB%86%E6%B5%81%E7%A8%8B/

    @Autowired
    BeautifulPicturesService beautifulPicturesService;

    @Autowired
    PictureService pictureService;

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

    //Java EE应用服务器配置，
    //如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MybatisPlusApplication.class);
    }

    //springboot运行后此方法首先被调用
    //实现CommandLineRunner抽象类中的run方法
    @Override
    public void run(String... args) throws Exception {
        System.out.println("springboot启动完成！");
    }

}
