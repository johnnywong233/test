package project.netSpider;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by johnny on 2016/9/16.
 * main Class
 */
public class FileDownLoader {
    /**
     * 根据 url 和网页类型生成需要保存的网页的文件名
     * 去除掉 url 中非文件名字符
     */
    private String getFileNameByUrl(String url, String contentType) {
        url = url.substring(7);//remove http://
        //text/html
        if (contentType.contains("html")) {
            url = url.replaceAll("[?/:*|<>\"]", "_") + ".html";
            return url;
        } else {//如application/pdf
            return url.replaceAll("[?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    /**
     * 保存网页字节数组到本地文件
     * filePath 为要保存的文件的相对地址
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(new File(filePath)));
            for (byte aData : data) {
                out.write(aData);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*下载 url 指向的网页*/
    String downloadFile(String url) {
        String filePath = null;
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().
                setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //set request retry handler
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
                filePath = null;
            }
            byte[] responseBody = getMethod.getResponseBody();//读取为字节数组
            //根据网页 url 生成保存时的文件名
            filePath = "D:\\Java_ex\\test\\src\\test\\resources\\" + getFileNameByUrl(url,
                    getMethod.getResponseHeader("Content-Type").getValue());
            saveToLocal(responseBody, filePath);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return filePath;
    }

    public static void main(String[] args) {
        FileDownLoader downLoader = new FileDownLoader();
        downLoader.downloadFile("http://www.twt.edu.cn");
    }
}
