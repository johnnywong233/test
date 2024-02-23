package file.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.StringUtil;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wajian on 2016/8/28.
 * demo of sax parse xml
 */
public class XMLSaxReaderDemo {
    //http://www.jb51.net/article/45451.htm
    public static void main(String[] args) throws IOException, SAXException {
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = null;
        try {
            sp = sf.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        XMLSaxReader reader = new XMLSaxReader();
        String xml = StringUtil.inputStream2String(new File("C:\\work\\test\\src\\main\\resources\\sample.xml"));
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));//xml就是刚得到的xml文件，类型String
        if (sp != null) {
            sp.parse(is, reader);
        }
        List<Video> videos;
        videos = reader.getVideos();//get Video List
        Long timeLength = reader.getLength();//get length of video
        System.out.println(videos);
        System.out.println(timeLength);
    }

}

class XMLSaxReader extends DefaultHandler {
    private List<Video> videos = null;
    private Video video = null;
    private Long timeLength = null;
    private String tag = null;

    @Override
    public void startDocument() throws SAXException {
        videos = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("durl".equals(qName)) {
            video = new Video();
        }
        tag = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("durl".equals(qName)) {
            videos.add(video);
            video = null;
        }
        tag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (tag != null) {
            String data = new String(ch, start, length);
            if ("timelength".equals(tag)) {
                timeLength = Long.valueOf(data);
            } else if ("order".equals(tag)) {
                video.setOrder(Integer.valueOf(data));
            } else if ("url".equals(tag)) {
                video.setUrl(data);
            } else if ("length".equals(tag)) {
                video.setLength(Integer.valueOf(data));
            }
        }
    }

    List<Video> getVideos() {
        return videos;
    }

    public long getLength() {
        return timeLength;
    }
}


class Video {
    private Integer order;
    private Integer length;
    private String url;

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}