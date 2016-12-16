package tika;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wajian on 2016/10/4.
 * simple demo of tika
 */
public class TypeDetection {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\work\\test\\src\\main\\resources\\johnny.txt");
        //Instantiating	tika facade	class
        Tika tika = new Tika();
        //detecting	the	file type using	detect	method
        String fileType = tika.detect(file);
        System.out.println(fileType);

        //TODO: why nothing?
        String fileContent = tika.parseToString(file);
        System.out.println("Extracted Content:" + fileContent);

        //parse	method	parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        parser.parse(inputStream, handler, metadata, context);
        System.out.println("File content : " + handler.toString());
    }
}
