package file.poi;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;

import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 * Created by johnny on 2016/10/7.
 * test on insert a link in a Word document
 */
public class WordInsertLink {
    //http://www.mincoder.com/article/2848.shtml
    public static void main(String[] args) throws Exception {
        WordInsertLink t = new WordInsertLink();
        t.example("/Users/johnny/link_result.docx");
    }

    public void example(String savePath) throws Exception {
        XWPFDocument document = new XWPFDocument();
        // Append a link to
        appendExternalHyperlink("mailto:1224017485@qq.com?subject=测试poi超链接", " 测试超链接HyperLink", document.createParagraph());
        document.write(new FileOutputStream(savePath));
    }

    /**
     * Appends an external hyperlink to the paragraph.
     *
     * @see "https://stackoverflow.com/questions/7007810/how-to-create-a-email-link-in-poi-word-format"
     */
    public void appendExternalHyperlink(String url, String text, XWPFParagraph paragraph) {
        // Add the link as External relationship
        String id = paragraph
                .getDocument()
                .getPackagePart()
                .addExternalRelationship(url,
                        XWPFRelation.HYPERLINK.getRelation()).getId();
        // Append the link and bind it to the relationship
        CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
        cLink.setId(id);
        // Create the linked text
        CTText ctText = CTText.Factory.newInstance();
        ctText.setStringValue(text);
        CTR ctr = CTR.Factory.newInstance();
        CTRPr rpr = ctr.addNewRPr();
        //设置超链接样式
        CTColor color = CTColor.Factory.newInstance();
        color.setVal("0000FF");
        rpr.addNewColor();
        rpr.addNewU().setVal(STUnderline.SINGLE);
        //设置字体
        CTFonts fonts = rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");
        //设置字体大小
        CTHpsMeasure sz = rpr.addNewSz();
        sz.setVal(new BigInteger("24"));
        ctr.setTArray(new CTText[]{ctText});
        // Insert the linked text into the link
        cLink.setRArray(new CTR[]{ctr});
        //设置段落居中
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
    }
}
