package com.johnny.service.creator;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.common.utils.HTMLUtils;
import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.AlbumFactory;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.image.ImageInfo;
import com.johnny.service.image.ImageUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HtmlCreator {
    public static final String DESC_FRONT_COVER = "【相册封面】";
    public static final String DESC_DEFAULT = "-";

    public static boolean createAlbumHTML(String albumPath) throws IOException {
        File albumDir = new File(albumPath);
        if (!albumDir.exists()) {
            return false;
        }

        File[] imageFiles = albumDir.listFiles(f -> f.getName().substring(f.getName().lastIndexOf(".") + 1).matches("(gif|jpg|png)"));
        assert imageFiles != null;
        if (imageFiles.length == 0) {
            return false;
        }

        Album album = null;
        try {
            album = AlbumFactory.getFromLocalFile(albumPath);
        } catch (IOException e) {
            Console.print(e.getMessage());
            Console.print("HTML文档生成失败");
        }

        String name = album.getName();
        AlbumHandler albumHandler = album.getAlbumHandler();
        List<BGImage> photosList = album.getPhotosList();

        String page = Common.HTML_TEMPLATE_PAGE;

        page = page.replace("${title}", HTMLUtils.textToHTML(name) + "(" + photosList.size() + ")");
        page = page.replace("${name}", HTMLUtils.textToHTML(name));
        page = page.replace("${url}", album.getUrl());

        if ((album.getDesc() == null) || ("-".equals(album.getDesc()))) {
            page = page.replace("${albumDesc}", "");
        } else {
            page = page.replace("${albumDesc}", HTMLUtils.textToHTML("■相册描述：" + album.getDesc()));
        }

        page = page.replace("${downloadTime}", "■下载时间：" + Common.SIMPLE_DATE_FORMAT.format(album.getDate()));

        page = page.replace("${imagesTotal}", "■照片数量：" + photosList.size());

        photosList.sort((i1, i2) -> {
            String number1 = i1.getNumber();
            String number2 = i2.getNumber();
            int id1;
            int id2;
            if (number1.startsWith("O-")) {
                if (number2.startsWith("O-")) {
                    id1 = Integer.parseInt(number1.substring(number1.indexOf("O-") + 2));
                    id2 = Integer.parseInt(number2.substring(number2.indexOf("O-") + 2));
                } else {
                    return 1;
                }
            } else {
                if (number2.startsWith("O-")) {
                    return -1;
                }

                id1 = Integer.parseInt(number1);
                id2 = Integer.parseInt(number2);
            }

            return id1 - id2;
        });
        Set<String> rawSet = new HashSet<>();
        if (albumHandler.hasRaw()) {
            File dir = new File(album.getPath() + File.separatorChar + "raw");
            if (dir.exists()) {
                for (File file : dir.listFiles()) {
                    rawSet.add(file.getName());
                }
            }

        }

        StringBuilder images = new StringBuilder("[");
        for (BGImage image : photosList) {
            String desc = image.getDesc();
            if (desc.trim().length() == 0) {
                desc = "-";
            } else if (desc.startsWith("※")) {
                desc = "【相册封面】" + desc.replaceAll("※+", "");
            }
            images.append("{");
            images.append("'number':'").append(image.getNumber()).append("',");
            images.append("'name':'").append(HTMLUtils.textToJson(image.getName())).append("',");
            images.append("'desc':'").append(HTMLUtils.textToJson(desc)).append("',");
            if (image.getOwnerName() != null) {
                images.append("'ownerName':'").append(HTMLUtils.textToJson(image.getOwnerName())).append("',");
                images.append("'ownerURL':'").append(image.getOwnerURL()).append("',");
            }

            String commentURL = albumHandler.getCommentURL(album, image);
            if (commentURL != null) {
                images.append("'commentURL':'").append(commentURL).append("',");
            }

            Integer commentTotal = image.getCommentTotal();
            if (commentTotal != null) {
                images.append("'commentTotal':'").append(commentTotal).append("',");
            }

            if ((albumHandler.hasRaw()) &&
                    (rawSet.contains(image.getName()))) {
                try {
                    ImageInfo imageInfo = ImageUtils.getImageSize(album.getPath() + File.separatorChar + image.getName());
                    ImageInfo rawInfo = ImageUtils.getImageSize(album.getPath() + File.separatorChar + "raw" + File.separatorChar + image.getName());
                    if ((imageInfo.getWidth() == rawInfo.getWidth()) && (imageInfo.getHeight() == rawInfo.getHeight())) {
                        images.append("'raw':'").append(Common.RAW_TYPE_UNCOMPRESSED).append("'");
                    } else {
                        images.append("'raw':'").append(Common.RAW_TYPE_LARGE).append("'");
                    }
                } catch (Exception e) {
                    images.append("'raw':'").append(Common.RAW_TYPE_LARGE).append("'");
                }

            }

            if (",".equals(images.substring(images.length() - 1, images.length()))) {
                images.delete(images.length() - 1, images.length());
            }

            images.append("},");
        }
        images.delete(images.length() - 1, images.length());
        images.append("]");
        page = page.replace("${images}", images.toString());

        File file = new File(album.getPath() + File.separator + "index.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(page);
        bw.flush();
        bw.close();

        List<String> resourceList = new ArrayList<>();
        resourceList.add("/com.johnny/resources/html/bg.jpg");
        resourceList.add("/com.johnny/resources/html/numberBg.png");
        resourceList.add("/com.johnny/resources/html/half-l.png");
        resourceList.add("/com.johnny/resources/html/half-d.png");
        File dir = new File(album.getPath() + File.separator + "resource");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (String resource : resourceList) {
            InputStream inputStream = Common.class.getResourceAsStream(resource);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            String path = dir.getAbsolutePath() + File.separator + resource.substring(resource.lastIndexOf("/") + 1);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            bos.flush();
            bos.close();
            bis.close();
        }
        return true;
    }

    public static void createAlbumHTML(List<String> paths) {
        for (String path : paths) {
            try {
                createAlbumHTML(path);
            } catch (IOException e) {
                Console.print(e.getMessage());
                Console.print("HTML文档生成失败");
                e.printStackTrace();
            }
        }
    }
}
