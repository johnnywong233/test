package com.johnny.service.handler;

import com.johnny.common.Console;
import com.johnny.common.ReflectUtils;
import com.johnny.service.handler.finder.IAlbumURLFinder;
import com.johnny.service.handler.handler.DefaultAlbumHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AlbumHandlerFactory {
    private static final String PACKAGE_FINDER = "com.johnny.service.handler.finder.impl";
    private static final String PACKAGE_HANDER = "com.johnny.service.handler.handler";
    private static Map<String, IAlbumURLFinder> albumURLFinderMap = new HashMap<>();
    private static Map<String, Class<?>> albumHandlerClassMap = new HashMap<>();

    static {
        List<Class<?>> finderClassList = ReflectUtils.getClassWithPackage(PACKAGE_FINDER);
        if (finderClassList != null) {
            for (Class<?> finderClass : finderClassList) {
                try {
                    IAlbumURLFinder obj = (IAlbumURLFinder) finderClass.newInstance();
                    albumURLFinderMap.put(obj.getURLRegex(), obj);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        List<Class<?>> handlerClassList = ReflectUtils.getClassWithPackage(PACKAGE_HANDER);
        assert handlerClassList != null;
        for (Class<?> handlerClass : handlerClassList) {
            try {
                AlbumHandler obj = (AlbumHandler) handlerClass.newInstance();
                albumHandlerClassMap.put(obj.getURLRegex(), handlerClass);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<AlbumHandler> getHandler(String url) {
        return getHandler(url, true);
    }

    public static List<AlbumHandler> getHandler(String url, boolean isPrintLog) {
        List<String> albumURLList = new ArrayList<>();

        boolean hasFinder = false;
        List<String> albumURLs;
        for (Entry<String, IAlbumURLFinder> element : albumURLFinderMap.entrySet()) {
            if (url.matches(element.getKey())) {
                IAlbumURLFinder albumURLFinder = element.getValue();
                albumURLs = albumURLFinder.findAlbumURL(url);
                for (String u : albumURLs) {
                    if (isPrintLog) {
                        Console.print("获取相册地址：" + u);
                    }
                }
                albumURLList.addAll(albumURLs);
                hasFinder = true;
                break;
            }
        }
        if (!hasFinder) {
            albumURLList.add(url);
            if (isPrintLog) {
                Console.print("获取图片地址：" + url);
            }
        }

        List<AlbumHandler> handlerList = new ArrayList<>();
        boolean hasHandler = false;
        for (String albumURL : albumURLList) {
            for (Entry<String, Class<?>> element : albumHandlerClassMap.entrySet()) {
                if ((element.getKey() != null) && (albumURL.matches(element.getKey()))) {
                    Class<?> clazz = element.getValue();
                    try {
                        AlbumHandler handler = (AlbumHandler) clazz.newInstance();
                        handler.setAlbumURL(albumURL);
                        handlerList.add(handler);
                        if (isPrintLog) {
                            Console.print("创建相册处理器：" + clazz.getSimpleName() + " - " + albumURL);
                        }
                        hasHandler = true;
                    } catch (IllegalArgumentException | SecurityException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (!hasHandler) {
                DefaultAlbumHandler defaultAlbumHandler = new DefaultAlbumHandler();
                defaultAlbumHandler.setAlbumURL(albumURL);
                handlerList.add(defaultAlbumHandler);
                if (isPrintLog) {
                    Console.print("创建默认相册处理器：" + defaultAlbumHandler.getClass().getSimpleName() + " - " + albumURL);
                }
            }
        }
        return handlerList;
    }

    public static void main(String[] args) {
        System.out.println("-------------相册列表--------------");
        getHandler("http://www.douban.com/people/blackgray/photos/");
        System.out.println("-------------相册--------------");
        getHandler("http://www.douban.com/photos/album/67952443/");

        System.out.println("-------------小站--------------");
        getHandler("http://site.douban.com/108128/widget/photos/7528342/");
        getHandler("http://site.douban.com/zheng/widget/photos/17304118/");

        System.out.println("-------------影人首页--------------");
        getHandler("http://movie.douban.com/celebrity/1048027/");
        System.out.println("-------------影人--------------");
        getHandler("http://movie.douban.com/celebrity/1048027/photos/");

        System.out.println("-------------活动首页--------------");
        getHandler("http://www.douban.com/online/11127307/");
        System.out.println("-------------活动--------------");
        getHandler("http://www.douban.com/online/11127307/album/72416214/");

        System.out.println("-------------其他--------------");
        getHandler("http://www.baidu.com/");
    }
}