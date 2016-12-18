package htmlparser;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import java.io.File;
import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 23:00
 */
public class HtmlClean {

    //http://www.jb51.net/article/48329.htm
    public static void main(String[] args) {
        HtmlClean test = new HtmlClean();
        //TODO
        test.cleanHtml("https://www.baidu.com/more/", "D:\\Java_ex\\test\\src\\test\\resources\\parserResponse.xml");
    }

    public void cleanHtml(String htmlurl, String xmlurl) {
        try {
            long start = System.currentTimeMillis();
            HtmlCleaner cleaner = new HtmlCleaner();
            CleanerProperties props = cleaner.getProperties();
            props.setUseCdataForScriptAndStyle(true);
            props.setRecognizeUnicodeChars(true);
            props.setUseEmptyElementTags(true);
            props.setAdvancedXmlEscape(true);
            props.setTranslateSpecialEntities(true);
            props.setBooleanAttributeValues("empty");

            TagNode node = cleaner.clean(new File(htmlurl));
            System.out.println("time consumed:" + (System.currentTimeMillis() - start));

            new PrettyXmlSerializer(props).writeXmlToFile(node, xmlurl);
            System.out.println("time consumed for:" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
