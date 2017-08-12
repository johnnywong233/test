package com.johnny.test;

import com.johnny.service.image.ImageInfo;
import com.johnny.service.image.ImageUtils;

import java.io.File;

public class TestGetImageSize {
    public static void main(String[] args) {
        ImageInfo rawInfo = ImageUtils.getImageSize("D:\\logs" + File.separatorChar + "p2112306858.jpg");
        System.out.println(rawInfo);

        rawInfo = ImageUtils.getImageSize("D:\\logs" + File.separatorChar + "p1553084119.jpg");
        System.out.println(rawInfo);
    }
}