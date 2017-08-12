package com.johnny.service;

import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.handler.AlbumHandlerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AlbumFactory {
    @SuppressWarnings("unchecked")
    static List<Album> getFromURL(String url) {
        List albums = new ArrayList();

        List<AlbumHandler> albumHandlers = AlbumHandlerFactory.getHandler(url);
        for (AlbumHandler albumHandler : albumHandlers) {
            Album album = new Album();
            album.setAlbumHandler(albumHandler);
            album.setUrl(albumHandler.getAlbumURL());
            albums.add(album);
        }
        return albums;
    }

    @SuppressWarnings("unchecked")
    public static Album getFromLocalFile(String path)
            throws IOException {
        Album album = new Album();
        File descFile = new File(path + File.separator + "描述.txt");
        String url = "";
        String charset = "GBK";
        String albumDesc = null;
        Date downloadTime = null;
        List photosList = new ArrayList();
        List<String> imageNameList = new ArrayList<>();

        if (descFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(descFile));
            String str;
            if ((str = reader.readLine()) != null) {
                String[] strArray = str.trim().split(" ", 4);
                if (strArray.length == 1) {
                    url = strArray[0];
                }
                if (strArray.length == 2) {
                    charset = strArray[0];
                    url = strArray[1];
                }
                if (strArray.length == 3) {
                    charset = strArray[0];
                    url = strArray[1];
                    downloadTime = new Date(Long.valueOf(strArray[2]));
                }
                if (strArray.length == 4) {
                    charset = strArray[0];
                    url = strArray[1];
                    downloadTime = new Date(Long.valueOf(strArray[2]));
                    albumDesc = strArray[3];
                }
                List handlerList = AlbumHandlerFactory.getHandler(url, false);
                if ((handlerList != null) && (handlerList.size() != 0)) {
                    album.setAlbumHandler((AlbumHandler) handlerList.get(0));
                }
            }

            List<BGImage> bgImages = album.getAlbumHandler().getBGImageFromDescDoc(descFile);
            photosList.addAll(bgImages);
            for (BGImage bgImage : bgImages) {
                imageNameList.add(bgImage.getName());
            }
            reader.close();
        }

        File dir = new File(path);
        File[] images = dir.listFiles(file -> {
            if (!AlbumFactory.contains(file.getName())) {
                if (file.isFile()) {
                    String name = file.getName();
                    return name.substring(name.lastIndexOf(".") + 1).matches("(gif|jpg|png)");
                }
                return false;
            }

            return false;
        });
        List list = new ArrayList(Arrays.asList(images));
        list.sort((Comparator<File>) (o1, o2) -> (int) (o1.lastModified() - o2.lastModified()));
        for (int i = 0; i < list.size(); i++) {
            File imageFile = (File) list.get(i);
            String imagePath = imageFile.getAbsolutePath().replaceAll("\\\\", "/");
            BGImage bgImage = new BGImage("O-" + (i + 1), imagePath, imageFile.getName());
            photosList.add(bgImage);
        }
        path = path.replaceAll("\\\\", "/");
        album.setName(path.substring(path.lastIndexOf("/") + 1));
        album.setUrl(url);
        album.setCharset(charset);
        album.setPath(path);
        album.setPhotosList(photosList);
        album.setDesc(albumDesc);
        album.setDate(downloadTime);
        return album;
    }
}
