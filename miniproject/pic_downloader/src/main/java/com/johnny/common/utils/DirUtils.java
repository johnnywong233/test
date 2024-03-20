package com.johnny.common.utils;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.model.Album;

import java.io.File;
import java.io.FilenameFilter;

public class DirUtils {
    private static File getDir(Album album) {
        File parentDir = new File(Common.PATH_DOWNLOAD);
        File[] files = parentDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(this.getClass().getName());
            }
        });
        if (files.length != 0) {
            return files[0];
        }
        return null;
    }

    public static void createDir(Album album) {
        File dir = getDir(album);
        if (dir != null) {
            String newName = dir.getParent() + File.separator + dir.getName().replaceAll("\\(\\d+\\)", "").trim();
            Console.print("相册已存在，更新目录：" + dir.getAbsolutePath() + " -> " + newName);
            File newDir = new File(newName);
            boolean flag = dir.renameTo(newDir);
            if (flag) {
                dir = newDir;
            }
            album.setUpdate(true);
        } else {
            String path = Common.PATH_DOWNLOAD + File.separator + album.getName().trim();
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        album.setPath(dir.getAbsolutePath());
    }
}