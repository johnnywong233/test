package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

//jsoup HTML parser

/**
 * Created by johnny on 2016/9/15.
 * jsoup实现抓图
 */
public class Demo {
    //cannot change another url
    private static final String url = "http://www.dbmeinv.com/?p=";
    private static final String picPath = "d:/picTest";

    //http://www.phpxs.com/code/1001569/
    public static void main(String[] args) {
        System.out.println("save pictures in d:/picTest");
        for (int i = 0; i < 50; i++) {
            try {
                String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0";
                Document doc = Jsoup.connect(url + i).userAgent(USER_AGENT).timeout(3000).data("pager_offset", i + 1 + "").post();
                Elements img = doc.select("img");
                for (Element ele : img) {
                    String src = ele.absUrl("src");
                    //System.out.println(src);
                    getImage(src);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("enough! pic download finish!");
    }


    private static void getImage(String src) {
        int indexName = src.lastIndexOf("/");
        String name = src.substring(indexName, src.length());
        //System.out.println(name);
        InputStream in = null;
        OutputStream out = null;
        try {
            URL url = new URL(src);
            in = url.openStream();

            //create folder
            File files = new File(picPath);
            if(!files.exists())
                files.mkdirs();

            out = new BufferedOutputStream(new FileOutputStream(files+name));
            for(int b;(b = in.read()) != -1;)
                out.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}