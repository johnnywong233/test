package com.johnny.validator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@SpringBootApplication
@Controller
@Configuration
public class Application {

    private static String UPLOAD_DIRECTORY = "upload-dir";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @RequestMapping({"", "/"})
    public String index() {
        return "index";
    }

    @Bean
    CommandLineRunner init() {
        return (String[] args) -> new File(UPLOAD_DIRECTORY).mkdir();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
