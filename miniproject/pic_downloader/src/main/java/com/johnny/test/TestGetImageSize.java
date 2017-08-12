package com.johnny.test;

import com.johnny.service.image.ImageInfo;
import com.johnny.service.image.ImageUtils;

import java.io.File;

public class TestGetImageSize {
    public static void main(String[] args) {
        ImageInfo rawInfo = ImageUtils.getImageSize("F:\\pic\\wander" + File.separatorChar + "12a01.jpg");
        System.out.println(rawInfo);
    }
}