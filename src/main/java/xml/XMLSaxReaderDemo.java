package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wajian on 2016/8/28.
 */
public class XMLSaxReaderDemo {

	//http://www.jb51.net/article/45451.htm
    public static void main(String[] args) {
        SAXParserFactory sf = SAXParserFactory.newInstance();
        SAXParser sp = null;
        try {
            sp = sf.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        XMLSaxReader reader = new XMLSaxReader();
        String xml = new File("C:\\work\\test\\src\\main\\resources\\sample.xml");
        InputStream in_withcode = new ByteArrayInputStream(
                xml.getBytes("UTF-8"));//xml就是刚得到的xml文件，类型String
        sp.parse(in_withcode, reader);
        List<Video> videos = new ArrayList<Video>();
        Long timeLength;
        videos = reader.getVideos();//得到Video List
        timeLength = reader.getLength();//得到视频长度
        System.out.println(videos);
    }
}

class XMLSaxReader extends DefaultHandler {
    private List<Video> videos = null;
    private Video video = null;
    private Long timeLength = null;
    private String tag = null;

    @Override
    public void startDocument() throws SAXException {
        videos = new ArrayList<Video>();
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

    public List<Video> getVideos() {
        return videos;
    }

    public long getLength() {
        return timeLength;
    }
}


class Video{
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