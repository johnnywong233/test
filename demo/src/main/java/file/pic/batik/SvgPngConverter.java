package file.pic.batik;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 2016/10/7.
 * converter between svg and png
 */
public class SvgPngConverter {
    //https://my.oschina.net/yuyidi/blog/339684
    public static void main(String[] args) throws IOException, TranscoderException {
        SvgPngConverter demo = new SvgPngConverter();
        Map<String, String> map = new HashMap<>();
        //TODO
        map.put("", "");
        demo.convertToPngByFile("D:\\Java_ex\\test\\src\\test\\resources\\solr.svg", "D:\\Java_ex\\test\\src\\test\\resources\\1.png", map);

    }

    /**
     * batik通过读取svg文件的方式转png
     *
     * @param filePath    传入读取的svg文件
     * @param pngFilePath 转换后的png图片
     * @param map         更改svg属性的集合 传值规则，id,name,value 主要是更改svg子节点的颜色属性值。
     *                    如果需要改变svg的多个element的颜色属性 则命名规范为 id1,name1,value1,id2,name2,value2....依次类推
     */
    private void convertToPngByFile(String filePath, String pngFilePath, Map<String, String> map)
            throws IOException, TranscoderException {
        File file = new File(pngFilePath);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            convertToPngByFile(filePath, outputStream, map);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void convertToPngByFile(String path, OutputStream outputStream, Map<String, String> map)
            throws TranscoderException, IOException {
        try {
            File file = new File(path);
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
            Document doc = f.createDocument(file.toURI().toString());
            for (int i = 1; i <= map.size() / 3; i++) {
                Element e = doc.getElementById(map.get("id" + i));
                System.out.println(map.get("name" + i));
                e.setAttribute(map.get("name" + i), map.get("value" + i));
            }
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(doc);
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
