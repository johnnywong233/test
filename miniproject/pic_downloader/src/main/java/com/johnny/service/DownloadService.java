package com.johnny.service;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.model.Album;
import com.johnny.service.creator.HtmlCreator;
import com.johnny.service.download.DownloadManager;
import com.johnny.ui.FailFileFrame;
import com.johnny.ui.MainFrame;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.util.ArrayList;
import java.util.List;

public class DownloadService {
    private static final MainFrame mainFrame = MainFrame.getInstance();
    private static final JProgressBar albumListProgressBar = mainFrame.albumListProgressBar;
    private static final JLabel albumListCountLabel = mainFrame.albumListCountLabel;

    public static void download(List<String> urlList) {
        List<Album> albums = new ArrayList<>();
        for (String url : urlList) {
            albums.addAll(AlbumFactory.getFromURL(url));
        }

        long time = System.currentTimeMillis();
        long imagesCount = 0L;

        albumListProgressBar.setMaximum(albums.size());
        albumListProgressBar.setValue(0);
        albumListCountLabel.setText("0/" + albums.size() + " ");

        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            album.init();

            album.download();

            albumListProgressBar.setValue(i + 1);
            albumListCountLabel.setText(i + 1 + "/" + albums.size() + " ");
            imagesCount += album.getPhotosList().size();
        }

        if (imagesCount != 0L) {
            String sp = "                  ";
            String sb = "==============================\r\n" +
                    sp + " 相册总数：" + albums.size() + "(个)\r\n" +
                    sp + " 照片总数:" + imagesCount + "(张)\r\n" +
                    sp + " 成功:" + (imagesCount - Common.failFileMap.size()) + "(张)\r\n" +
                    sp + " 失败:" + Common.failFileMap.size() + "(张)\r\n" +
                    sp + " 总耗时:" + (System.currentTimeMillis() - time) / 1000L + "s" + sp + "~\\(≧▽≦)/~";
            Console.print(sb);
        }

        if (Common.failFileMap.size() != 0) {
            Console.print("【存在下载失败文件,尝试重新下载】");
            int num = 1;
            int flag = 0;
            while ((num < Common.AUTO_DOWNLOAD_FAIL_FILE) && (flag == 0)) {
                flag = DownloadManager.downloadFailFile();
                if (flag == 0) {
                    Console.print("【部分文件依然下载失败，显示失败文件列表】 - " + num);
                } else {
                    Console.print("【失败文件下载完成】");
                }
                num++;
            }
        }

        List<String> finishedAlbumPathList = new ArrayList<>();
        for (Album a : albums) {
            finishedAlbumPathList.add(a.getPath());
        }
        if (Common.failFileMap.size() > 0) {
            FailFileFrame frame = FailFileFrame.getInstance(finishedAlbumPathList);
            frame.setVisible(true);
        } else {
            Console.print("【正在生成HTML文档,请稍等】");
            HtmlCreator.createAlbumHTML(finishedAlbumPathList);
            Console.print("【FINISH】");

            MainFrame.getInstance().downloadBtn.setEnabled(true);
        }
    }
}
