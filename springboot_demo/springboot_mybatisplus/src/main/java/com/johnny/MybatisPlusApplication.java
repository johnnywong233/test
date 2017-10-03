package com.johnny;

import com.johnny.service.BeautifulPicturesService;
import com.johnny.service.PictureService;
import javafx.application.Application;
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
        return application.sources(Application.class);
    }

    //springboot运行后此方法首先被调用
    //实现CommandLineRunner抽象类中的run方法
    public void run(String... args) throws Exception {
        System.out.println("springboot启动完成！");
    }

}
