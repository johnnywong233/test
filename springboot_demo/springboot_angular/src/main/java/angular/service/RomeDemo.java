package angular.service;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.List;

/**
 * @author johnny
 */
@Slf4j
public class RomeDemo {

    public static void main(String[] args) throws FeedException, IOException {
        publish();
        query();
    }

    public static void publish() throws IOException, FeedException {
        SyndContent desc = new SyndContentImpl();
        desc.setType("text/html");
        desc.setValue("ES系列");

        SyndCategory category = new SyndCategoryImpl();
        category.setName("ES系列");
        List<SyndCategory> categories = List.of(category);

        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("ES系列");
        entry.setLink("https://blog.csdn.net/lonelymanontheway/category_12594356.html");
        entry.setDescription(desc);
        entry.setCategories(categories);

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_1.0");
        feed.setTitle("johnny CSDN blog");
        feed.setLink("https://blog.csdn.net/lonelymanontheway");
        feed.setDescription("rome rss test");
        feed.setEntries(List.of(entry));

        Writer writer = new FileWriter("xyz.xml");
        SyndFeedOutput syndFeedOutput = new SyndFeedOutput();
        syndFeedOutput.output(feed, writer);
        writer.close();
    }


    public static void query() throws IOException, FeedException {
        URL feedSource = new URL("https://www.douban.com/feed/subject/36686001/reviews");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        log.info("feed:{}", feed);
    }
}
