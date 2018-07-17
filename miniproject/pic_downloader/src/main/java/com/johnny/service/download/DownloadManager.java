package com.johnny.service.download;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.ui.MainFrame;

import javax.swing.JProgressBar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DownloadManager {
    private static final JProgressBar mainProgressBar = MainFrame.getInstance().progressBar;
    private static final Integer TIMEOUT = 10;

    static int downloadImage(List<String> imageURLList, String path) {
        mainProgressBar.setMaximum(imageURLList.size());
        mainProgressBar.setValue(0);
        List<DownloadThread> threadList = new ArrayList<>();
        int imageSize = imageURLList.size();
        DownloadThread downloadThread;
        for (int i = 0; i < 15; i++) {
            String threadName = "线程0";
            if (i < 10) {
                threadName = threadName + i;
            } else {
                threadName = "线程" + String.valueOf(i);
            }
            downloadThread = new DownloadThread(threadName, imageURLList, imageSize, path, mainProgressBar);
            downloadThread.start();
            threadList.add(downloadThread);
        }
        Map<Thread, Integer> waitThreadMap = new HashMap<>();
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (imageURLList.size() == 0) {
                for (DownloadThread thread : threadList) {
                    if (thread.isAlive()) {
                        if (waitThreadMap.containsKey(thread)) {
                            waitThreadMap.put(thread, waitThreadMap.get(thread) + 1);
                        } else {
                            waitThreadMap.put(thread, 0);
                        }

                        if (waitThreadMap.get(thread) > TIMEOUT) {
                            try {
                                Console.print("下载超时,中断线程,请稍等.. - " + thread.getName() + " - " + thread.getUrl());
                                thread.closeStream();

                                if (!Common.failFileMap.containsKey(thread.getUrl())) {
                                    Common.failFileMap.put(thread.getUrl(), thread.getPath());
                                }
                            } catch (IOException e) {
                                Console.print("线程中断操作异常：" + e.getMessage());
                                e.printStackTrace();
                            }
                            waitThreadMap.remove(thread);
                        }
                    } else {
                        waitThreadMap.remove(thread);
                    }
                }

                if (waitThreadMap.size() == 0) {
                    mainProgressBar.setValue(mainProgressBar.getMaximum());
                    break;
                }

                Console.print("就快好了~ ～(￣▽￣～)(～￣▽￣)～ ");
                Console.print("---------------------------------------------------");
                for (Entry<Thread, Integer> entry : waitThreadMap.entrySet()) {
                    DownloadThread t = (DownloadThread) entry.getKey();
                    Integer time = entry.getValue();
                    String sb = "等待线程" + " - " + t.getName() + " - [" + time + "s]" +
                            " = " + t.getUrl();
                    Console.print(sb);
                }
            }
        }
        return 0;
    }

    public static int downloadFailFile() {
        int num = 1;
        int size = Common.failFileMap.size();
        JProgressBar progressBar = MainFrame.getInstance().progressBar;
        progressBar.setMaximum(size);
        progressBar.setValue(0);
        Console.print("=====================================");
        Console.print("下载图片上次失败图片：" + size + "(张)");
        Map<String, String> failMap = new TreeMap<>();
        for (Entry<String, String> element : Common.failFileMap.entrySet()) {
            try {
                Console.print("下载图片(" + num + "/" + size + ")：" + element.getKey());
                DownloadThread downloadThread = new DownloadThread();
                downloadThread.downloadImage(element.getKey(), element.getValue(), true);
            } catch (IOException e) {
                Console.print("图片下载失败：" + element.getKey());
                failMap.put(element.getKey(), element.getValue());
            }
            progressBar.setValue(num);
            num++;
        }
        Common.failFileMap.clear();
        if (failMap.size() > 0) {
            Console.print("【FINISH】成功：" + (size - failMap.size()) + "，失败" + failMap.size());
            Common.failFileMap.putAll(failMap);
            return 0;
        }
        Console.print("【FINISH】成功：" + size + "，失败" + 0);
        return 1;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("START");
        new DownloadThread().downloadImage("http://img3.douban.com/view/photo/photo/public/p1105635956.jpg", "D:\\", true);
        System.out.println("FINISH");
    }
}