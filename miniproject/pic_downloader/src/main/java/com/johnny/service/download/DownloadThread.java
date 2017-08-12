package com.johnny.service.download;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.common.utils.URLUtils;

import javax.swing.JProgressBar;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class DownloadThread extends Thread {
    private List<String> imageURLList;
    private String path;
    private String url;
    private int imageCount;
    private JProgressBar mainProgressBar;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;

    DownloadThread() {
    }

    DownloadThread(String name, List<String> imageURLList, int imageCount, String path, JProgressBar mainProgressBar) {
        this.imageURLList = imageURLList;
        this.path = path.trim();
        this.imageCount = imageCount;
        this.mainProgressBar = mainProgressBar;
        setName(name);
    }

    void closeStream() throws IOException {
        if (this.inputStream != null) {
            this.inputStream.close();
            this.inputStream = null;
        }
        if (this.outputStream != null) {
            this.outputStream.close();
            this.outputStream = null;
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getUrl() {
        return this.url;
    }

    public BufferedInputStream getInputStream() {
        return this.inputStream;
    }

    public BufferedOutputStream getOutputStream() {
        return this.outputStream;
    }

    public void run() {
        while (true) {
            List<String> list = this.imageURLList;
            int listSize;
            synchronized (list) {
                if (this.imageURLList.size() == 0) {
                    return;
                }

                this.url = list.get(0);
                listSize = list.size() - 1;
                this.imageURLList.remove(this.url);
            }

            try {
                int c = this.downloadImage(this.url, this.path, false);
                if (c == 0) {
                    Console.print(this.getName() + " - 图片已存在(" + (this.imageCount - listSize) + "/" + this.imageCount + ")：" + this.url);
                }

                if (c == 1) {
                    Console.print(this.getName() + " - 图片下载完成(" + (this.imageCount - listSize) + "/" + this.imageCount + ")：" + this.url);
                }

                if (c == 2) {
                    Console.print(this.getName() + " - 图片不存在(" + (this.imageCount - listSize) + "/" + this.imageCount + ")：" + this.url);
                }
            } catch (Exception e) {
                if (!e.getClass().equals(FileNotFoundException.class)) {
                    Console.print("图片下载失败：" + this.url + " - " + e.getMessage());
                    Map<String, String> failFileMap = Common.failFileMap;
                    synchronized (failFileMap) {
                        if (!failFileMap.containsKey(this.url)) {
                        	failFileMap.put(this.url, this.path);
                        }
                    }

                    e.printStackTrace();
                } else {
                    Console.print("图片不存在：" + this.url + " - " + e.getMessage());
                    e.printStackTrace();
                }
            } finally {
                JProgressBar jProgressBar = this.mainProgressBar;
                synchronized (jProgressBar) {
                    this.mainProgressBar.setValue(this.mainProgressBar.getValue() + 1);
                }
            }
        }
    }

    int downloadImage(String url, String filePath, boolean isDeleteOldFile) throws IOException {
        if (!URLUtils.exists(url)) {
            return 2;
        } else {
            String fileName = url.substring(url.lastIndexOf(47));
            File file = new File(filePath + File.separatorChar + fileName);
            if (isDeleteOldFile && file.exists()) {
                file.delete();
            }

            URL image = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) image.openConnection();
            conn.setRequestProperty("referer", "https://www.douban.com/");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            InputStream in = conn.getInputStream();
            if (file.exists()) {
                return 0;
            } else {
                this.inputStream = new BufferedInputStream(in);
                this.outputStream = new BufferedOutputStream(new FileOutputStream(file));
                byte[] data = new byte[2048];

                int n;
                while ((n = this.inputStream.read(data)) != -1) {
                    this.outputStream.write(data, 0, n);
                }

                this.outputStream.flush();
                in.close();
                this.inputStream.close();
                this.outputStream.close();
                conn.disconnect();
                return 1;
            }
        }
    }
}
