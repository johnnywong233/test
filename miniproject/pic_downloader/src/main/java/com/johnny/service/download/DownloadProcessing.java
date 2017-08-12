package com.johnny.service.download;

import com.johnny.common.Console;
import com.johnny.common.utils.DirUtils;
import com.johnny.model.Album;
import com.johnny.model.BGImage;
import com.johnny.service.handler.AlbumHandler;
import com.johnny.service.handler.PageAnalyzer;
import com.johnny.ui.MainFrame;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DownloadProcessing {
    private static MainFrame mainFrame = MainFrame.getInstance();
    private static JProgressBar processUnitProgressBar;
    private static JLabel processUnitCountLabel;

    static {
        processUnitProgressBar = mainFrame.processUnitProgressBar;
        processUnitCountLabel = mainFrame.processUnitCountLabel;
    }

    public DownloadProcessing() {
    }

    public static void downloadAlbum(Album album) {
        long albumDownloadTime = System.currentTimeMillis();
        int updateCount = 0;
        Map<String, BGImage> imageMap = new HashMap<>();
        DirUtils.createDir(album);
        int processUnitMax = (new Double(Math.ceil((double) album.getPageURLLsit().size() / 50.0D))).intValue();
        int processUnitNumber = 0;
        processUnitProgressBar.setMaximum(processUnitMax);
        processUnitProgressBar.setValue(0);
        processUnitCountLabel.setText("0/" + processUnitMax + " ");

        for (int j = 0; j < processUnitMax; ++j) {
            long processUnitTime = System.currentTimeMillis();
            List<String> pageURLList = new ArrayList<>();
            int start = processUnitNumber * 50;
            int end = start + 50;
            if (end > album.getPageURLLsit().size()) {
                end = album.getPageURLLsit().size();
            }

            for (int k = start; k < end; ++k) {
                pageURLList.add(album.getPageURLLsit().get(k));
            }

            updateCount += processUnit(album, imageMap, pageURLList);
            ++processUnitNumber;
            processUnitProgressBar.setValue(j + 1);
            processUnitCountLabel.setText(j + 1 + "/" + processUnitMax + " ");
            if (processUnitMax > 1 && j + 1 != processUnitMax) {
                long t = System.currentTimeMillis() - processUnitTime;
                Console.print("处理单元耗时：" + t);
                if (t < 60000L) {
                    Console.print("短时间访问页面次数过多，启动休眠~");
                    Console.print("(￣ε(#￣)☆╰╮o(￣皿￣///)");

                    for (long c = 60L; c > 0L; --c) {
                        try {
                            Thread.sleep(1000L);
                            Console.print("休眠倒计时：" + c + "\t (＃°Д°)\"");
                        } catch (InterruptedException var18) {
                            var18.printStackTrace();
                        }
                    }

                    Console.print("[]~(￣▽￣)~* ");
                }
            }
        }

        album.setPhotosList(new ArrayList<>(imageMap.values()));
        if (album.getPhotosList().size() != 0) {
            album.createDescDoc();
            Console.print("相册下载完成 - " + album.getName());
            Console.print(" 数量：" + album.getPhotosList().size());
            if (album.isUpdate()) {
                Console.print(" 新增:" + updateCount + "(张)");
            }

            Console.print(" 单相册耗时:" + (System.currentTimeMillis() - albumDownloadTime) / 1000L + "s");
        } else {
            Console.print("提示：失败或页面无图像。");
        }

    }

    private static int processUnit(Album album, Map<String, BGImage> imageMap, List<String> pageURLList) {
        Integer update;
        Console.print("处理单元：启动信息获取");
        Set<String> imageURLSet = infoProcess(album, imageMap, pageURLList);
        Console.print("处理单元：开始下载：" + album.getName() + "(" + imageURLSet.size() + "张)");
        update = DownloadManager.downloadImage(new ArrayList<>(imageURLSet), album.getPath());
        AlbumHandler albumHandler = album.getAlbumHandler();
        if (albumHandler.hasRaw()) {
            Console.print("处理单元：检测并下载大图");
            String path = album.getPath() + File.separatorChar + "raw";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }

            List<String> list = new ArrayList<>();

            for (String url : imageURLSet) {
                list.add(albumHandler.getRawURL(url));
            }

            update += DownloadManager.downloadImage(list, path);
        }

        return update;
    }

    private static Set<String> infoProcess(Album album, Map<String, BGImage> imageMap, List<String> pageURLList) {
        Set<String> imageURLSet = new HashSet<>();

        for (int i = 0; i < pageURLList.size(); ++i) {
            Console.print("分析页面(" + (i + 1) + "/" + pageURLList.size() + ")：" + pageURLList.get(i));
            Map<String, BGImage> map = new HashMap<>();

            try {
                map = PageAnalyzer.findImageURLAndDesc(album, pageURLList.get(i));
            } catch (Exception var11) {
                try {
                    map = PageAnalyzer.findImageURLAndDesc(album, pageURLList.get(i));
                } catch (Exception var10) {
                    Console.print("页面分析错误，下载失败：" + pageURLList.get(i));
                }
                var11.printStackTrace();
            }

            for (Entry<String, BGImage> entry : map.entrySet()) {
                if (!imageMap.containsKey(entry.getKey())) {
                    imageMap.put(entry.getKey(), entry.getValue());
                    imageURLSet.add(entry.getKey());
                } else {
                    BGImage bgImage;
                    if (imageMap.get(entry.getKey()).getDesc().equals("")) {
                        if (entry.getValue().getDesc().equals("")) {
                            bgImage = imageMap.get(entry.getKey());
                            bgImage.setDesc("※" + entry.getValue().getDesc());
                            imageMap.put(entry.getKey(), bgImage);
                        } else {
                            imageMap.put(entry.getKey(), entry.getValue());
                        }
                    } else {
                        bgImage = imageMap.get(entry.getKey());
                        String desc = ("※" + bgImage.getDesc()).replaceAll("※+", "※");
                        if (desc.equals("※")) {
                            bgImage = entry.getValue();
                            bgImage.setDesc("※" + bgImage.getDesc());
                        } else {
                            bgImage.setDesc(desc);
                        }

                        imageMap.put(entry.getKey(), bgImage);
                    }
                }
            }
        }

        return imageURLSet;
    }
}
