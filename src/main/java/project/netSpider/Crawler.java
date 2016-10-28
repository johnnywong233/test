package project.netSpider;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by johnny on 2016/9/16.
 *
 */
public class Crawler {
    /* 使用种子 url 初始化 URL 队列*/
    private void initCrawlerWithSeeds(String[] seeds) {
        for (String seed : seeds)
            LinkDb.addUnvisitedUrl(seed);
    }

    /* 爬取方法*/
    private void crawling(String[] seeds) {
        LinkFilter filter = new LinkFilter(){
            //提取以 http://www.twt.edu.cn 开头的链接
            public boolean accept(String url) {
                return url.startsWith("http://www.twt.edu.cn");
            }
        };
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while(!LinkDb.unVisitedUrlsEmpty()&&LinkDb.getVisitedUrlNum()<=1000)
        {
            //队头 URL 出对
            String visitUrl=LinkDb.unVisitedUrlDeQueue();
            if(visitUrl==null)
                continue;
            FileDownLoader downLoader=new FileDownLoader();
            //下载网页
            downLoader.downloadFile(visitUrl);
            //该 url 放入到已访问的 URL 中
            LinkDb.addVisitedUrl(visitUrl);
            //提取出下载网页中的 URL

            Set<String> links=HtmlParserTool.extractLinks(visitUrl,filter);
            //新的未访问的 URL 入队
            for(String link:links)
            {
                LinkDb.addUnvisitedUrl(link);
            }
        }
    }

    // http://www.jb51.net/article/76224.htm
    public static void main(String[]args)
    {
        Crawler crawler = new Crawler();
        crawler.crawling(new String[]{"http://www.twt.edu.cn"});
    }
}

//用来保存已经访问过 Url 和待访问的 Url 的类
class LinkDb {
    //已访问的 url 集合
    private static Set<String> visitedUrl = new HashSet<>();
    //待访问的 url 集合
    private static Queue<String> unVisitedUrl = new Queue<>();

    public static Queue<String> getUnVisitedUrl() {
        return unVisitedUrl;
    }

    static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }

    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    static String unVisitedUrlDeQueue() {
        return unVisitedUrl.deQueue();
    }

    // 保证每个 url 只被访问一次
    static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("")
                && !visitedUrl.contains(url)
                && !unVisitedUrl.contians(url))
            unVisitedUrl.enQueue(url);
    }

    static int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    static boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.empty();
    }
}
