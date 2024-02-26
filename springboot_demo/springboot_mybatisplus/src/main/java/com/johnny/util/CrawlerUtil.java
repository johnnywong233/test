package com.johnny.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Author: Johnny
 * Date: 2017/9/16
 * Time: 14:18
 */
@Slf4j
public class CrawlerUtil {
    // 访问接口，返回json封装的数据格式
    public static JSONObject getReturnJson(String url) {
        try {
            URL httpUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.openStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();
            char[] a = content.toString().toCharArray();
            if (a[0] == '[' && a[1] == ']') {
                return null;
            } else {
                return JSONObject.parseObject(content.toString());
            }
        } catch (Exception e) {
            log.error("接口访问失败:" + url);
        }
        return null;
    }

    public static void saveToFile(String destUrl, String saveUrl) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url;
        String uuid = UUID.randomUUID().toString();
        // 存储本地文件地址
        String fileAddress = saveUrl + uuid;
        int bufferSize = 1024;
        byte[] buf = new byte[bufferSize];
        int size;
        try {
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            String type = httpUrl.getHeaderField("Content-Type");
            switch (type) {
                case "image/gif":
                    fileAddress += ".gif";
                    break;
                case "image/png":
                    fileAddress += ".png";
                    break;
                case "image/jpeg":
                    fileAddress += ".jpg";
                    break;
                default:
                    log.error("未知图片格式");
                    return;
            }
            bis = new BufferedInputStream(httpUrl.getInputStream());
            fos = new FileOutputStream(fileAddress);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException | ClassCastException e) {
            log.error("saveToFile fail", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (httpUrl != null) {
                    httpUrl.disconnect();
                }
            } catch (IOException | NullPointerException e) {
                log.error("close fail", e);
            }
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("close fail", e2);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("close fail", ex);
            }
        }
        return result.toString();
    }
}
