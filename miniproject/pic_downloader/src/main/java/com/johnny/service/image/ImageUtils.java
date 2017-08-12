package com.johnny.service.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtils {
    public static ImageInfo getImageSize(String path) {
        try {
            File file = new File(path);
            String readImageFormat = path.substring(path.lastIndexOf(".") + 1, path.length());
            Iterator readers = ImageIO.getImageReadersByFormatName(readImageFormat);
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            return new ImageInfo(reader.getWidth(0), reader.getHeight(0));
        } catch (IOException e) {
        }
        return getImageSizeByBufferedImage(path);
    }

    private static ImageInfo getImageSizeByBufferedImage(String path) {
        File picture = new File(path);
        try {
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
            return new ImageInfo(sourceImg.getWidth(), sourceImg.getHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("图片文件尺寸获取异常：" + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String path = "D:\\logs";
        File dir = new File(path);

        long begin = System.currentTimeMillis();
        for (File picture : dir.listFiles()) {
            getImageSize(picture.getPath());
        }
        System.out.println("【getImageSize】" + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        for (File picture : dir.listFiles()) {
            getImageSizeByBufferedImage(picture.getPath());
        }
        System.out.println("【getImageSizeByBufferedImage】" + (System.currentTimeMillis() - begin));
    }
}